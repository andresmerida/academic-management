package org.am.core.web.repository.jpa.users;

import org.am.core.web.domain.entity.users.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {
    List<Professor> findProfessorsByActiveOrderByName(Boolean active);
}
