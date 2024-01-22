package org.am.core.web.rest.admingeneral;
import org.am.core.web.dto.admingeneral.AcademicPeriodDto;
import org.am.core.web.dto.admingeneral.AcademicPeriodRequest;
import org.am.core.web.service.admingeneral.AcademicPeriodService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaID}/academicPeriod")
public class AcademicPeriodController {
    private final AcademicPeriodService academicPeriodService;

    public AcademicPeriodController(AcademicPeriodService academicPeriodService) {
        this.academicPeriodService = academicPeriodService;
    }


    @GetMapping
    public ResponseEntity<List<AcademicPeriodDto>> listAcademicPeriodByArea(@PathVariable final Integer areaID) {
        return ResponseEntity.ok().body(academicPeriodService.getActiveAcademicPeriodByAreaId(areaID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicPeriodDto> getAcademicPeriodById(@PathVariable final Integer id) {
        return ResponseEntity.ok(academicPeriodService.getAcademicPeriodById(id).orElseThrow(() -> new IllegalArgumentException("AcademicPeriod with " + id + "not exist")));
    }

    @PostMapping
    public ResponseEntity<AcademicPeriodDto> createAcademicPeriod(@RequestBody final AcademicPeriodRequest academicPeriodRequest) throws URISyntaxException {

        AcademicPeriodDto academicPeriodDtoDB = academicPeriodService.save(academicPeriodRequest);

        return ResponseEntity.created(new URI("/admin/academicPeriod" + academicPeriodDtoDB.id())).body(academicPeriodDtoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicPeriodDto> editAcademicPeriod(@RequestBody final AcademicPeriodDto dto, @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid academicPeriod ID");
        }

        if (!Objects.equals(id, dto.id())) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return ResponseEntity
                .ok()
                .body(academicPeriodService.edit(dto));
    }


    @DeleteMapping("/{academic_period_id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer academic_period_id) {

        try {
            academicPeriodService.delete(academic_period_id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

