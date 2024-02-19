package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.service.schedule.GroupItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/itinerary-groups")
@RequiredArgsConstructor
public class GroupItineraryController {

    private final GroupItineraryService groupItineraryService;
    @GetMapping
    public ResponseEntity<List<GroupDto>> listItineraryGroups(@PathVariable final Integer areaId,
                                                              @PathVariable final Integer careerId,
                                                              @PathVariable final Integer itineraryId){
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getScheduleById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(groupItineraryService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody final GroupRequest groupRequest) throws URISyntaxException {
        GroupDto groupDto = groupItineraryService.save(groupRequest);

        return ResponseEntity.ok().body(groupDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> edit(@PathVariable final Integer id, @RequestBody GroupRequest groupRequest){
        return ResponseEntity
                .ok()
                .body(groupItineraryService.edit(groupRequest, id));
    }
}
