package org.am.core.web.domain.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Area;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "area_professor")
public class AreaProfessor {
    @EmbeddedId
    private AreaProfessorId areaProfessorId;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("areaId")
    @JoinColumn(name = "area_id")
    private Area area;

}
