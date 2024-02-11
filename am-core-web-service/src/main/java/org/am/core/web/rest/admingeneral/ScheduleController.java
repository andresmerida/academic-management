package org.am.core.web.rest.admingeneral;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.GroupDto;
import org.am.core.web.dto.admingeneral.GroupRequest;
import org.am.core.web.dto.admingeneral.ScheduleDto;
import org.am.core.web.dto.admingeneral.ScheduleRequest;
import org.am.core.web.service.admingeneral.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/itinerary/{itineraryId}/group/{groupId}/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> listScheduleByAreaId(@PathVariable final Integer areaId
            , @PathVariable final Integer itineraryId, @PathVariable final Integer groupId ){
        return ResponseEntity.ok().body(scheduleService.listScheduleByAreaAndItineraryAndGroup(areaId,itineraryId, groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getItinerayById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(scheduleService.getScheduleById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(@RequestBody final ScheduleRequest scheduleRequest
            , @PathVariable final Integer areaId, @PathVariable final Integer itineraryId,
                                              @PathVariable final Integer groupId) throws URISyntaxException {
        ScheduleDto scheduleDB= scheduleService.save(scheduleRequest);

        return ResponseEntity.created(new URI("/admin/areas/"+areaId+"/itinerary/"
                +itineraryId+"/group/"+ groupId+"/schedule"+scheduleDB.id())).body(scheduleDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> edit(@PathVariable final Integer id, @RequestBody ScheduleRequest scheduleRequest){
        return ResponseEntity
                .ok()
                .body(scheduleService.edit(scheduleRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){

        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
