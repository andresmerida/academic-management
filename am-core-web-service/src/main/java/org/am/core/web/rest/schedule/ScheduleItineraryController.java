package org.am.core.web.rest.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.dto.schedule.ScheduleRequest;
import org.am.core.web.service.schedule.ScheduleItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/group/{groupId}/scheduleItinerary")
@RequiredArgsConstructor
public class ScheduleItineraryController {

    private final ScheduleItineraryService scheduleItineraryService;



    @GetMapping
    public ResponseEntity<List<ScheduleDto>> listByGroupId(@PathVariable final Integer groupId){
        return ResponseEntity.ok().body(scheduleItineraryService.listSchedulesItineraryByGroupId(groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(scheduleItineraryService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(@RequestBody final ScheduleRequest scheduleRequest, @PathVariable final Integer groupId) throws URISyntaxException {
        ScheduleDto scheduleDto = scheduleItineraryService.save(scheduleRequest);

        return ResponseEntity.created(new URI("/admin/group/" + groupId + "/scheduleItinerary"+ scheduleDto.id())).body(scheduleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> edit(@PathVariable final Integer id, @RequestBody ScheduleRequest scheduleRequest){
        return ResponseEntity
                .ok()
                .body(scheduleItineraryService.edit(scheduleRequest, id));
    }


}
