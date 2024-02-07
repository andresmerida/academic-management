package org.am.core.web.domain.entity.admingeneral;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.schedule.Itinerary;

import java.time.LocalDate;

@Entity
@Table(name = "group_table")
@NoArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @SequenceGenerator(name = "group_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_sequence")
    private Integer id;
    private String identifier;
    private String remark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;


}
