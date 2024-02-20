package org.am.core.web.dto.schedule;

import java.time.LocalTime;

public record GroupAuxDto(Integer groupItineraryId,
                          Short level,
                          String subjectName,
                          String subjectInitials,
                          String groupIdentifier,
                          Integer dayOfWeek,
                          LocalTime startTime,
                          LocalTime endTime,
                          String assistant,
                          String classroomName,
                          String classroomInitials,
                          String professorName,
                          String professorLastName,
                          String professorSecondLastName,
                          Integer scheduleItineraryId) {
}
