package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.GroupAuxDto;
import org.am.core.web.dto.schedule.GroupDetailedDto;

import java.util.List;

public interface GroupItineraryJdbcRepository {
    List<GroupAuxDto> getGroupItinerariesByCareer(Integer careerId, Integer itineraryId);
    List<GroupDetailedDto> getGroupItinerariesDetailedByCareer(Integer careerId, Integer itineraryId);
}
