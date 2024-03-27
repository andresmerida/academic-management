package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.users.Professor;

import java.util.HashSet;
import java.util.Set;

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
    private String description;
    @ManyToMany(mappedBy = "areas")
    private Set<Professor> professors = new HashSet<>();

    public Area(String name, String initials, Boolean active, String description) {
        this.name = name;
        this.initials = initials;
        this.active = active;
        this.description = description;
    }
}
