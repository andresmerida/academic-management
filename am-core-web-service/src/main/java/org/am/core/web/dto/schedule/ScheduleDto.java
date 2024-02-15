package org.am.core.web.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleDto(DayOfWeek dayOfWeek,
                          LocalTime start_time,
                          LocalTime end_time,
                          String classroomName,
                          String classroomInitials,
                          String professorFullName) {
}
