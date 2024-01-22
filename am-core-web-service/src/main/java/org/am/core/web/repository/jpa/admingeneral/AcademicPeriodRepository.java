package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod,Integer> {
    List<AcademicPeriod> findAllByArea_IdOrderById(Integer areaID);
}
