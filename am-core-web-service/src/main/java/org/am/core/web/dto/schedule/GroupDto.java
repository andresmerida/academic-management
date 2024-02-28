package org.am.core.web.dto.schedule;

import java.util.List;

public record GroupDto(Integer id,
                       Short level,
                       String subjectName,
                       String subjectInitials,
                       String groupIdentifier,
                       List<ScheduleDto> listScheduleDto) {
}