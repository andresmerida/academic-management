package org.am.core.web.rest.admingeneral;

import org.am.core.web.dto.admingeneral.ClassroomDto;
import org.am.core.web.dto.admingeneral.ClassroomRequest;
import org.am.core.web.service.admingeneral.ClassroomService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaId}/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDto>>listClassroomByArea(@PathVariable final Integer areaId){
        return ResponseEntity.ok().body(classroomService.getActiveClassroomByAreaId(areaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDto> getListClassroomByIdByArea(@PathVariable final Integer id){
        return ResponseEntity.ok(classroomService.getClassroomByIdByArea(id).orElseThrow(()->new IllegalArgumentException("Classroom with "+ id+"not exist")));
    }

    @PostMapping
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody final ClassroomRequest classroomRequest) throws URISyntaxException {
        ClassroomDto classroomDB = classroomService.save(classroomRequest);
        return ResponseEntity.created(new URI("/admin/classroom"+classroomDB.id())).body(classroomDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDto> editClassroom(@RequestBody final ClassroomDto dto, @PathVariable final Integer id){
        System.out.println(dto.id());
        if(dto.id()==null){
            throw new IllegalArgumentException("Invalid classroom ID");
        }
        if (!Objects.equals(id, dto.id())){
            throw new IllegalArgumentException("Invalid ID");
        }
        return ResponseEntity
                .ok()
                .body(classroomService.edit(dto));
    }

    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> delete(@PathVariable final Integer classroomId){
        try{
            classroomService.delete(classroomId);
            return ResponseEntity.noContent().build();
        }catch (DataIntegrityViolationException e){
            ClassroomDto classroom = classroomService.getClassroomById(classroomId).get();
            classroomService.editActive(classroom);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
