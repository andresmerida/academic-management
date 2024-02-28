package org.am.core.web.rest.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ClassManDto;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.service.schedule.ClassManService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/class-management")
@RequiredArgsConstructor
public class ClassManController {

    private final ClassManService classManService;
    @GetMapping
    public ResponseEntity<List<ClassManDto>> listItineraryClassGroupsByCareerAndItinerary(@PathVariable final Integer careerId,
                                                                                          @PathVariable final Integer itineraryId){
        return ResponseEntity
                .ok()
                .body(classManService.getClassManByCareerAndItinerary(careerId, itineraryId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClassManDto> getGroupItineraryById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(classManService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@PathVariable final Integer areaId,
                                           @PathVariable final Integer careerId,
                                           @PathVariable final Integer itineraryId,
                                           @RequestBody final GroupRequest groupRequest) throws URISyntaxException {
        GroupDto groupDto = classManService.save(groupRequest);

        return ResponseEntity.created(
                        new URI("/admin/areas/" + areaId + "/careers/" + careerId + "/itineraries/" + itineraryId +
                                "/class-management/" + groupDto.id())
                )
                .body(groupDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> edit(@PathVariable final Integer id, @RequestBody GroupRequest groupRequest){
        return ResponseEntity
                .ok()
                .body(classManService.edit(groupRequest, id));
    }
}

