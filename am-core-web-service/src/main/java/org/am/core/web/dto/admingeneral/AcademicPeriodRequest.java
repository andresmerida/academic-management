package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.time.Year;


public record AcademicPeriodRequest(Integer year, String name, LocalDate startDate, LocalDate endDate, Integer areaId){
}
