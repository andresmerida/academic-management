package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.*;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jdbc.schedule.GroupJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService implements CustomMap <GroupDto, Group> {

    private final GroupRepository groupRepository;
    private final GroupJdbcRepository groupJdbcRepository;
    private final ScheduleService scheduleService;
    private GroupItineraryService groupItineraryService;

    public List<GroupDto> listGroupsByCareerAndAcademicPeriod(Integer careerId, Integer academicPeriodId) {
        List<GroupDto> groupDtoList = new ArrayList<>();
        GroupAuxDto previousGroupAuxDto = null;
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (GroupAuxDto auxDto: groupJdbcRepository.getGroupsDetailedByCareer(careerId, academicPeriodId)) {
            if (previousGroupAuxDto != null && previousGroupAuxDto.groupItineraryId().intValue() != auxDto.groupItineraryId().intValue()) {
                groupDtoList.add(new GroupDto(
                        previousGroupAuxDto.groupItineraryId(),
                        previousGroupAuxDto.level(),
                        previousGroupAuxDto.subjectName(),
                        previousGroupAuxDto.subjectInitials(),
                        previousGroupAuxDto.groupIdentifier(),
                        previousGroupAuxDto.groupRemark(),

                        new ArrayList<>(scheduleDtoList)));
                scheduleDtoList.clear();
            }

            String fullName = NOT_ASSIGNED_YET;
            if (auxDto.professorName() != null) {
                fullName = getFullName(auxDto.professorName(), auxDto.professorLastName(), auxDto.professorSecondLastName());
            } else if (auxDto.assistant() != null && !auxDto.assistant().isEmpty()) {
                fullName = auxDto.assistant();
            }

            scheduleDtoList.add(new ScheduleDto(
                    auxDto.scheduleItineraryId(),
                    DayOfWeek.of(auxDto.dayOfWeek()).toString(),
                    auxDto.startTime(),
                    auxDto.endTime(),
                    auxDto.classroomName(),
                    auxDto.classroomInitials(),
                    fullName
            ));
            previousGroupAuxDto = auxDto;
        }
        if (previousGroupAuxDto != null) {
            groupDtoList.add(new GroupDto(
                    previousGroupAuxDto.groupItineraryId(),
                    previousGroupAuxDto.level(),
                    previousGroupAuxDto.subjectName(),
                    previousGroupAuxDto.subjectInitials(),
                    previousGroupAuxDto.groupIdentifier(),
                    previousGroupAuxDto.groupRemark(),
                    new ArrayList<>(scheduleDtoList)));
            scheduleDtoList = null;
        }
        return groupDtoList;
    }

   public void generateGroupsFromItinerary(Integer careerId, Integer itineraryId,Integer academicPeriodId) {
        List<GroupDetailedDto> groupDetailedAuxDtoList = groupItineraryService.getGroupsScheduleByCareerAndItinerary(careerId, itineraryId);
        for(GroupDetailedDto groupDetailedDto: groupDetailedAuxDtoList){
            groupRepository.save(toEntity(groupDetailedDto, academicPeriodId));
        }

    }

    public GroupDto save(GroupRequest groupRequest, Integer academicPeriodId) {
        return toDto(groupRepository.save(toEntityGroup(groupRequest, academicPeriodId)));
    }

    public GroupDto edit(GroupRequest dto, Integer id, Integer academicPeriodId) {
        int requestIndex = 0;

        Group groupFromDb = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id for curriculumId"));
        groupFromDb.setIdentifier(dto.identifier());
        groupFromDb.setRemark(dto.remark());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                dto.curriculumId(),
                dto.subjectId());
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        groupFromDb.setSubjectCurriculum(subjectCurriculum);
        groupFromDb.setItinerary(null);

        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setId(academicPeriodId);
        groupFromDb.setAcademicPeriod(academicPeriod);

        Set<Schedule> newScheduleSet = new HashSet<>();
        Set<Schedule> scheduleSet = groupFromDb.getScheduleSetList();

        if (scheduleSet.size() == dto.listSchedule().size()){
            for (Schedule scheduleAux : scheduleSet) {
                if (requestIndex < dto.listSchedule().size()) {
                    ScheduleRequest auxRequest = dto.listSchedule().get(requestIndex);

                    scheduleAux.setStartTime(auxRequest.startTime());
                    scheduleAux.setEndTime(auxRequest.endTime());
                    scheduleAux.setWeekday(auxRequest.dayOfWeek().getValue());
                    scheduleAux.setAssistant(auxRequest.assistant());

                    Classroom classroom = new Classroom();
                    classroom.setId(auxRequest.classroomId());
                    scheduleAux.setClassroom(classroom);

                    scheduleAux.setProfessor(buildProfessorById(auxRequest.professorId()));
                    scheduleAux.setGroup(groupFromDb);

                    newScheduleSet.add(scheduleAux);
                    requestIndex++;
                } else {
                    break;
                }
            }
        } else {
            if (scheduleSet.size() < dto.listSchedule().size()){
                for (Schedule scheduleAux : scheduleSet) {
                    if (requestIndex < dto.listSchedule().size()) {
                        ScheduleRequest auxRequest = dto.listSchedule().get(requestIndex);

                        scheduleAux.setStartTime(auxRequest.startTime());
                        scheduleAux.setEndTime(auxRequest.endTime());
                        scheduleAux.setWeekday(auxRequest.dayOfWeek().getValue());
                        scheduleAux.setAssistant(auxRequest.assistant());

                        Classroom classroom = new Classroom();
                        classroom.setId(auxRequest.classroomId());
                        scheduleAux.setClassroom(classroom);

                        scheduleAux.setProfessor(buildProfessorById(auxRequest.professorId()));
                        scheduleAux.setGroup(groupFromDb);

                        newScheduleSet.add(scheduleAux);
                        requestIndex++;
                    }
                }
                List<ScheduleRequest> remainingRequests = dto.listSchedule().subList(requestIndex,
                        dto.listSchedule().size());
                for (ScheduleRequest scheduleDetailedDtoList : remainingRequests) {
                    Schedule schedule = new Schedule();
                    schedule.setStartTime(scheduleDetailedDtoList.startTime());
                    schedule.setEndTime(scheduleDetailedDtoList.endTime());
                    schedule.setWeekday(scheduleDetailedDtoList.dayOfWeek().getValue());
                    schedule.setAssistant(scheduleDetailedDtoList.assistant());

                    Classroom classroom = new Classroom();
                    classroom.setId(scheduleDetailedDtoList.classroomId());
                    schedule.setClassroom(classroom);

                    schedule.setProfessor(buildProfessorById(scheduleDetailedDtoList.professorId()));
                    schedule.setGroup(groupFromDb);

                    newScheduleSet.add(schedule);
                }
            }else {
                if(scheduleSet.size() > dto.listSchedule().size()) {
                    for (Schedule scheduleAux : scheduleSet) {
                        if (requestIndex < dto.listSchedule().size()) {
                            ScheduleRequest auxRequest = dto.listSchedule().get(requestIndex);

                            scheduleAux.setStartTime(auxRequest.startTime());
                            scheduleAux.setEndTime(auxRequest.endTime());
                            scheduleAux.setWeekday(auxRequest.dayOfWeek().getValue());
                            scheduleAux.setAssistant(auxRequest.assistant());

                            Classroom classroom = new Classroom();
                            classroom.setId(auxRequest.classroomId());
                            scheduleAux.setClassroom(classroom);

                            scheduleAux.setProfessor(buildProfessorById(auxRequest.professorId()));
                            scheduleAux.setGroup(groupFromDb);

                            newScheduleSet.add(scheduleAux);
                            requestIndex++;
                        }
                    }
                    Iterator<Schedule> iterator = scheduleSet.iterator();
                    int count = 0;
                    while (iterator.hasNext()) {
                        Schedule schedule = iterator.next();
                        if (count >= requestIndex) {
                            scheduleService.delete(schedule.getId());
                            iterator.remove();
                        }
                        count++;
                    }
                }
            }
        }
        groupFromDb.setScheduleSetList(newScheduleSet);

        return toDto(groupRepository.save(groupFromDb));
    }

    @Transactional(readOnly = true)
    public String suggestGroupIdentifier(Integer subjectId, Integer curriculumId) {
        return groupJdbcRepository.suggestGroupIdentifier(subjectId, curriculumId);
    }

    public void delete(Integer id) {
        groupRepository.deleteById(id);

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

        Curriculum curriculum = new Curriculum();
        curriculum.setId(group.getSubjectCurriculum().getSubjectCurriculumId().getCurriculumId());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                curriculum.getId(),
                subject.getId());
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);

        return new GroupDto(
                group.getId(),
                subjectCurriculum.getLevel(),
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
        group.setSubjectCurriculum(subjectCurriculum);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupDetailedDto.itineraryId());
        group.setItinerary(itinerary);

        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setId(academicPeriodId);
        group.setAcademicPeriod(academicPeriod);

        Set<Schedule> scheduleSet = new HashSet<>();
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
            schedule.setGroup(group);

            scheduleSet.add(schedule);
        }
        group.setScheduleSetList(scheduleSet);

        return group;
    }

    public Group toEntityGroup(GroupRequest groupRequest, Integer academicPeriodId) {

        Group group = new Group();
        group.setIdentifier(groupRequest.identifier());
        group.setRemark(groupRequest.remark());
        group.setActive(Boolean.TRUE);

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                groupRequest.curriculumId(),
                groupRequest.subjectId());
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        group.setSubjectCurriculum(subjectCurriculum);

        group.setItinerary(null);

        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setId(academicPeriodId);
        group.setAcademicPeriod(academicPeriod);

        Set<Schedule> scheduleSet = new HashSet<>();
        for (ScheduleRequest scheduleDetailedDtoList : groupRequest.listSchedule()) {
            Schedule schedule = new Schedule();
            schedule.setStartTime(scheduleDetailedDtoList.startTime());
            schedule.setEndTime(scheduleDetailedDtoList.endTime());
            schedule.setWeekday(scheduleDetailedDtoList.dayOfWeek().getValue());
            schedule.setAssistant(scheduleDetailedDtoList.assistant());

            Classroom classroom = new Classroom();
            classroom.setId(scheduleDetailedDtoList.classroomId());
            schedule.setClassroom(classroom);

            schedule.setProfessor(buildProfessorById(scheduleDetailedDtoList.professorId()));
            schedule.setGroup(group);

            scheduleSet.add(schedule);
        }
        group.setScheduleSetList(scheduleSet);

        return group;
    }


    @Autowired
    public void setGroupItineraryService(GroupItineraryService groupItineraryService) {
        this.groupItineraryService = groupItineraryService;
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
}