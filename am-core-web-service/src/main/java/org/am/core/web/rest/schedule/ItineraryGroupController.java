package org.am.core.web.rest.schedule;

import org.am.core.web.dto.schedule.GroupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/groups")
public class ItineraryGroupController {
    @GetMapping
    public ResponseEntity<List<GroupDto>> listItineraryGroups(@PathVariable final Integer areaId,
                                                              @PathVariable final Integer careerId,
                                                              @PathVariable final Integer itineraryId){
        return ResponseEntity.ok().body(null);
    }
}
