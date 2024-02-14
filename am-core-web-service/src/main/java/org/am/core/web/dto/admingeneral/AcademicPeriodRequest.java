package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;

public record AcademicPeriodRequest(Integer year, String name, LocalDate startDate, LocalDate endDate, Integer areaId){
}
