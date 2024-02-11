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
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
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



    public Schedule(LocalTime startTime, LocalTime endTime, String weekday, String assistant,
                    Classroom classroom, Group group, Professor professor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.assistant = assistant;
        this.classroom = classroom;
        this.group = group;
        this.professor = professor;

    }
}
