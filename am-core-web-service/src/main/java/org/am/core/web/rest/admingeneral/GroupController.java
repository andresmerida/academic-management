package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.GroupDto;
import org.am.core.web.dto.admingeneral.GroupRequest;
import org.am.core.web.service.admingeneral.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/admin/areas/{areaId}/itinerary/{itineraryId}/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @GetMapping
    public ResponseEntity<List<GroupDto>> listGroupByAreaId(@PathVariable final Integer areaId
            , @PathVariable final Integer itineraryId ){
        return ResponseEntity.ok().body(groupService.listGroupsByAreaAndItinerary(areaId,itineraryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getItinerayById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(groupService.getGroupById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody final GroupRequest groupRequest, @PathVariable final Integer areaId, @PathVariable final Integer itineraryId) throws URISyntaxException {
        GroupDto groupDB= groupService.save(groupRequest);

        return ResponseEntity.created(new URI("/admin/areas/"+areaId+"/itinerary/"+itineraryId+"/group"+ groupDB.id())).body(groupDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> edit(@PathVariable final Integer id, @RequestBody GroupRequest groupRequest){
        return ResponseEntity
                .ok()
                .body(groupService.edit(groupRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){

        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
