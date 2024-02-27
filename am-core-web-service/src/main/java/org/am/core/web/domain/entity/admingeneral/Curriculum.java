package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "start_date")
    private LocalDate starDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id")
    private Career career;

    @OneToMany(mappedBy="curriculum", cascade = CascadeType.ALL)
    private Set<SubjectCurriculum> levelRequests = new HashSet<>();


    public Curriculum(String name,
                      Short minApprovedSubjeccts,
                      LocalDate starDate,
                      LocalDate endDate,
                      Boolean active,
                      Career career,
                      Set<SubjectCurriculum> levelRequests) {
        this.name = name;
        this.minApprovedSubjeccts = minApprovedSubjeccts;
        this.starDate = starDate;
        this.endDate = endDate;
        this.active = active;
        this.career = career;
        this.levelRequests = levelRequests;
    }
}
