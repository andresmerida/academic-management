package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.BulkCreateGroupRequest;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.service.schedule.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/academic-periods/{academicPeriodId}/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/bulk-create")
    public ResponseEntity<GroupDto> bulkCreate(@PathVariable final Integer careerId,
                                               @PathVariable final Integer academicPeriodId,
                                               @RequestBody final BulkCreateGroupRequest bulkCreateGroupRequest) throws URISyntaxException {
        if (bulkCreateGroupRequest.careerId() == null) {
            throw new IllegalArgumentException("Invalid career id, null value");
        }
        if (!Objects.equals(bulkCreateGroupRequest.careerId(), careerId)) {
            throw new IllegalArgumentException("Invalid id");
        }

        if (bulkCreateGroupRequest.academicPeriodId() == null) {
            throw new IllegalArgumentException("Invalid academicPeriodId, null value");
        }
        if (!Objects.equals(bulkCreateGroupRequest.academicPeriodId(), academicPeriodId)) {
            throw new IllegalArgumentException("Invalid id");
        }

        return ResponseEntity.noContent().build();
    }
}
