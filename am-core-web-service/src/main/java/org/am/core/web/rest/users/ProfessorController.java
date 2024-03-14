package org.am.core.web.rest.users;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.dto.users.ProfessorRequest;
import org.am.core.web.service.users.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;
    @GetMapping
    public ResponseEntity<List<ProfessorDto>> listProfessorsByAreaIdAndActive(@RequestParam(name = "areaId") final Integer areaId){
        return ResponseEntity.ok().body(professorService.listProfessorsByAreaIdAndActive(areaId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable final Integer id){
        return ResponseEntity.ok().body(professorService.getProfessorById(id).orElseThrow(()->new IllegalArgumentException("Professor with id "+id+" not exist")));
    }
    @PostMapping
    public ResponseEntity<ProfessorDto> create(@RequestBody final ProfessorRequest professorRequest) throws URISyntaxException{
        ProfessorDto professorDto = professorService.save(professorRequest);

        return ResponseEntity
                .created(new URI("/professors/" + professorDto.id()))
                .body(professorDto);
    }
    @PostMapping("/{professor}/area/{area}")
    public ResponseEntity<Void> addProfessorToArea(@PathVariable final Integer professor, @PathVariable final Integer area){
        professorService.addProfessorToArea(professor, area);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> edit(@RequestBody final ProfessorDto professorDto,
                                             @PathVariable final Integer id){
        if(professorDto.id()==null){
            throw new IllegalArgumentException("Invalid professor id, null value");
        }
        if(!Objects.equals(professorDto.id(),id)){
            throw new IllegalArgumentException("Invalid id");
        }
        return ResponseEntity
                .ok()
                .body(professorService.edit(professorDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/area/{areaId}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id,@PathVariable final Integer areaId){
        professorService.deleteFromArea(id, areaId);
        return ResponseEntity.noContent().build();
    }

}
