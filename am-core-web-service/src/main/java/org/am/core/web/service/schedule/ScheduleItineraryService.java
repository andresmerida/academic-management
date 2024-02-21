package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ScheduleItineraryRepository;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;


@Service
@RequiredArgsConstructor
public class ScheduleItineraryService implements CustomMap<ScheduleDto, ScheduleItinerary> {

    private final ScheduleItineraryRepository scheduleItineraryRepository;

    public Optional<ScheduleDto> getScheduleById(Integer id){
        return scheduleItineraryRepository.findById(id).map(this::toDto);
    }

    public ScheduleDto save(ScheduleRequest scheduleRequest){
        return toDto(scheduleItineraryRepository.save(toEntity(scheduleRequest)));
    }


    public List<ScheduleDto> saveAll(List<ScheduleRequest> scheduleRequestList, GroupItinerary savedGroup) {

        List<ScheduleItinerary> scheduleItineraries = new ArrayList<>();

        for(ScheduleRequest request : scheduleRequestList) {
            ScheduleItinerary schedule = toEntity(request);
            schedule.setGroupItinerary(savedGroup);
            scheduleItineraries.add(schedule);
        }

        return scheduleItineraryRepository.saveAll(scheduleItineraries).stream()
                .map(this::toDto)
                .collect(Collectors.toList());


    }

    public ScheduleDto edit(ScheduleRequest scheduleDto, Integer scheduleItineraryId){
        ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        scheduleItineraryFromDB.setDayOfWeek(scheduleDto.dayOfWeek().getValue());
        scheduleItineraryFromDB.setStartTime(scheduleDto.startTime());
        scheduleItineraryFromDB.setEndTime(scheduleDto.endTime());

        scheduleItineraryFromDB.setProfessor(buildProfessorById(scheduleDto.professorId()));

        scheduleItineraryFromDB.setAssistant(scheduleDto.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleDto.classroomId());
        scheduleItineraryFromDB.setClassroom(classroom);

        GroupItinerary groupItinerary = new GroupItinerary();
        groupItinerary.setId(scheduleDto.groupItineraryId());
        scheduleItineraryFromDB.setGroupItinerary(groupItinerary);

        return toDto(scheduleItineraryRepository.save(scheduleItineraryFromDB));
    }



    public List<ScheduleDto> editAll(List<ScheduleRequest> requests, Integer groupItineraryId, GroupItinerary savedGroup){

        List<ScheduleItinerary> existing = scheduleItineraryRepository.findByGroupItineraryId(groupItineraryId);
        List<ScheduleItinerary> scheduleItineraries = new ArrayList<>();

        int requestIndex = 0;
        int auxExisting = 0;

        System.out.println();

        if(existing.size() == requests.size()){
            for (ScheduleItinerary schedule : existing) {
                if (requestIndex < requests.size()) {
                    ScheduleRequest auxRequest = requests.get(requestIndex);
                    Integer scheduleId = schedule.getId();
                    ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
                    scheduleItineraryFromDB.setDayOfWeek(auxRequest.dayOfWeek().getValue());
                    scheduleItineraryFromDB.setStartTime(auxRequest.startTime());
                    scheduleItineraryFromDB.setEndTime(auxRequest.endTime());
                    scheduleItineraryFromDB.setProfessor(buildProfessorById(auxRequest.professorId()));
                    scheduleItineraryFromDB.setAssistant(auxRequest.assistant());
                    Classroom classroom = new Classroom();
                    classroom.setId(auxRequest.classroomId());
                    scheduleItineraryFromDB.setClassroom(classroom);
                    GroupItinerary groupItinerary = new GroupItinerary();
                    groupItinerary.setId(groupItineraryId);
                    scheduleItineraryFromDB.setGroupItinerary(groupItinerary);
                    scheduleItineraryRepository.save(scheduleItineraryFromDB);
                    requestIndex++;
                } else {
                    break;
                }
            }
        }else{
            if (existing.size() < requests.size()) {
                for (ScheduleItinerary schedule : existing) {
                    if (requestIndex < requests.size()) {
                        ScheduleRequest auxRequest = requests.get(requestIndex);
                        Integer scheduleId = schedule.getId();
                        ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
                        scheduleItineraryFromDB.setDayOfWeek(auxRequest.dayOfWeek().getValue());
                        scheduleItineraryFromDB.setStartTime(auxRequest.startTime());
                        scheduleItineraryFromDB.setEndTime(auxRequest.endTime());
                        scheduleItineraryFromDB.setProfessor(buildProfessorById(auxRequest.professorId()));
                        scheduleItineraryFromDB.setAssistant(auxRequest.assistant());
                        Classroom classroom = new Classroom();
                        classroom.setId(auxRequest.classroomId());
                        scheduleItineraryFromDB.setClassroom(classroom);
                        GroupItinerary groupItinerary = new GroupItinerary();
                        groupItinerary.setId(groupItineraryId);
                        scheduleItineraryFromDB.setGroupItinerary(groupItinerary);
                        scheduleItineraryRepository.save(scheduleItineraryFromDB);
                        requestIndex++;
                        auxExisting++;
                    }
                }
                List<ScheduleRequest> remainingRequests = requests.subList(requestIndex, requests.size());
                for (ScheduleRequest remainingRequest : remainingRequests) {
                        ScheduleItinerary schedule = toEntity(remainingRequest);
                        schedule.setGroupItinerary(savedGroup);
                        scheduleItineraryRepository.save(schedule);

                }
            }else {

                if(existing.size() > requests.size()){
                    for (ScheduleItinerary schedule : existing) {
                        if (requestIndex < requests.size()) {
                            ScheduleRequest auxRequest = requests.get(requestIndex);
                            Integer scheduleId = schedule.getId();
                            ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleId)
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
                            scheduleItineraryFromDB.setDayOfWeek(auxRequest.dayOfWeek().getValue());
                            scheduleItineraryFromDB.setStartTime(auxRequest.startTime());
                            scheduleItineraryFromDB.setEndTime(auxRequest.endTime());
                            scheduleItineraryFromDB.setProfessor(buildProfessorById(auxRequest.professorId()));
                            scheduleItineraryFromDB.setAssistant(auxRequest.assistant());
                            Classroom classroom = new Classroom();
                            classroom.setId(auxRequest.classroomId());
                            scheduleItineraryFromDB.setClassroom(classroom);
                            GroupItinerary groupItinerary = new GroupItinerary();
                            groupItinerary.setId(groupItineraryId);
                            scheduleItineraryFromDB.setGroupItinerary(groupItinerary);
                            scheduleItineraryRepository.save(scheduleItineraryFromDB);
                            requestIndex++;
                            auxExisting++;
                        }
                    }
                    for (int i = requestIndex; i < existing.size(); i++) {
                        ScheduleItinerary schedule = existing.get(i);
                        scheduleItineraryRepository.delete(schedule);
                    }
                }
            }

        }


