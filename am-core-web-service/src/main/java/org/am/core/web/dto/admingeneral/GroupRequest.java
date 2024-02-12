package org.am.core.web.dto.admingeneral;

public record  GroupRequest(String identifier, String remark, Integer curriculumId,
                            Integer subjectId, Integer itineraryId) {
}
