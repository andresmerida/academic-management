package org.am.core.web.dto.schedule;

import java.time.LocalTime;

public record ScheduleDetailedDto (LocalTime startTime,
                                   LocalTime endTime,
                                   String dayOfWeek,
                                   String assistant,
                                   Integer classroomId,
                                   Integer professorId,
                                   Integer groupId) {
}