        List<ScheduleItinerary> finalList = scheduleItineraryRepository.findByGroupItineraryId(groupItineraryId);
        return finalList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



    public void delete(Integer id) {
        scheduleItineraryRepository.deleteById(id);
    }
    public void deleteAll( List<ScheduleItinerary> schedules) {
        scheduleItineraryRepository.deleteAll(schedules);
    }
    @Override
    public ScheduleDto toDto(ScheduleItinerary scheduleItinerary) {
        String fullName = NOT_ASSIGNED_YET;
        if (scheduleItinerary.getProfessor() != null) {
            fullName = getFullName(scheduleItinerary.getProfessor().getName(),
                    scheduleItinerary.getProfessor().getLastName(),
                    scheduleItinerary.getProfessor().getSecondLastName());
        } else if (scheduleItinerary.getAssistant() != null && !scheduleItinerary.getAssistant().isEmpty()) {
            fullName = scheduleItinerary.getAssistant();
        }

        return new ScheduleDto(
                scheduleItinerary.getId(),
                DayOfWeek.of(scheduleItinerary.getDayOfWeek()).toString(),
                scheduleItinerary.getStartTime(),
                scheduleItinerary.getEndTime(),
                scheduleItinerary.getClassroom().getName(),
                scheduleItinerary.getClassroom().getInitials(),
                fullName
        );
    }

    @Override
    public ScheduleItinerary toEntity(ScheduleDto scheduleDto) {
        return null;
    }

    public ScheduleItinerary toEntity(ScheduleRequest scheduleRequest) {
        ScheduleItinerary scheduleItinerary = new ScheduleItinerary();

        scheduleItinerary.setDayOfWeek(scheduleRequest.dayOfWeek().getValue());
        scheduleItinerary.setStartTime(scheduleRequest.startTime());
        scheduleItinerary.setEndTime(scheduleRequest.endTime());

        scheduleItinerary.setProfessor(buildProfessorById(scheduleRequest.professorId()));

        scheduleItinerary.setAssistant(scheduleRequest.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleRequest.classroomId());
        scheduleItinerary.setClassroom(classroom);

        GroupItinerary groupItinerary = new GroupItinerary();
        groupItinerary.setId(scheduleRequest.groupItineraryId());
        scheduleItinerary.setGroupItinerary(groupItinerary);

        return scheduleItinerary;
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

    public List<ScheduleItinerary> findSchedleByGroupId(Integer groupId){
        return scheduleItineraryRepository.findByGroupItineraryId(groupId);
    }
}
