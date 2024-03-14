package org.am.core.web.domain.entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AreaProfessorId implements Serializable {
    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "professor_id")
    private Integer professorId;

}
