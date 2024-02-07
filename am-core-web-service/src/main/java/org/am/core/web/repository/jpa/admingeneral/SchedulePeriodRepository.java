package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.SchedulePeriod;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriodId;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, SchedulePeriodId> {

    List<SchedulePeriod> findByAreaId(Integer areaId);

    @Transactional
    @Modifying
    void deleteByAreaId(Integer areaId);
}

