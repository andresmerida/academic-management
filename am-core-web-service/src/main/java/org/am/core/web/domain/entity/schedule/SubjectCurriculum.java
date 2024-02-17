package org.am.core.web.domain.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="subject_curriculum")
public class SubjectCurriculum {

    @EmbeddedId
    private SubjectCurriculumId subjectCurriculumId;
    private Short level;
    private Boolean optional;
    private String path;
    private Short workload;
    private Boolean active;


    public SubjectCurriculum(SubjectCurriculumId subjectCurriculumId, Short level, Boolean optional,
                             String path, Short workload, Boolean active) {
        this.subjectCurriculumId = subjectCurriculumId;
        this.level = level;
        this.optional = optional;
        this.path = path;
        this.workload = workload;
        this.active = active;
    }
}
