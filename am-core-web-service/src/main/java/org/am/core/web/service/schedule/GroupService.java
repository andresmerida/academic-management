package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService implements CustomMap <GroupDto, Group> {

    private final GroupRepository groupRepository;

    @Override
    public GroupDto toDto(Group group) {
        return null;
    }

    @Override
    public Group toEntity(GroupDto groupDto) {
        return null;
    }
}
