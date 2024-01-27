package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriod;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriodId;
import org.am.core.web.dto.admingeneral.SchedulePeriodDto;
import org.am.core.web.dto.admingeneral.SchedulePeriodRequest;
import org.am.core.web.repository.jpa.admingeneral.SchedulePeriodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SchedulePeriodService {

    private final SchedulePeriodRepository schedulePeriodRepository;

    // ... otros métodos del servicio

    public Optional<List<SchedulePeriodDto>> getSchedulePeriodsByAreaId(Integer areaId) {
        List<SchedulePeriod> schedulePeriods = schedulePeriodRepository.findByAreaId(areaId);
        return Optional.of(schedulePeriods.stream().map(this::toDto).collect(Collectors.toList()));
    }


    public SchedulePeriodDto edit(SchedulePeriodId id, SchedulePeriodRequest periodRequest) {
        SchedulePeriod schedulePeriodFromDB = schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        schedulePeriodFromDB.setStartTime(periodRequest.startTime());
        schedulePeriodFromDB.setEndTime(periodRequest.endTime());
        schedulePeriodFromDB.setWeekday(periodRequest.weekday());
        schedulePeriodFromDB.setActive(periodRequest.active());
        // Setea la relación con AreaParameters si es necesario

        return toDto(schedulePeriodRepository.save(schedulePeriodFromDB));
    }

    private SchedulePeriodDto toDto(SchedulePeriod schedulePeriod) {
        // Implementa la conversión a DTO según tu lógica
        // Puedes basarte en la implementación de toDto en ScheduleParametersService
        return new SchedulePeriodDto(
                schedulePeriod.getStartTime(),
                schedulePeriod.getEndTime(),
                schedulePeriod.getWeekday(),
                schedulePeriod.getActive(),
                schedulePeriod.getAreaId()
        );
    }
}
