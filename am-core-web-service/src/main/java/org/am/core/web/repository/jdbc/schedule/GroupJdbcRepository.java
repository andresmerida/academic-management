package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.admingeneral.GroupDto;

import java.util.List;

public interface GroupJdbcRepository {
    List<GroupDto> getGroupsByAreaIdAndItineraryId(Integer areaId, Integer itineraryId);
}

