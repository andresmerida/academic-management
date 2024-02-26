package org.am.core.web.dto.admingeneral;

import java.util.List;

public record LevelRequest(Integer levelIdentifier,
                           String levelName,
                           List<SubjectCurriculumRequest> subjectCurriculumList) {
}
