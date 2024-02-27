package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.admingeneral.CurriculumDetailedDto;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.dto.admingeneral.LevelRequest;
import org.am.core.web.dto.admingeneral.SubjectCurriculumRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculumService implements CustomMap<CurriculumDto, Curriculum> {
    private final CurriculumRepository curriculumRepository;

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

    public CurriculumDto edit(CurriculumDetailedDto dto) {
        Curriculum curriculumFromDb = curriculumRepository.findById(dto.curriculumId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id for curriculumId"));
        curriculumFromDb.setName(dto.name());
        curriculumFromDb.setMinApprovedSubjeccts(dto.minApprovedSubjects());
        curriculumFromDb.setStarDate(dto.startDate());
        curriculumFromDb.setEndDate(dto.endDate());

        Set<SubjectCurriculum> newSubjectCurriculumSet = new HashSet<>();
        Set<SubjectCurriculum> subjectCurriculumSet = curriculumFromDb.getSubjectCurriculumSet();

        for (LevelRequest levelRequest : dto.levelList()) {
            for (SubjectCurriculumRequest subjectCurriculumRequest : levelRequest.subjectCurriculumList()) {

                Optional<SubjectCurriculum> subjectCurriculumOptional = subjectCurriculumSet
                        .stream()
                        .filter(obj -> obj.getSubjectCurriculumId().getCurriculumId().equals(dto.curriculumId())
                                && obj.getSubjectCurriculumId().getSubjectId().equals(subjectCurriculumRequest.subjectId()))
                        .findFirst();
                if (subjectCurriculumOptional.isPresent()) {
                    SubjectCurriculum subjectCurriculum = subjectCurriculumOptional.get();
                    subjectCurriculum.setOptional(subjectCurriculumRequest.optional());
                    subjectCurriculum.setPath(subjectCurriculumRequest.path());
                    subjectCurriculum.setWorkload(subjectCurriculumRequest.workload());

                    newSubjectCurriculumSet.add(subjectCurriculum);
                } else {
                    newSubjectCurriculumSet.add(getSubjectCurriculumInstance(subjectCurriculumRequest,
                            levelRequest, dto.curriculumId()));
                }

            }
        }

        curriculumFromDb.setSubjectCurriculumSet(newSubjectCurriculumSet);

        return toDto(curriculumRepository.save(curriculumFromDb));
    }

    public void delete(Integer id) {
        curriculumRepository.deleteById(id);

    }

    public void logicalDelete(Integer id){
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        curriculum.setActive(Boolean.FALSE);

        for (SubjectCurriculum subjectCurriculum : curriculum.getSubjectCurriculumSet()) {
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

        for (LevelRequest levelRequest : curriculumRequest.levelList()) {
            for (SubjectCurriculumRequest subjectCurriculumRequest : levelRequest.subjectCurriculumList()) {
                SubjectCurriculum subjectCurriculum = getSubjectCurriculum(levelRequest, subjectCurriculumRequest, savedCurriculum);
                savedCurriculum.getSubjectCurriculumSet().add(subjectCurriculum);
            }
        }
        return savedCurriculum;
    }
<<<<<<< HEAD

    private static SubjectCurriculum getSubjectCurriculum(LevelRequest levelRequest1, SubjectCurriculumRequest subjectCurriculumRequest, Curriculum savedCurriculum) {
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

        return subjectCurriculum;
    }

    private static SubjectCurriculum getSubjectCurriculumInstance(SubjectCurriculumRequest subjectCurriculumRequest,
                                                                 LevelRequest levelRequest, Integer curriculumId) {
        Curriculum curriculum = new Curriculum();
        curriculum.setId(curriculumId);

        Subject subject = new Subject();
        subject.setId(subjectCurriculumRequest.subjectId());
        return new SubjectCurriculum(
                null,
=======

    private static SubjectCurriculum getSubjectCurriculum(LevelRequest levelRequest1, SubjectCurriculumRequest subjectCurriculumRequest, Curriculum savedCurriculum) {
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

        return subjectCurriculum;
    }

    private static SubjectCurriculum getSubjectCurriculumInstance(SubjectCurriculumRequest subjectCurriculumRequest,
                                                                 LevelRequest levelRequest, Integer curriculumId) {
        Curriculum curriculum = new Curriculum();
        curriculum.setId(curriculumId);

        Subject subject = new Subject();
        subject.setId(subjectCurriculumRequest.subjectId());

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                curriculum.getId(),
                subject.getId()

        );
        return new SubjectCurriculum(
                subjectCurriculumId,
>>>>>>> 1bdf8b3e9afe938519ceccccfae09c97232d4c16
                levelRequest.levelIdentifier(),
                subjectCurriculumRequest.optional(),
                subjectCurriculumRequest.path(),
                subjectCurriculumRequest.workload(),
                Boolean.TRUE,
                levelRequest.levelName(),
                curriculum,
                subject
        );
    }
}
