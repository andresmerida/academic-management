package org.am.core.web.service.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;
import org.am.core.web.domain.entity.schedule.SubjectCurriculumId;
import org.am.core.web.dto.schedule.SubjectCurriculumDto;
import org.am.core.web.dto.schedule.SubjectCurriculumRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.SubjectCurriculumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectCurriculumService implements CustomMap<SubjectCurriculumDto, SubjectCurriculum> {

    private final SubjectCurriculumRepository subjectCurriculumRepository;



    public List<SubjectCurriculumDto> listSubjectCurriculum() {
        return subjectCurriculumRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SubjectCurriculumDto> getSubjectCurriculumById(SubjectCurriculumId id){
        return subjectCurriculumRepository.findById(id).map(this::toDto);
    }

    public SubjectCurriculumDto save(SubjectCurriculumRequest subjectCurriculumRequest){
        return toDto(subjectCurriculumRepository.save(toEntity(subjectCurriculumRequest)));
    }

    public SubjectCurriculumDto edit(SubjectCurriculumRequest subjectCurriculumDto, SubjectCurriculumId subjectCurriculumId){
        SubjectCurriculum subjectCurriculumFromDB = subjectCurriculumRepository.findById(subjectCurriculumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        subjectCurriculumFromDB.setLevel(subjectCurriculumDto.level());
        subjectCurriculumFromDB.setOptional(subjectCurriculumDto.optional());
        subjectCurriculumFromDB.setPath(subjectCurriculumDto.path());
        subjectCurriculumFromDB.setWorkload(subjectCurriculumDto.workload());

        return toDto(subjectCurriculumRepository.save(subjectCurriculumFromDB));
    }


    @Override
    public SubjectCurriculumDto toDto(SubjectCurriculum subjectCurriculum) {
        SubjectCurriculumId subjectCurriculumId = subjectCurriculum.getSubjectCurriculumId();
        return new SubjectCurriculumDto(
                subjectCurriculumId.getCurriculumId(),
                subjectCurriculumId.getSubjectId(),
                subjectCurriculum.getLevel(),
                subjectCurriculum.getOptional(),
                subjectCurriculum.getPath(),
                subjectCurriculum.getWorkload(),
                subjectCurriculum.getActive()
        );
    }

    @Override
    public SubjectCurriculum toEntity(SubjectCurriculumDto subjectCurriculumDto) {
        return null;
    }

    public SubjectCurriculum toEntity(SubjectCurriculumRequest subjectCurriculumRequest) {
        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                subjectCurriculumRequest.curriculumId(),
                subjectCurriculumRequest.subjectId()
        );

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        subjectCurriculum.setLevel(subjectCurriculumRequest.level());
        subjectCurriculum.setOptional(subjectCurriculumRequest.optional());
        subjectCurriculum.setPath(subjectCurriculumRequest.path());
        subjectCurriculum.setWorkload(subjectCurriculumRequest.workload());
        subjectCurriculum.setActive(subjectCurriculumRequest.active());
        return subjectCurriculum;
    }


}
