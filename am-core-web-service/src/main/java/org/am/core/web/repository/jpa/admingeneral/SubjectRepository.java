package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
