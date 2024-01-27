package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.ItineraryDto;
import org.am.core.web.dto.admingeneral.ItineraryRequest;
import org.am.core.web.service.admingeneral.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaId}/itineraries")
@RequiredArgsConstructor
public class ItineraryController {
    private final ItineraryService itineraryService;

    @GetMapping
    public ResponseEntity<List<ItineraryDto>> findAllActive(){
        return ResponseEntity.ok().body(itineraryService.getActiveIntineraries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryDto> getItinerayById(@PathVariable final Integer id){
        return ResponseEntity.ok().body(itineraryService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping
    public ResponseEntity<ItineraryDto> create(@RequestBody final ItineraryRequest itineraryRequest) throws URISyntaxException {
        ItineraryDto itineraryDto=itineraryService.save(itineraryRequest);

        return ResponseEntity.created(new URI("/admin/itineraries/" + itineraryDto.id())).body(itineraryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryDto> edit(@PathVariable final Integer id,@RequestBody ItineraryDto itineraryDto){
        if(itineraryDto.id()==null){
            throw new IllegalArgumentException("Invalid itinerary id, null value");
        }

        if(!Objects.equals(itineraryDto.id(),id)){
            throw new IllegalArgumentException("Invalid id");
        }

        return ResponseEntity
                .ok()
                .body(itineraryService.edit(itineraryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){

            itineraryService.editActive(id);
            return ResponseEntity.noContent().build();

    }


}
