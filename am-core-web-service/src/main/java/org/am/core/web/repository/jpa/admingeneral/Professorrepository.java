package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Professorrepository extends JpaRepository<Professor, Integer> {
}
