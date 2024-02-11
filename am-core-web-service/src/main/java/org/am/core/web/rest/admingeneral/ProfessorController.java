package org.am.core.web.rest.admingeneral;
import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.ProfessorDto;
import org.am.core.web.dto.admingeneral.ProfessorRequest;
import org.am.core.web.service.admingeneral.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> listProfessorByArea() {
        return ResponseEntity.ok().body(professorService.getProfessorByAreaId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable final Integer id) {
        return ResponseEntity
                .ok(professorService.getProfessorById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Professor with " + id + "not exist")));

    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody final ProfessorRequest professorRequest) throws URISyntaxException {

        ProfessorDto professorDB = professorService.save(professorRequest);
        return ResponseEntity.created(new URI("/admin/professor" + professorDB.id())).body(professorDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> editProfessor(@RequestBody final ProfessorDto dto, @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid professor ID");
        }
        if (!Objects.equals(id, dto.id())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .ok()
                .body(professorService.edit(dto));
    }


    @DeleteMapping("/{professorId}")
    public ResponseEntity<Void> delete(@PathVariable final Integer professorId) {
        professorService.delete(professorId);
        return ResponseEntity.noContent().build();
    }
}
