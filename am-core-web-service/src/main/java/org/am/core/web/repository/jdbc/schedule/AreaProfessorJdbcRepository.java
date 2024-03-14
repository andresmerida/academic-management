package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.users.ProfessorDto;

import java.util.List;

public interface AreaProfessorJdbcRepository {
    List<ProfessorDto> getProfessorsByAreaIdAndActive(Integer areaId);
    ProfessorDto findProfessorByAreaIdAndActive(Integer professorId, Integer areaId);
    Long getAreaCountByProfessorId(Integer id);
    void addProfessorToArea(Integer professorId, Integer areaId);
    void desactiveAreaProfessor(Integer professorId, Integer areaId);
}
