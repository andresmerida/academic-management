package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CareerRequest (String name, String initials,
                             String description, LocalDate creationDate, Boolean active, String code, Area area){
}
