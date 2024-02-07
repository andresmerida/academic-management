package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Professor(String name, String lastName, String secondLastName) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
    }
}
