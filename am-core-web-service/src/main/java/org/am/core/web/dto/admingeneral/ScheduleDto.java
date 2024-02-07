package org.am.core.web.dto.admingeneral;

import java.time.LocalTime;

public record ScheduleDto(Integer id, LocalTime starTime, LocalTime endTime, String weekDay,
                          String assistant) {
}
