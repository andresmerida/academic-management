package org.am.core.web.dto.schedule;

public record BulkCreateGroupRequest (Integer careerId,
                                      Integer itineraryId,
                                      Integer academicPeriodId) {
}
