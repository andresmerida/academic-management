package org.am.core.web.rest.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.dto.admingeneral.CareerDto;
import org.am.core.web.dto.admingeneral.CareerRequest;
import org.am.core.web.service.admingeneral.CareerService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/careers")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<List<CareerDto>> listCareer() {
        return ResponseEntity.ok().body(careerService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDto> getCareerById(@PathVariable final Integer id) {
        return ResponseEntity.ok(careerService.getCareerById(id).orElseThrow(() -> new IllegalArgumentException("Career with " + id + "not exist")));
    }

    @PostMapping
    public ResponseEntity<CareerDto> createCareer(@RequestBody final CareerRequest careerRequest) throws URISyntaxException {

        CareerDto careerDB = careerService.save(careerRequest);

        return ResponseEntity.created(new URI("/admin/career" + careerDB.id())).body(careerDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerDto> editCareer(@RequestBody final CareerDto dto, @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid career ID");
        }

        if (!Objects.equals(id, dto.id())) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return ResponseEntity
                .ok()
                .body(careerService.edit(dto));
    }


    @DeleteMapping("/{careerid}")
    public ResponseEntity<Void> delete(@PathVariable final Integer careerid) {
        try {
            careerService.delete(careerid);
            return ResponseEntity.noContent().build();

        } catch (DataIntegrityViolationException e) {
            CareerDto career = careerService.getCareerById(careerid).get();
            careerService.editActive(career);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}


