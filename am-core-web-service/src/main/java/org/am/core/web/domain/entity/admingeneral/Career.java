package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "career")
@NoArgsConstructor
@Getter
@Setter
public class Career {
    @Id
    @SequenceGenerator(name = "career_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "career_sequence")
    private Integer id;
    private String name;
    private String initials;
    private String description;
    private Boolean active;
    private String code;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id")
    private Area area;

    public Career(String name, String initials, String description, Boolean active, String code, LocalDateTime creationDate, Area area) {
        this.name = name;
        this.initials = initials;
        this.description = description;
        this.active = active;
        this.code = code;
        this.creationDate = creationDate;
        this.area = area;
    }
}
