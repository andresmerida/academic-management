package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.*;
import org.am.core.web.dto.admingeneral.ScheduleDto;
import org.am.core.web.dto.admingeneral.ScheduleRequest;
import org.am.core.web.repository.jdbc.schedule.ScheduleJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService implements CustomMap<ScheduleDto, Schedule> {

    private final ScheduleJdbcRepository scheduleJdbcRepository;
    private final ScheduleRepository scheduleRepository;


    public List<ScheduleDto> listScheduleByAreaAndItineraryAndGroup(Integer areaId, Integer itineraryId, Integer groupId) {
        return scheduleJdbcRepository.getScheduleByAreaIdAndGroupIdAndItineraryId(areaId, itineraryId, groupId);
    }

    public ScheduleDto save(ScheduleRequest scheduleRequest){
        return toDto(scheduleRepository.save(toEntity(scheduleRequest)));
    }


    public Optional<ScheduleDto> getScheduleById(Integer id){
        return scheduleRepository.findById(id).map(this::toDto);
    }

    public ScheduleDto edit(ScheduleRequest scheduleRequest, Integer scheduleId){
        Schedule scheduleFromDB = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        scheduleFromDB.setStartTime(scheduleRequest.starTime());
        scheduleFromDB.setEndTime(scheduleRequest.endTime());
        scheduleFromDB.setWeekday(scheduleRequest.weekDay());
        scheduleFromDB.setAssistant(scheduleRequest.assistant());

        return toDto(scheduleRepository.save(scheduleFromDB));
    }
    public void delete(Integer id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public ScheduleDto toDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getWeekday(),
                schedule.getAssistant()
        );
    }

    @Override
    public Schedule toEntity(ScheduleDto scheduleDto) {
        return null;
    }

    private Schedule toEntity(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(scheduleRequest.starTime());
        schedule.setEndTime(scheduleRequest.endTime());
        schedule.setWeekday(scheduleRequest.weekDay());
        schedule.setAssistant(scheduleRequest.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleRequest.classromId());
        schedule.setClassroom(classroom);

        Professor professor = new Professor();
        professor.setId(scheduleRequest.professorId());
        schedule.setProfessor(professor);

        Group group = new Group();
        group.setId(scheduleRequest.groupId());
        schedule.setGroup(group);
        return schedule;
    }
}
