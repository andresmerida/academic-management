package org.am.core.web.dto.admingeneral;

import java.time.LocalTime;

public record ScheduleDto(Integer id, LocalTime startTime, LocalTime endTime, String weekDay,
                          String assistant) {
}
