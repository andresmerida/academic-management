package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    List<Curriculum> findCurriculumByCareer_IdAndActiveOrderByName(Integer careerId, Boolean active);
}
