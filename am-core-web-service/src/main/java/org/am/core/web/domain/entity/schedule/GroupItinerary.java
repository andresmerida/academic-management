package org.am.core.web.domain.entity.schedule;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="group_itinerary")
public class GroupItinerary {

    @Id
    @SequenceGenerator(name = "group_itinerary_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_itinerary_sequence")
    private Integer id;

    private String identifier;

    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "curriculum_id", referencedColumnName = "curriculum_id"),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    })
    private SubjectCurriculum subjectCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    public GroupItinerary(String identifier, String remark, SubjectCurriculum subjectCurriculum, Itinerary itinerary) {
        this.identifier = identifier;
        this.remark = remark;
        this.subjectCurriculum = subjectCurriculum;
        this.itinerary = itinerary;
    }
}
