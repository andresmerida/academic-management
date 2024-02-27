package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.service.admingeneral.CurriculumService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/curriculums")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;
    @GetMapping
    public ResponseEntity<List<CurriculumDto>> listByCareerId(@PathVariable final Integer careerId) {
        return ResponseEntity.ok().body(curriculumService.getCurriculumsByCareerId(careerId));
    }

    @PostMapping
    public ResponseEntity<CurriculumDto> create(@RequestBody final CurriculumRequest curriculumRequest,
                                            @PathVariable final Integer areaId) throws URISyntaxException {
        CurriculumDto curriculumDto = curriculumService.save(curriculumRequest);
        return ResponseEntity.created(new URI("/admin/areas/"+areaId+"/careers" + curriculumRequest.careerId() + "/curriculums/" + areaId)).body(curriculumDto);
    }

    @DeleteMapping("/{curriculumid}")
    public ResponseEntity<Void> delete(@PathVariable final Integer curriculumid) {

        try {
            curriculumService.delete(curriculumid);
        } catch (DataIntegrityViolationException e) {
            curriculumService.logicalDelete(curriculumid);
        }

        return ResponseEntity.noContent().build();
    }

}
