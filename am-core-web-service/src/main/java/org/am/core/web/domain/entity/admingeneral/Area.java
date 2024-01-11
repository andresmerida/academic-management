package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "area")
@NoArgsConstructor
@Getter
@Setter
public class Area {
    @Id
    @SequenceGenerator(name = "area_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_sequence")
    private Integer id;
    private String name;
    private String initials;
    private Boolean active;

    public Area(String name, String initials, Boolean active) {
        this.name = name;
        this.initials = initials;
        this.active = active;
    }
}
