package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="curriculum")
public class Curriculum {

    @Id
    @SequenceGenerator(name = "curriculum_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculum_sequence")
    private Integer id;

    private String name;

    @Column(name = "min_approved_subjects")
    private Short minApprovedSubjeccts;

    @Column(name = "star_date")
    private LocalDate starDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id")
    private Career career;


}
