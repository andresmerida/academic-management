package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.Schedule;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService implements CustomMap<ScheduleDto, Schedule> {

    @Override
    public ScheduleDto toDto(Schedule schedule) {
        return null;
    }

    @Override
    public Schedule toEntity(ScheduleDto scheduleDto) {
        return null;
    }
}
