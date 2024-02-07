package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
