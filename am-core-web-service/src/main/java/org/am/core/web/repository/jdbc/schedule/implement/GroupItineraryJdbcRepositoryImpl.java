package org.am.core.web.repository.jdbc.schedule.implement;


import lombok.RequiredArgsConstructor;
import org.am.core.web.repository.jdbc.schedule.GroupItineraryJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupItineraryJdbcRepositoryImpl implements GroupItineraryJdbcRepository {

    private final JdbcClient jdbcClient;
}
