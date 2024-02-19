package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ScheduleItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.am.core.web.util.CommonUtils.getFullName;


@Service
@RequiredArgsConstructor
public class ScheduleItineraryService implements CustomMap<ScheduleDto, ScheduleItinerary> {

    private final ScheduleItineraryRepository scheduleItineraryRepository;

    public Optional<ScheduleDto> getItineraryById(Integer id){
        return scheduleItineraryRepository.findById(id).map(this::toDto);
    }

    public ScheduleDto save(ScheduleRequest scheduleRequest){
        return toDto(scheduleItineraryRepository.save(toEntity(scheduleRequest)));
    }

    public List<ScheduleDto> saveAll(List<ScheduleRequest> scheduleRequestList) {
        List<ScheduleItinerary> scheduleItineraryList = scheduleRequestList.stream().map(this::toEntity).toList();
        return scheduleItineraryRepository.saveAll(scheduleItineraryList)
                .stream()
                .map(this::toDto).toList();
    }

    public ScheduleDto edit(ScheduleRequest scheduleDto, Integer scheduleItineraryId){
        ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        scheduleItineraryFromDB.setDayOfWeek(scheduleDto.dayOfWeek());
        scheduleItineraryFromDB.setStartTime(scheduleDto.start_time());
        scheduleItineraryFromDB.setEndTime(scheduleDto.end_time());

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

    @Override
    public ScheduleDto toDto(ScheduleItinerary scheduleItinerary) {
        String fullName = "";
        if (scheduleItinerary.getProfessor() != null) {
            fullName = getFullName(scheduleItinerary.getProfessor().getName(),
                    scheduleItinerary.getProfessor().getLastName(),
                    scheduleItinerary.getProfessor().getSecondLastName());
        } else if (scheduleItinerary.getAssistant() != null && !scheduleItinerary.getAssistant().isEmpty()) {
            fullName = scheduleItinerary.getAssistant();
        }

        return new ScheduleDto(
                scheduleItinerary.getId(),
                scheduleItinerary.getDayOfWeek(),
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

        scheduleItinerary.setDayOfWeek(scheduleRequest.dayOfWeek());
        scheduleItinerary.setStartTime(scheduleRequest.start_time());
        scheduleItinerary.setEndTime(scheduleRequest.end_time());

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
}
