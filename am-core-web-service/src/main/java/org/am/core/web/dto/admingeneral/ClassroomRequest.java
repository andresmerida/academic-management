package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;

public record ClassroomRequest(String initials, String name, String type,
                               String address, Boolean active, Integer areaId) {
}
