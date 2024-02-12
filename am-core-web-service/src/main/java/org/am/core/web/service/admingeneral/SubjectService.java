package org.am.core.web.service.admingeneral;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.*;
import org.am.core.web.dto.admingeneral.CareerDto;
import org.am.core.web.dto.admingeneral.CareerRequest;
import org.am.core.web.dto.admingeneral.SubjectDto;
import org.am.core.web.dto.admingeneral.SubjectRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.GroupRepository;
import org.am.core.web.repository.jpa.admingeneral.ScheduleRepository;
import org.am.core.web.repository.jpa.admingeneral.SubjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService implements CustomMap<SubjectDto, Subject> {

    private  final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;


    public List<SubjectDto> getSubjectByAreaIdAndActive(Integer areaID) {
        List<Subject> subjects = subjectRepository.findAllByArea_IdAndActiveOrderByName(areaID, Boolean.TRUE);

        return subjects.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SubjectDto save(SubjectRequest subjectRequest) {
        return toDto(subjectRepository.save(this.toEntity(subjectRequest)));
    }

    public SubjectDto edit(SubjectDto subjectDto) {
        Subject subjectFromDB = subjectRepository.findById(subjectDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        subjectFromDB.setName(subjectDto.name());
        subjectFromDB.setInitials(subjectDto.initials());
        return toDto(subjectRepository.save(subjectFromDB));
    }

    public void delete(Integer id) {
        try {
            subjectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            List<Group> groups = groupRepository.findBySubjectId(id);

            for (Group group : groups) {
                List<Schedule> schedules = scheduleRepository.findByGroupId(group.getId());
                scheduleRepository.deleteAll(schedules);
            }

            groupRepository.deleteAll(groups);

            Subject subjectFromDB = subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
            subjectFromDB.setActive(Boolean.FALSE);
            subjectRepository.save(subjectFromDB);
        }
    }

    public Optional<SubjectDto> getSubjectById(Integer id) {
        return subjectRepository.findById(id).map(this::toDto);
    }


    @Override
    public SubjectDto toDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName(),
                subject.getInitials()
        );
    }

    @Override
    public Subject toEntity(SubjectDto subjectDto) {
        return null;
    }

    private Subject toEntity(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setName(subjectRequest.name());
        subject.setInitials(subjectRequest.initials());
        subject.setActive(Boolean.TRUE);

        Area area = new Area();
        area.setId(subjectRequest.areaId());
        subject.setArea(area);

        return subject;
    }
}
