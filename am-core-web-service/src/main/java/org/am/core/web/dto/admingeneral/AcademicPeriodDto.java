package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.time.Year;

public record AcademicPeriodDto (Integer id, Year year, String name, LocalDate startDate, LocalDate endDate,Boolean active){
}
