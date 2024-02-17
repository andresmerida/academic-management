package org.am.core.web.dto.schedule;

public record SubjectCurriculumRequest(Integer curriculumId,
                                       Integer subjectId,
                                       Short level,
                                       Boolean optional,
                                       String path,
                                       Short workload,
                                       Boolean active) {
}
