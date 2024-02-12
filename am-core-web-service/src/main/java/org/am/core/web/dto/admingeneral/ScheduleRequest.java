package org.am.core.web.dto.admingeneral;

import java.time.LocalTime;

public record ScheduleRequest(LocalTime starTime, LocalTime endTime, String weekDay,
                              String assistant, Integer classromId, Integer professorId, Integer groupId) {
}
