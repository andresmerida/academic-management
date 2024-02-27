package org.am.core.web.service.admingeneral;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.dto.admingeneral.LevelRequest;
import org.am.core.web.dto.admingeneral.SubjectCurriculumRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CurriculumRepository;
import org.am.core.web.repository.jpa.schedule.GroupItineraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculumService implements CustomMap<CurriculumDto, Curriculum> {
    private final CurriculumRepository curriculumRepository;
    private final GroupItineraryRepository groupItineraryRepository;

    @Transactional(readOnly = true)
    public List<CurriculumDto> getCurriculumsByCareerId(Integer careerId) {
        return curriculumRepository.findCurriculumByCareer_IdAndActiveOrderByName(careerId, Boolean.TRUE)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CurriculumDto save(CurriculumRequest curriculumRequest) {
        return toDto(curriculumRepository.save(this.toEntity(curriculumRequest)));
    }

    @Transactional
    public void delete(Integer id) {
            Curriculum curriculum = curriculumRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

            curriculum.setActive(Boolean.FALSE);

            Set<SubjectCurriculum> subjectCurriculums = curriculum.getLevelRequests();
            for (SubjectCurriculum subjectCurriculum : subjectCurriculums) {
                subjectCurriculum.setActive(false);
            }
            curriculumRepository.save(curriculum);

    }

    @Override
    public CurriculumDto toDto(Curriculum curriculum) {
        return new CurriculumDto(
                curriculum.getId(),
                curriculum.getName(),
                curriculum.getMinApprovedSubjeccts(),
                curriculum.getStarDate(),
                curriculum.getEndDate()
        );
    }

    @Override
    public Curriculum toEntity(CurriculumDto curriculumDto) {
        return null;
    }


    public Curriculum toEntity(CurriculumRequest curriculumRequest) {
        Curriculum curriculum = new Curriculum();

        curriculum.setName(curriculumRequest.name());
        curriculum.setMinApprovedSubjeccts(curriculumRequest.minApprovedSubjects());
        curriculum.setStarDate(curriculumRequest.startDate());
        curriculum.setEndDate(curriculumRequest.endDate());
        curriculum.setActive(Boolean.TRUE);

        Career career = new Career();
        career.setId(curriculumRequest.careerId());
        curriculum.setCareer(career);

        Curriculum savedCurriculum = curriculumRepository.save(curriculum);

        List<LevelRequest> levelRequests = curriculumRequest.levelList();

        for (LevelRequest levelRequest1 : levelRequests) {
            for (SubjectCurriculumRequest subjectCurriculumRequest : levelRequest1.subjectCurriculumList()) {
                SubjectCurriculum subjectCurriculum = new SubjectCurriculum();

                Subject subject = new Subject();
                subject.setId(subjectCurriculumRequest.subjectId());
                subjectCurriculum.setSubject(subject);

                SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                        savedCurriculum.getId(),
                        subject.getId()

                );
                subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
                subjectCurriculum.setLevelName(levelRequest1.levelName());
                subjectCurriculum.setLevel(levelRequest1.levelIdentifier());
                subjectCurriculum.setPath(subjectCurriculumRequest.path());
                subjectCurriculum.setWorkload(subjectCurriculumRequest.workload());
                subjectCurriculum.setOptional(subjectCurriculumRequest.optional());
                subjectCurriculum.setActive(Boolean.TRUE);
                subjectCurriculum.setCurriculum(savedCurriculum);

                if (savedCurriculum.getLevelRequests() == null) {
                    savedCurriculum.setLevelRequests(new HashSet<>());
                }
                savedCurriculum.getLevelRequests().add(subjectCurriculum);
            }
        }
        return savedCurriculum;
    }
}
