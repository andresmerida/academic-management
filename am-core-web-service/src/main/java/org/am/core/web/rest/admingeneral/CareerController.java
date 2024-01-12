package org.am.core.web.rest.admingeneral;


import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.dto.admingeneral.AreaDto;
import org.am.core.web.dto.admingeneral.AreaRequest;
import org.am.core.web.dto.admingeneral.CareerDto;
import org.am.core.web.dto.admingeneral.CareerRequest;
import org.am.core.web.service.admingeneral.CareerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/career")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<List<CareerDto>> listCareer(){
        return ResponseEntity.ok().body(careerService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDto> getCareerById(@PathVariable final Integer id){
        return ResponseEntity.ok(careerService.getCareerById(id).orElseThrow(()-> new IllegalArgumentException("Career with " + id + "not exist")));
    }
    @PostMapping
    public ResponseEntity<CareerDto> createCareer(@RequestBody final CareerRequest careerRequest) throws URISyntaxException {
        if(careerRequest.name() == null){
            throw new IllegalArgumentException("\n" + "You cannot have a new ID");
        }

        CareerDto careerDB = careerService.save(careerRequest);

        return ResponseEntity.created(new URI("/admin/career" + careerDB.id())).body(careerDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerDto> editCareer(@RequestBody final CareerDto dto, @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid career ID");
        }

        if(!Objects.equals(id, dto.id())){
            throw new IllegalArgumentException("Invalid ID");
        }
        return ResponseEntity
                .ok()
                .body(careerService.edit(dto));
    }


    @PatchMapping("/active/{careerId}")
    public ResponseEntity<CareerDto> editActiveCareer(@RequestBody final CareerDto dto,
                                                      @PathVariable final Integer careerId) throws URISyntaxException {
        Optional<CareerDto> existingCareer = careerService.getCareerById(careerId);

        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid career ID, the value is null");
        }
        if (!Objects.equals(dto.id(), careerId)) {
            throw new IllegalArgumentException("The career ID does not match the provided ID");
        }

        CareerDto existingCareerEntity = existingCareer.orElseThrow(() ->
                new IllegalArgumentException("Career with ID " + careerId + " does not exist"));

        return ResponseEntity
                .ok()
                .body(careerService.editActive(dto));
    }
    }

