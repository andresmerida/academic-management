package org.am.core.web.repository.jdbc.schedule.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.GroupDto;
import org.am.core.web.repository.jdbc.schedule.GroupJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupJdbcRepositoryImpl implements GroupJdbcRepository {

    private final JdbcClient jdbcClient;

        @Override
        public List<GroupDto> getGroupsByAreaIdAndItineraryId(Integer areaId, Integer itineraryId) {
            return jdbcClient.sql("""
                SELECT g.id,
                       g.identifier,
                       g.remark,
                       g.curriculum_id as curriculumId,
                       i.id as itineraryId,
                       i.name as itineraryName,
                       c.id as careerId,
                       c.name as careerName,
                       c.initials as careerInitials
                FROM group_table g
                         INNER JOIN itinerary i ON g.itinerary_id = i.id
                         INNER JOIN career c ON i.career_id = c.id
                WHERE c.area_id = ? AND i.id = ? AND i.active = true
                ORDER BY g.identifier
                """)
                    .params(areaId, itineraryId)
                    .query(GroupDto.class)
                    .list();
        }
    }
