package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;
import org.am.core.web.domain.entity.admingeneral.Curriculum;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="class_group")
public class ClassMan {
    @Id
    @SequenceGenerator(name = "class_group_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_group_sequence")
    private Integer id;

    private String identifier;
    private String remark;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "curriculum_id", referencedColumnName = "curriculum_id"),
            @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    })
    private SubjectCurriculum subjectCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriod academicPeriod;

    @OneToMany(mappedBy="classMan", cascade = CascadeType.MERGE)
    private List<ScheduleItinerary> scheduleItineraries;

    @OneToMany(mappedBy="group", cascade = CascadeType.MERGE)
    private List<Schedule> schedules;


    @Transient
    private String careerName;

    public ClassMan(String identifier,
                    String remark,
                    boolean active,
                    SubjectCurriculum subjectCurriculum,
                    Itinerary itinerary,
                    AcademicPeriod academicPeriod,
                    String careerName,
                    List<ScheduleItinerary> scheduleItineraries) {
        this.identifier = identifier;
        this.remark = remark;
        this.active = active;
        this.subjectCurriculum = subjectCurriculum;
        this.itinerary = itinerary;
        this.academicPeriod = academicPeriod;
        this.careerName = careerName;
        this.scheduleItineraries = scheduleItineraries;
    }
}
