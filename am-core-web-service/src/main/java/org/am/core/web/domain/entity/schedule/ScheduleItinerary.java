package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.users.Professor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_itinerary_id")
    private GroupItinerary groupItinerary;

    public ScheduleItinerary(LocalTime startTime, LocalTime endTime,
                             DayOfWeek dayOfWeek, String assistant,
                             Classroom classroom, Professor professor,
                             GroupItinerary groupItinerary) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.assistant = assistant;
        this.classroom = classroom;
        this.professor = professor;
        this.groupItinerary = groupItinerary;
    }
}
