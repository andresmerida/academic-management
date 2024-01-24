package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record CareerDto (Integer id, String name, String initials,
<<<<<<< HEAD
                         String description, LocalDate creationDate, Boolean active, String code, Area area){
=======
                         String description, LocalDateTime creationDate,Boolean active, String code, Area area){
>>>>>>> dev
}
