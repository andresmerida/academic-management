package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jdbc.schedule.ScheduleItineraryJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ScheduleItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ScheduleItineraryService implements CustomMap<ScheduleDto, ScheduleItinerary> {

    private final ScheduleItineraryRepository scheduleItineraryRepository;
    private final ScheduleItineraryJdbcRepository scheduleItineraryJdbcRepository;




    public List<ScheduleDto> listSchedulesItineraryByGroupId(Integer groupId) {
        return scheduleItineraryJdbcRepository.findAllByGroupItineraryId(groupId);
    }

    public Optional<ScheduleDto> getItineraryById(Integer id){
        return scheduleItineraryRepository.findById(id).map(this::toDto);
    }

    public ScheduleDto save(ScheduleRequest scheduleRequest){
        return toDto(scheduleItineraryRepository.save(toEntity(scheduleRequest)));
    }

    public ScheduleDto edit(ScheduleRequest scheduleDto, Integer scheduleItineraryId){
        ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        scheduleItineraryFromDB.setDayOfWeek(scheduleDto.dayOfWeek());
        scheduleItineraryFromDB.setStartTime(scheduleDto.start_time());
        scheduleItineraryFromDB.setEndTime(scheduleDto.end_time());

        Professor professor = new Professor();
        professor.setId(scheduleDto.professorId());
        scheduleItineraryFromDB.setProfessor(professor);

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
        String professorFullName = scheduleItinerary.getProfessor().getName() + " " +
                scheduleItinerary.getProfessor().getLastName() + " " +
                scheduleItinerary.getProfessor().getSecondLastName();

        return new ScheduleDto(
                scheduleItinerary.getId(),
                scheduleItinerary.getDayOfWeek(),
                scheduleItinerary.getStartTime(),
                scheduleItinerary.getEndTime(),
                scheduleItinerary.getClassroom().getName(),
                scheduleItinerary.getClassroom().getInitials(),
                professorFullName
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

        Professor professor = new Professor();
        professor.setId(scheduleRequest.professorId());
        scheduleItinerary.setProfessor(professor);

        scheduleItinerary.setAssistant(scheduleRequest.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleRequest.classroomId());
        scheduleItinerary.setClassroom(classroom);

        GroupItinerary groupItinerary = new GroupItinerary();
        groupItinerary.setId(scheduleRequest.groupItineraryId());
        scheduleItinerary.setGroupItinerary(groupItinerary);

        return scheduleItinerary;
    }


}
