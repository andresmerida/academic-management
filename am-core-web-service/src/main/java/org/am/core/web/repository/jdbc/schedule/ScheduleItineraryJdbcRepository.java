package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.ScheduleDto;

import java.util.List;

public interface ScheduleItineraryJdbcRepository {

    List<ScheduleDto> findAllByGroupItineraryId(Integer groupId);
}
