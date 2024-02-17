package org.am.core.web.repository.jdbc.schedule.implement;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.repository.jdbc.schedule.ItineraryJdbcRepository;
import org.am.core.web.repository.jdbc.schedule.ScheduleItineraryJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleItineraryJdbcRepositoryImpl implements ScheduleItineraryJdbcRepository {

    private final JdbcClient jdbcClient;
    @Override
    public List<ScheduleDto> findAllByGroupItineraryId(Integer groupId) {
        return jdbcClient.sql("""
                SELECT 
                    si.id, 
                    si.weekday as dayOfWeek, 
                    si.start_time, 
                    si.end_time, 
                    c.name AS classroomName, 
                    c.initials AS classroomInitials, 
                    p.name || ' ' || p.last_name || ' ' || p.second_last_name AS professorFullName 
                FROM 
                    schedule_itinerary si 
                    LEFT JOIN classroom c ON si.classroom_id = c.id 
                    LEFT JOIN professor p ON si.professor_id = p.id 
                WHERE 
                    si.group_itinerary_id = :groupId
                """)
                .param("groupId", groupId)
                .query(ScheduleDto.class)
                .list();
    }
}
