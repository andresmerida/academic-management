package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Group;
import org.am.core.web.domain.entity.admingeneral.Schedule;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.Itinerary;
import org.am.core.web.dto.admingeneral.GroupDto;
import org.am.core.web.dto.admingeneral.GroupRequest;
import org.am.core.web.repository.jdbc.schedule.GroupJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.GroupRepository;
import org.am.core.web.repository.jpa.admingeneral.ScheduleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class GroupService implements CustomMap<GroupDto, Group> {

    private final GroupRepository groupRepository;
    private final GroupJdbcRepository groupJdbcRepository;
    private final ScheduleRepository scheduleRepository;


    public List<GroupDto> listGroupsByAreaAndItinerary(Integer areaId, Integer itineraryId) {
        return groupJdbcRepository.getGroupsByAreaIdAndItineraryId(areaId, itineraryId);
    }

    public GroupDto save(GroupRequest groupRequest){
        return toDto(groupRepository.save(toEntity(groupRequest)));
    }


    public Optional<GroupDto> getGroupById(Integer id){
        return groupRepository.findById(id).map(this::toDto);
    }

    public GroupDto edit(GroupRequest groupRequest, Integer groupId){
        Group groupFromDB = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        groupFromDB.setIdentifier(groupRequest.identifier());
        groupFromDB.setRemark(groupRequest.remark());

        return toDto(groupRepository.save(groupFromDB));
    }
    public void delete(Integer id) {
        try {
            groupRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            List<Schedule> schedules = scheduleRepository.findByGroupId(id);
            scheduleRepository.deleteAll(schedules);
            groupRepository.deleteById(id);
        }

    }

    @Override
    public GroupDto toDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getIdentifier(),
                group.getRemark()
        );
    }

    @Override
    public Group toEntity(GroupDto groupDto) {
        return null;
    }

    private Group toEntity(GroupRequest groupRequest) {
        Group group = new Group();
        group.setIdentifier(groupRequest.identifier());
        group.setRemark(groupRequest.remark());
        group.setCurriculumId(groupRequest.curriculumId());

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupRequest.itineraryId());
        group.setItinerary(itinerary);

        Subject subject = new Subject();
        subject.setId(groupRequest.subjectId());
        group.setSubject(subject);
        return group;
    }
}
