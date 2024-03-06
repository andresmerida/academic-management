package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.*;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.domain.entity.schedule.Itinerary;
import org.am.core.web.domain.entity.schedule.Schedule;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;
import org.am.core.web.domain.entity.schedule.SubjectCurriculumId;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService implements CustomMap <GroupDto, Group> {

    private final GroupRepository groupRepository;
    private final ScheduleService scheduleService;
    private GroupItineraryService groupItineraryService;


    public void generateGroupsFromItinerary(Integer careerId, Integer itineraryId,Integer academicPeriodId) {
        List<GroupDetailedDto> groupDetailedAuxDtoList = groupItineraryService.getGroupsScheduleByCareerAndItinerary(careerId, itineraryId);
        for(GroupDetailedDto groupDetailedDto: groupDetailedAuxDtoList){
            Group groupAux = groupRepository.findByIdentifierAndRemark(groupDetailedDto.groupIdentifier(), groupDetailedDto.remark());
            if(groupAux == null){
                groupRepository.save(toEntity(groupDetailedDto, academicPeriodId));
            }else {
                groupAux = null;
            }
        }

    }


    @Override
    public GroupDto toDto(Group group) {
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (group.getScheduleSetList() != null) {
            scheduleDtos = group.getScheduleSetList()
                    .stream()
                    .map(scheduleService::toDto)
                    .collect(Collectors.toList());
        }

        Subject subject = new Subject();
        subject.setId(group.getSubjectCurriculum().getSubjectCurriculumId().getSubjectId());

        return new GroupDto(
                group.getId(),
                group.getSubjectCurriculum().getLevel(),
                subject.getName(),
                subject.getInitials(),
                group.getIdentifier(),
                group.getRemark(),
                scheduleDtos
        );
    }

    @Override
    public Group toEntity(GroupDto groupDto) {
        return null;
    }


    public Group toEntity(GroupDetailedDto groupDetailedDto, Integer academicPeriodId) {

        Group group = new Group();
        group.setIdentifier(groupDetailedDto.groupIdentifier());
        group.setRemark(groupDetailedDto.remark());
        group.setActive(Boolean.TRUE);

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                groupDetailedDto.curriculumId(),
                groupDetailedDto.SubjectId());
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupDetailedDto.itineraryId());

        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setId(academicPeriodId);
        group.setSubjectCurriculum(subjectCurriculum);
        group.setItinerary(itinerary);
        group.setAcademicPeriod(academicPeriod);

        Group savedGroup= groupRepository.save(group);

        for (ScheduleDetailedDto scheduleDetailedDtoList : groupDetailedDto.listScheduleDto()) {
            Schedule schedule = new Schedule();
            schedule.setStartTime(scheduleDetailedDtoList.startTime());
            schedule.setEndTime(scheduleDetailedDtoList.endTime());
            schedule.setWeekday(scheduleDetailedDtoList.dayOfWeek().getValue());
            schedule.setAssistant(scheduleDetailedDtoList.assistant());

            Classroom classroom = new Classroom();
            classroom.setId(scheduleDetailedDtoList.classroomId());
            schedule.setClassroom(classroom);

            schedule.setProfessor(buildProfessorById(scheduleDetailedDtoList.professorId()));
            schedule.setGroup(savedGroup);
            savedGroup.getScheduleSetList().add(schedule);
        }

        return savedGroup;
    }
    private Professor buildProfessorById(Integer professorId) {
        Professor professor = new Professor();
        if (professorId != null) {
            professor.setId(professorId);
        } else {
            professor = null;
        }

        return professor;
    }

    public Group deleteScheduleById(Integer groupId, Integer scheduleId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            Set<Schedule> schedules = group.getScheduleSetList();

            Optional<Schedule> optionalSchedule = schedules.stream()
                    .filter(schedule -> schedule.getId().equals(scheduleId))
                    .findFirst();

            if (optionalSchedule.isPresent()) {
                Schedule schedule = optionalSchedule.get();
                schedules.remove(schedule);
                scheduleService.delete(schedule);
            }
        }
        return optionalGroup.orElse(null);
    }


    @Autowired
    public void setGroupItineraryService(GroupItineraryService groupItineraryService) {
        this.groupItineraryService = groupItineraryService;
    }
}
