package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.domain.entity.schedule.Itinerary;
import org.am.core.web.domain.entity.schedule.Schedule;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;
import org.am.core.web.domain.entity.schedule.SubjectCurriculumId;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.schedule.GroupDetailedAuxDto;
import org.am.core.web.dto.schedule.GroupDetailedDto;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.ScheduleDetailedDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupRepository;
import org.am.core.web.repository.jpa.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.am.core.web.dto.schedule.BulkCreateGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService implements CustomMap <GroupDto, Group> {

    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;

    private GroupItineraryService groupItineraryService;


    public void generateGroupsFromItinerary(BulkCreateGroupRequest dto) {

        int count = 0;
        int size = 100;
        List<Schedule> listSchedule = new ArrayList<>();
        List<GroupDetailedDto> groupDetailedAuxDtoList = groupItineraryService.getGroupsScheduleByCareerAndItinerary(dto.careerId(),dto.itineraryId());
        for(GroupDetailedDto groupDetailedDto: groupDetailedAuxDtoList){
            Group group =groupRepository.save(toEntity(groupDetailedDto, dto.academicPeriodId()));
            for(ScheduleDetailedDto scheduleDetailedDto: groupDetailedDto.listScheduleDto()){
                listSchedule.add(toEntity(scheduleDetailedDto, group.getId()));
                if(count==size){
                    saveBatch(listSchedule);
                    count = 0;
                }
                count++;
            }

        }

    }

    private void saveBatch(List<Schedule> batchSchedules) {
        scheduleRepository.saveAll(batchSchedules);
    }

    @Override
    public GroupDto toDto(Group group) {
        return null;
    }

    @Override
    public Group toEntity(GroupDto groupDto) {
        return null;
    }

    private Schedule toEntity(ScheduleDetailedDto dto, Integer groupId){
        Schedule schedule = new Schedule();
        schedule.setStartTime(dto.startTime());
        schedule.setEndTime(dto.endTime());
        schedule.setWeekday(dto.dayOfWeek());
        schedule.setAssistant(dto.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(dto.classroomId());
        schedule.setClassroom(classroom);

        Professor professor = new Professor();
        professor.setId(dto.professorId());
        schedule.setProfessor(professor);

        Group group = new Group();
        group.setId(groupId);
        schedule.setGroup(group);
        return schedule;
    }
    private Group toEntity(GroupDetailedDto groupDetailedDto, Integer academicPeriodId) {
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
        return group;
    }

    @Autowired
    public void setGroupItineraryService(GroupItineraryService groupItineraryService) {
        this.groupItineraryService = groupItineraryService;
    }
}
