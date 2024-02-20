package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.dto.schedule.BulkCreateGroupRequest;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService implements CustomMap <GroupDto, Group> {

    private final GroupRepository groupRepository;
    private GroupItineraryService groupItineraryService;

    public void generateGroupsFromItinerary(BulkCreateGroupRequest dto) {
        int count = 0;
        /*
        List<Group> groupList = new ArrayList<>();
        for (GroupDto groupDto : groupItineraryService.(dto.careerId(), dto.itineraryId())) {
            groupList.add(toEntity(groupDto, dto));
        }
         */
    }

    @Override
    public GroupDto toDto(Group group) {
        return null;
    }

    @Override
    public Group toEntity(GroupDto groupDto) {
        return null;
    }

    public Group toEntity(GroupDto groupDto, BulkCreateGroupRequest dto) {
        /*
        return new Group(
                groupDto.groupIdentifier(),
                groupDto.remark(),
                Boolean.TRUE

        );
         */
        return null;
    }

    @Autowired
    public void setGroupItineraryService(GroupItineraryService groupItineraryService) {
        this.groupItineraryService = groupItineraryService;
    }
}
