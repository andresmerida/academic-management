package org.am.core.web.dto.admingeneral;

import java.time.LocalTime;

public record SchedulePeriodDto(LocalTime startTime, LocalTime endTime, String weekday, Boolean active, Integer areaId) {
}
