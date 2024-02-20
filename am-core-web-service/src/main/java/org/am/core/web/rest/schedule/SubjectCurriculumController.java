package org.am.core.web.rest.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.SubjectCurriculumId;
import org.am.core.web.dto.schedule.SubjectCurriculumDto;
import org.am.core.web.dto.schedule.SubjectCurriculumRequest;
import org.am.core.web.service.schedule.SubjectCurriculumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/subjectCurriculum")
@RequiredArgsConstructor
public class SubjectCurriculumController {

    private final SubjectCurriculumService subjectCurriculumService;

    @GetMapping
    public ResponseEntity<List<SubjectCurriculumDto>> listByAreaId(){
        return ResponseEntity.ok().body(subjectCurriculumService.listSubjectCurriculum());
    }

    @GetMapping("/{curriculumId}/{subjectId}")
    public ResponseEntity<SubjectCurriculumDto> getSubjectCurriculumById(
            @PathVariable final Integer curriculumId,
            @PathVariable final Integer subjectId) {
        SubjectCurriculumId id = new SubjectCurriculumId(curriculumId, subjectId);
        return subjectCurriculumService.getSubjectCurriculumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubjectCurriculumDto> create(@RequestBody final SubjectCurriculumRequest subjectCurriculumRequest) throws URISyntaxException {
        SubjectCurriculumDto subjectCurriculumDto = subjectCurriculumService.save(subjectCurriculumRequest);

        return ResponseEntity.created(new URI("/admin/areas/subject-curriculums/" + subjectCurriculumDto.curriculumId() + "/" + subjectCurriculumDto.subjectId())).body(subjectCurriculumDto);
    }

    @PutMapping("/{curriculumId}/{subjectId}")
    public ResponseEntity<SubjectCurriculumDto> edit(@PathVariable final Integer curriculumId, @PathVariable final Integer subjectId, @RequestBody SubjectCurriculumRequest subjectCurriculumRequest){
        SubjectCurriculumId id = new SubjectCurriculumId(curriculumId, subjectId);
        return ResponseEntity.ok().body(subjectCurriculumService.edit(subjectCurriculumRequest, id));
    }


}
