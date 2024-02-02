package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "academic_period")
@NoArgsConstructor
@Getter
@Setter
public class AcademicPeriod {
    @Id
    @SequenceGenerator(name = "academic_period_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academic_period_sequence")
    private Integer id;
    private Year year;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    private Area area;

    public AcademicPeriod(Year year, String name, LocalDate startDate, LocalDate endDate, Area area) {
        this.year=year;
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.active=true;
        this.area=area;
    }
}
