package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.time.Year;

public record AcademicPeriodRequest(Year year, String name, LocalDate startDate, LocalDate endDate){
}
