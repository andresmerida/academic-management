package org.am.core.web.repository.jdbc.schedule.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.ScheduleDto;
import org.am.core.web.repository.jdbc.schedule.ScheduleJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleJdbcRepositoryImpl implements ScheduleJdbcRepository {

    private final JdbcClient jdbcClient;


    @Override
    public List<ScheduleDto> getScheduleByAreaIdAndGroupIdAndItineraryId(Integer areaId, Integer itineraryId, Integer groupId) {
        return jdbcClient.sql("""
                SELECT s.id,
                       s.start_time as startTime,
                       s.end_time as endTime,
                       s.weekday,
                       s.assistant
                FROM schedule s
                         INNER JOIN group_table g ON s.group_id = g.id
                         INNER JOIN itinerary i ON g.itinerary_id = i.id
                         INNER JOIN career c ON i.career_id = c.id
                WHERE c.area_id = ? AND g.id = ? AND i.id = ? AND i.active = true
                ORDER BY s.weekday, s.start_time
                """)
                .params(areaId, groupId, itineraryId)
                .query(ScheduleDto.class)
                .list();
    }
}
