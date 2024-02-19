package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Classroom;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="schedule_itinerary")
public class ScheduleItinerary {

    @Id
    @SequenceGenerator(name = "group_itinerary_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_itinerary_sequence")
    private Integer id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "weekday")
    private DayOfWeek dayOfWeek;

    private String assistant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_itinerary_id")
    private GroupItinerary groupItinerary;


}
