package org.am.core.web.dto.schedule;

import java.util.List;

public record GroupDtoClss (Integer id,
                           Short level,
                           String subjectName,
                           String subjectInitials,
                           String groupIdentifier,
                           String remark,
                           List<ScheduleDto> listScheduleDto) {
}
