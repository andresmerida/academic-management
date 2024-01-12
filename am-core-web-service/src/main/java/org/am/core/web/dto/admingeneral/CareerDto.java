package org.am.core.web.dto.admingeneral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Area;

import java.time.LocalDateTime;


public record CareerDto (Integer id, String name, String initials,
                         String description, LocalDateTime creationDate,Boolean active, Area areaID){
}
