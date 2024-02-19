package org.am.core.web.domain.entity.schedule;

import ch.qos.logback.core.spi.AbstractComponentTracker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.repository.jpa.admingeneral.SubjectRepository;

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
