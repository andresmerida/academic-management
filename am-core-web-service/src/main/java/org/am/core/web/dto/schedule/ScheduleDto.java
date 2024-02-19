package org.am.core.web.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleDto(Integer id,
                          DayOfWeek dayOfWeek,
                          LocalTime startTime,
                          LocalTime endTime,
                          String classroomName,
                          String classroomInitials,
                          String professorFullName) {
}
