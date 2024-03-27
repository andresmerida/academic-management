package org.am.core.web.domain.entity.users;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Area;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professor")
@NoArgsConstructor
@Getter
@Setter
public class Professor {

    @Id
    @SequenceGenerator(name = "professor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_sequence")
    private Integer id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "second_last_name")
    private String secondLastName;
    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "area_professor",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<Area> areas = new HashSet<>();
    public Professor(String name, String lastName, String secondLastName) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
    }
}
