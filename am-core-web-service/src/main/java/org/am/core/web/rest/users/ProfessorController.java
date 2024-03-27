package org.am.core.web.rest.users;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.service.users.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping("/all")
    public ResponseEntity<List<ProfessorDto>> findAll(){
        return ResponseEntity.ok().body(professorService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> findProfessorById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(professorService.findProfessorById(id).orElseThrow(IllegalArgumentException::new));
    }
    @GetMapping
    public ResponseEntity<List<ProfessorDto>> findProfessorByAreaId(@RequestParam("areaId") Integer areaId){
        return ResponseEntity.ok()
                .body(professorService.findProfessorByAreaId(areaId));
    }
}
