package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.ClassManAuxDto;
import org.am.core.web.dto.schedule.ClassManDto;
import org.am.core.web.dto.schedule.GroupDetailedAuxDto;

import java.util.List;

public interface GroupItineraryClassJdbcRepository {

    List<ClassManAuxDto> getClassManByCareer(Integer careerId, Integer itineraryId);
    List<GroupDetailedAuxDto> getGroupItinerariesDetailedByCareer(Integer careerId, Integer itineraryId);
}
