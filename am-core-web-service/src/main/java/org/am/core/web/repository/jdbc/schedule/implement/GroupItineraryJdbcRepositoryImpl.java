package org.am.core.web.repository.jdbc.schedule.implement;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupAuxDto;
import org.am.core.web.repository.jdbc.schedule.GroupItineraryJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupItineraryJdbcRepositoryImpl implements GroupItineraryJdbcRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<GroupAuxDto> getGroupItinerariesByCareer(Integer careerId, Integer itineraryId) {
        return jdbcClient.sql("""
                select g.id AS groupItineraryId,
                    sc.level,
                    s.name as subjectName,
                    s.initials as subjectInitials,
                    g.identifier as groupIdentifier,
                    si.weekday as dayOfWeek,
                    si.start_time as startTime,
                    si.end_time as endTime,
                    si.assistant,
                    c.name as classroomName,
                    c.initials as classroomInitials,
                    p.name as professorName,
                    p.last_name as professorLastName,
                    p.second_last_name as professorSecondLastName,
                    si.id as scheduleItineraryId
                from group_itinerary g
                join public.schedule_itinerary si on g.id = si.group_itinerary_id
                    left join public.classroom c on c.id = si.classroom_id
                    left join public.professor p on p.id = si.professor_id
                join public.subject_curriculum sc on sc.curriculum_id = g.curriculum_id and sc.subject_id = g.subject_id
                    inner join public.subject s on sc.subject_id = s.id
                    inner join public.curriculum cu on cu.id = g.curriculum_id
                where cu.career_id = ? and g.itinerary_id = ? and sc.active = true
                order by g.id, sc.level, s.name, g.identifier, si.weekday, si.start_time;
                """)
                .param(careerId)
                .param(itineraryId)
                .query(GroupAuxDto.class)
                .list();
    }
}
