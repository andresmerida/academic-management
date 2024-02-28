package org.am.core.web.dto.schedule;

import java.util.List;

public record ClassManDto(Integer id,
                          Short level,
                          String subjectName,
                          String subjectInitials,
                          String careerName,
                          List<ScheduleDto> listScheduleDto) {


}
