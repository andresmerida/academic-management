package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "area_parameters")
@NoArgsConstructor
@Getter
@Setter
public class AreaParameters {

    @Id
    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "monday_schedule")
    private Boolean mondaySchedule;

    @Column(name = "tuesday_schedule")
    private Boolean tuesdaySchedule;

    @Column(name = "wednesday_schedule")
    private Boolean wednesdaySchedule;

    @Column(name = "thursday_schedule")
    private Boolean thursdaySchedule;

    @Column(name = "friday_schedule")
    private Boolean fridaySchedule;

    @Column(name = "saturday_schedule")
    private Boolean saturdaySchedule;

    @Column(name = "sunday_schedule")
    private Boolean sundaySchedule;

    @Column(name = "start_time_schedule", columnDefinition = "TIME")
    private LocalTime startTimeSchedule;

    @Column(name = "end_time_schedule", columnDefinition = "TIME")
    private LocalTime endTimeSchedule;

    @Column(name = "time_interval_schedule")
    private Integer timeIntervalSchedule;

    @Column(name = "lunch_time_schedule", nullable = false)
    private Boolean lunchTimeSchedule;

    @Column(name = "start_lunch_time_schedule")
    private LocalTime startLunchTimeSchedule;

    @Column(name = "end_lunch_time_schedule")
    private LocalTime endLunchTimeSchedule;

    @Column(name = "between_period", nullable = false)
    private Integer betweenPeriod;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Area area;

    public AreaParameters(Boolean mondaySchedule, Boolean tuesdaySchedule,
                          Boolean wednesdaySchedule, Boolean thursdaySchedule,
                          Boolean fridaySchedule, Boolean saturdaySchedule,
                          Boolean sundaySchedule, LocalTime startTimeSchedule,
                          LocalTime endTimeSchedule, Integer timeIntervalSchedule,
                          Boolean lunchTimeSchedule, LocalTime startLunchTimeSchedule,
                          LocalTime endLunchTimeSchedule, Integer betweenPeriod,
                          Area area) {
        this.mondaySchedule = mondaySchedule;
        this.tuesdaySchedule = tuesdaySchedule;
        this.wednesdaySchedule = wednesdaySchedule;
        this.thursdaySchedule = thursdaySchedule;
        this.fridaySchedule = fridaySchedule;
        this.saturdaySchedule = saturdaySchedule;
        this.sundaySchedule = sundaySchedule;
        this.startTimeSchedule = startTimeSchedule;
        this.endTimeSchedule = endTimeSchedule;
        this.timeIntervalSchedule = timeIntervalSchedule;
        this.lunchTimeSchedule = lunchTimeSchedule;
        this.startLunchTimeSchedule = startLunchTimeSchedule;
        this.endLunchTimeSchedule = endLunchTimeSchedule;
        this.betweenPeriod = betweenPeriod;
        this.area = area;
    }
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Boolean getMondaySchedule() {
        return mondaySchedule;
    }

    public void setMondaySchedule(Boolean mondaySchedule) {
        this.mondaySchedule = mondaySchedule;
    }

    public Boolean getTuesdaySchedule() {
        return tuesdaySchedule;
    }

    public void setTuesdaySchedule(Boolean tuesdaySchedule) {
        this.tuesdaySchedule = tuesdaySchedule;
    }

    public Boolean getWednesdaySchedule() {
        return wednesdaySchedule;
    }

    public void setWednesdaySchedule(Boolean wednesdaySchedule) {
        this.wednesdaySchedule = wednesdaySchedule;
    }

    public Boolean getThursdaySchedule() {
        return thursdaySchedule;
    }

    public void setThursdaySchedule(Boolean thursdaySchedule) {
        this.thursdaySchedule = thursdaySchedule;
    }

    public Boolean getFridaySchedule() {
        return fridaySchedule;
    }

    public void setFridaySchedule(Boolean fridaySchedule) {
        this.fridaySchedule = fridaySchedule;
    }

    public Boolean getSaturdaySchedule() {
        return saturdaySchedule;
    }

    public void setSaturdaySchedule(Boolean saturdaySchedule) {
        this.saturdaySchedule = saturdaySchedule;
    }

    public Boolean getSundaySchedule() {
        return sundaySchedule;
    }

    public void setSundaySchedule(Boolean sundaySchedule) {
        this.sundaySchedule = sundaySchedule;
    }

    public LocalTime getStartTimeSchedule() {
        return startTimeSchedule;
    }

    public void setStartTimeSchedule(LocalTime startTimeSchedule) {
        this.startTimeSchedule = startTimeSchedule;
    }

    public LocalTime getEndTimeSchedule() {
        return endTimeSchedule;
    }

    public void setEndTimeSchedule(LocalTime endTimeSchedule) {
        this.endTimeSchedule = endTimeSchedule;
    }

    public Integer getTimeIntervalSchedule() {
        return timeIntervalSchedule;
    }

    public void setTimeIntervalSchedule(Integer timeIntervalSchedule) {
        this.timeIntervalSchedule = timeIntervalSchedule;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }


    public Boolean getLunchTimeSchedule() {
        return lunchTimeSchedule;
    }

    public void setLunchTimeSchedule(Boolean lunchTimeSchedule) {
        this.lunchTimeSchedule = lunchTimeSchedule;
    }

    public LocalTime getStartLunchTimeSchedule() {
        return startLunchTimeSchedule;
    }

    public void setStartLunchTimeSchedule(LocalTime startLunchTimeSchedule) {
        this.startLunchTimeSchedule = startLunchTimeSchedule;
    }

    public LocalTime getEndLunchTimeSchedule() {
        return endLunchTimeSchedule;
    }

    public void setEndLunchTimeSchedule(LocalTime endLunchTimeSchedule) {
        this.endLunchTimeSchedule = endLunchTimeSchedule;
    }

    public Integer getBetweenPeriod() {
        return betweenPeriod;
    }

    public void setBetweenPeriod(Integer betweenPeriod) {
        this.betweenPeriod = betweenPeriod;
    }
}
