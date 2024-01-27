package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.SchedulePeriodId;
import org.am.core.web.dto.admingeneral.SchedulePeriodRequest;
import org.am.core.web.dto.admingeneral.SchedulePeriodDto;
import org.am.core.web.service.admingeneral.SchedulePeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/schedule-periods")
@RequiredArgsConstructor
public class SchedulePeriodController {

    private final SchedulePeriodService schedulePeriodService;

    @GetMapping("/{areaId}")
    public ResponseEntity<List<SchedulePeriodDto>> getSchedulePeriodsByAreaId(@PathVariable final Integer areaId) {
        List<SchedulePeriodDto> schedulePeriods = schedulePeriodService.getSchedulePeriodsByAreaId(areaId)
                .orElseThrow(IllegalArgumentException::new);

        return ResponseEntity.ok(schedulePeriods);
    }

    // ... otros métodos del controlador
}
