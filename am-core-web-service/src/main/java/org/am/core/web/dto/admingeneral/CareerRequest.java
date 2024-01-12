package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;

import java.time.LocalDateTime;

public record CareerRequest (String name, String initials,
                             String description,LocalDateTime creationDate,Boolean active, Area areaID){
}
