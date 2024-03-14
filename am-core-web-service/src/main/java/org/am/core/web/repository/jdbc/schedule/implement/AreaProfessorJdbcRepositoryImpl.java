package org.am.core.web.repository.jdbc.schedule.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.repository.jdbc.schedule.AreaProfessorJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AreaProfessorJdbcRepositoryImpl implements AreaProfessorJdbcRepository {
    private final JdbcClient jdbcClient;
    @Override
    public List<ProfessorDto> getProfessorsByAreaIdAndActive(Integer areaId) {
        return jdbcClient.sql("""
                SELECT p.id, p.name, p.last_name, p.second_last_name
                FROM professor p
                INNER JOIN area_professor ap on p.id = ap.professor_id
                WHERE ap.area_id=?
                AND ap.active = true
                """)
                .param(areaId)
                .query(ProfessorDto.class)
                .list();
    }
    @Override
    public ProfessorDto findProfessorByAreaIdAndActive(Integer professorId, Integer areaId) {
        return jdbcClient.sql("""
                SELECT p.id, p.name, p.last_name, p.second_last_name
                FROM professor p
                INNER JOIN area_professor ap on p.id = ap.professor_id
                WHERE ap.area_id=?
                AND ap.professor_id=?
                AND ap.active = true
                """)
                .param(areaId)
                .param(professorId)
                .query(ProfessorDto.class)
                .single();
    }

    @Override
    public Long getAreaCountByProfessorId(Integer id) {
        Long areaCount = jdbcClient.sql("""
            SELECT COUNT(*) FROM area_professor
            WHERE professor_id = ?
            """)
                .param(id)
                .query(Long.class)
                .single();

        if (areaCount > 1) {
            throw new IllegalArgumentException("Professor is registered in more than one area. Deletion not allowed.");
        }
        return areaCount;
    }

    @Override
    public void addProfessorToArea(Integer professorId, Integer areaId) {
        jdbcClient.sql("""
            INSERT INTO area_professor (professor_id, area_id, active)
            VALUES (?, ?, true)
            """)
                .params(professorId, areaId)
                .update();
    }

    @Override
    public void desactiveAreaProfessor(Integer professorId, Integer areaId) {
        jdbcClient.sql("""
                UPDATE area_professor
                SET active = false
                WHERE professor_id = ?
                  AND area_id = ?
                """)
                .params(professorId, areaId)
                .update();
    }
}
