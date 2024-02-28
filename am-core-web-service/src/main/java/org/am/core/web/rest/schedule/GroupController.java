package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.service.schedule.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/academic-periods/{academicPeriodId}/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> bulkCreate(@PathVariable final Integer careerId,
                                               @PathVariable final Integer academicPeriodId,
                                               @PathVariable final Integer itineraryId) throws URISyntaxException {
        groupService.generateGroupsFromItinerary(careerId, itineraryId, academicPeriodId);
        return ResponseEntity.noContent().build();
    }

}
