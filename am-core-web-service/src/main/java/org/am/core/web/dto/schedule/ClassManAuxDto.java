package org.am.core.web.dto.schedule;

import java.time.LocalTime;

public record ClassManAuxDto (Integer groupItineraryId,
                             Short level,
                             String careerName,
                             Integer academicPeriodId,
                             String subjectName,
                             String subjectInitials,
                             String groupIdentifier,
                             String groupRemark,
                             Integer dayOfWeek,
                             LocalTime startTime,
                             LocalTime endTime,
                             String assistant,
                             String classroomName,
                             String classroomInitials,
                             String professorName,
                             String professorLastName,
                             String professorSecondLastName,
                             Integer scheduleItineraryId){
}