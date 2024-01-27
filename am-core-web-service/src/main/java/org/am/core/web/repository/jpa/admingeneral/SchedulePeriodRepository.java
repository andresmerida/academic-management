package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.SchedulePeriod;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriodId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod,SchedulePeriodId> {
}
