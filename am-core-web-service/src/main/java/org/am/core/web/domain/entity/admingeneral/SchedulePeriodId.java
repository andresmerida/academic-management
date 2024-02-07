package org.am.core.web.domain.entity.admingeneral;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class SchedulePeriodId implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;
    private String weekday;
    private Integer areaId;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
