package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.schedule.Itinerary;

import java.time.LocalTime;


@Entity
@Table(name = "schedule")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @SequenceGenerator(name = "schedule_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_sequence")
    private Integer id;
    @Column(name = "start_time")
    private LocalTime start_time;
    @Column(name = "end_time")
    private LocalTime end_time;
    private String weekday;
    private String assistant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    public Schedule(LocalTime start_time, LocalTime end_time, String weekday, String assistant,
                    Classroom classroom, Group group, Professor professor, Itinerary itinerary) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.weekday = weekday;
        this.assistant = assistant;
        this.classroom = classroom;
        this.group = group;
        this.professor = professor;
        this.itinerary = itinerary;
    }
}
