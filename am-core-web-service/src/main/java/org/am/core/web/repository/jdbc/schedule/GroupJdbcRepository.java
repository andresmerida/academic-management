package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.GroupAuxDto;
import org.am.core.web.dto.schedule.GroupDetailedAuxDto;

import java.util.List;

public interface GroupJdbcRepository {

    List<GroupAuxDto> getGroupsDetailedByCareer(Integer careerId, Integer academicPeriodId);
}
