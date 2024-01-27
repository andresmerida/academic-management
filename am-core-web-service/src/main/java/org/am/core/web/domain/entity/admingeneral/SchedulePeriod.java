package org.am.core.web.domain.entity.admingeneral;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "schedule_period")
@NoArgsConstructor
@Getter
@Setter
@IdClass(SchedulePeriodId.class)
public class SchedulePeriod{

    @Id
    @Column(name = "start_time")
    private LocalTime startTime;

    @Id
    @Column(name = "end_time")
    private LocalTime endTime;

    @Id
    @Column(name = "weekday")
    private String weekday;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "area_id")
    private Integer areaId;
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "area_id", nullable = false)
    //private AreaParameters areaParameters;

    // Constructores, getters y setters

    public SchedulePeriod(LocalTime startTime, LocalTime endTime, String weekday, Boolean active, Integer areaId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.active = active;
        this.areaId = areaId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
