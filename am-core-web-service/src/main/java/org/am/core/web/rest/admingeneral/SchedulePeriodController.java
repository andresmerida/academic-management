package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriodId;
import org.am.core.web.dto.admingeneral.SchedulePeriodRequest;
import org.am.core.web.dto.admingeneral.SchedulePeriodDto;
import org.am.core.web.service.admingeneral.SchedulePeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/schedule-periods")
@RequiredArgsConstructor
public class SchedulePeriodController {

    private final SchedulePeriodService schedulePeriodService;

    @GetMapping("/{areaId}")
    public ResponseEntity<SchedulePeriodDto> getSchedulePeriodById(@PathVariable final SchedulePeriodId areaId) {
        return ResponseEntity
                .ok()
                .body(
                        schedulePeriodService.getSchedulePeriodById(areaId)
                                .orElseThrow(IllegalArgumentException::new)
                );
    }

    @PutMapping("/{areaId}")
    public ResponseEntity<SchedulePeriodDto> editSchedulePeriod(@PathVariable final SchedulePeriodId areaId,
                                                                @RequestBody final SchedulePeriodRequest request) {
        return ResponseEntity.ok().body(schedulePeriodService.edit(areaId, request));
    }
}
