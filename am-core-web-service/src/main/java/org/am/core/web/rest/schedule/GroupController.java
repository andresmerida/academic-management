package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.dto.schedule.BulkCreateGroupRequest;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.service.schedule.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/academic-periods/{academicPeriodId}/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> bulkCreate(@PathVariable final Integer careerId,
                                               @PathVariable final Integer itineraryId,
                                               @PathVariable final Integer academicPeriodId) throws URISyntaxException {
        groupService.generateGroupsFromItinerary(careerId, itineraryId, academicPeriodId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/{scheduleId}")
    public ResponseEntity <Group> EditGroup(@PathVariable("groupId") Integer groupId,
                                                      @PathVariable("scheduleId") Integer scheduleId) throws URISyntaxException {

        return ResponseEntity
                .ok()
                .body(groupService.deleteScheduleById(groupId, scheduleId));
    }
}
