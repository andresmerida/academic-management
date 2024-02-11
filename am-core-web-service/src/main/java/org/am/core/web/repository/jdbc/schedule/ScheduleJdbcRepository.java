package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.admingeneral.ScheduleDto;

import java.util.List;

public interface ScheduleJdbcRepository {

    List<ScheduleDto> getScheduleByAreaIdAndGroupIdAndItineraryId(Integer areaId, Integer itineraryId, Integer groupId);
}
