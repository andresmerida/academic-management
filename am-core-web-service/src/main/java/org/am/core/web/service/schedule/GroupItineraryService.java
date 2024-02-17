package org.am.core.web.service.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.schedule.GroupItinerary;
import org.am.core.web.domain.entity.schedule.Professor;
import org.am.core.web.domain.entity.schedule.ScheduleItinerary;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.dto.schedule.ScheduleRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupItineraryService implements CustomMap<GroupDto, GroupRequest> {
    private final GroupItineraryRepository groupItineraryRepository;




    @Override
    public GroupDto toDto(GroupItinerary groupItinerary) {
        List<ScheduleDto> scheduleDtos = groupItinerary.getScheduleItineraries()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new ScheduleDto(
                groupItinerary.getSubjectCurriculum().getLevel(),
                groupItinerary.getSubjectCurriculum().getName(),
                groupItinerary.getSubjectCurriculum().getSubject().getInitials(),
                scheduleItinerary.getDayOfWeek(),
                scheduleItinerary.getStartTime(),
                scheduleItinerary.getEndTime(),
                scheduleItinerary.getClassroom().getName(),
                scheduleItinerary.getClassroom().getInitials(),
                professorFullName
        );
    }



}
