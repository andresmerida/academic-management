package org.am.core.web.domain.entity.users;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<AreaProfessor> listAreaProfessor = new ArrayList<>();

    public Professor(Integer id, String name, String lastName, String secondLastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
    }
}
