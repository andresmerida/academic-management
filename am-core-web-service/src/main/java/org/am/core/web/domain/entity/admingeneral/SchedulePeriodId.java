package org.am.core.web.domain.entity.admingeneral;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class SchedulePeriodId implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;
    private String weekday;
    private Integer areaId;

    // Constructores, getters, setters y métodos equals y hashCode aquí...

    // Asegúrate de implementar correctamente equals y hashCode para la comparación
    // y uso de objetos SchedulePeriodId en conjunto con la clase SchedulePeriod.


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
