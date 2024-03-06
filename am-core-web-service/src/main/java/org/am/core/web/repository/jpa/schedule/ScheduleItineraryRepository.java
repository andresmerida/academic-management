package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.ScheduleItinerary;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleItineraryRepository extends JpaRepository<ScheduleItinerary, Integer> {
    List<ScheduleItinerary> findByGroupItineraryId (Integer groupId);
}
