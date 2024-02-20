package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.GroupAuxDto;

import java.util.List;

public interface GroupItineraryJdbcRepository {
    List<GroupAuxDto> getGroupItinerariesByCareer(Integer careerId, Integer itineraryId);
}
