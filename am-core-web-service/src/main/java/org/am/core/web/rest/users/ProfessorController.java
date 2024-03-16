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
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> listProfessorsActive(){
        return ResponseEntity.ok().body(professorService.listProfessorsActive());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getProfessorId(@PathVariable final Integer id){
        return ResponseEntity.ok().body(professorService.getProfessorById(id)
                .orElseThrow(IllegalArgumentException::new));
    }
    @PostMapping
    public ResponseEntity<ProfessorDto> create(@RequestBody final ProfessorRequest professorRequest) throws URISyntaxException {
        ProfessorDto professorDto = professorService.save(professorRequest);
        return ResponseEntity
                .created(new URI("/professor/"+professorDto.id()))
                .body(professorDto);
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
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
