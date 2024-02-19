package org.am.core.web.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleRequest(DayOfWeek dayOfWeek,
                              LocalTime start_time,
                              LocalTime end_time,
                              Integer professorId,
                              String assistant,
                              Integer classroomId,
                              Integer groupItineraryId) {
}
