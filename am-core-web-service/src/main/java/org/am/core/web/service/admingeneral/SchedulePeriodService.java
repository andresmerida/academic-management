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

    public Optional<List<SchedulePeriodDto>> getSchedulePeriodsByAreaId(Integer areaId) {
        List<SchedulePeriod> schedulePeriods = schedulePeriodRepository.findByAreaId(areaId);
        return Optional.of(schedulePeriods.stream().map(this::toDto).collect(Collectors.toList()));
    }

    public void deleteByAreaId(Integer areaId) {
        schedulePeriodRepository.deleteByAreaId(areaId);
    }


    public SchedulePeriodDto createOrUpdate(SchedulePeriodRequest request) {
        SchedulePeriod schedulePeriod = new SchedulePeriod(request.startTime(), request.endTime(), request.weekday(), request.active(), request.areaId());
        SchedulePeriod savedSchedulePeriod = schedulePeriodRepository.save(schedulePeriod);

        return toDto(savedSchedulePeriod);
    }

    public SchedulePeriodDto edit(SchedulePeriodId id, SchedulePeriodRequest periodRequest) {
        SchedulePeriod schedulePeriodFromDB = schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        schedulePeriodFromDB.setStartTime(periodRequest.startTime());
        schedulePeriodFromDB.setEndTime(periodRequest.endTime());
        schedulePeriodFromDB.setWeekday(periodRequest.weekday());
        schedulePeriodFromDB.setActive(periodRequest.active());


        return toDto(schedulePeriodRepository.save(schedulePeriodFromDB));
    }

    private SchedulePeriodDto toDto(SchedulePeriod schedulePeriod) {

        return new SchedulePeriodDto(
                schedulePeriod.getStartTime(),
                schedulePeriod.getEndTime(),
                schedulePeriod.getWeekday(),
                schedulePeriod.getActive(),
                schedulePeriod.getAreaId()
        );
    }
}
