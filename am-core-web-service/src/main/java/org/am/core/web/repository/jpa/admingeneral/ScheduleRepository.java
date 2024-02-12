package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByGroupId(Integer groupId);
    List<Schedule> findByProfessorId(Integer professorId);

    List<Schedule> findByClassroomId(Integer professorId);

}
