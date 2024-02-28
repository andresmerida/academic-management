package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.ClassMan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupItineraryClassRepository extends JpaRepository<ClassMan, Integer> {
}
