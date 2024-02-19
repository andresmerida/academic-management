package org.am.core.web.service.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class GroupItineraryService implements CustomMap<GroupDto, GroupItinerary> {

    private final GroupItineraryRepository groupItineraryRepository;
    private final ScheduleItineraryService scheduleItineraryService;


    public Optional<GroupDto> getItineraryById(Integer id){
        return groupItineraryRepository.findById(id).map(this::toDto);
    }


    public GroupDto save(GroupRequest groupRequest) {
        GroupItinerary savedGroup = groupItineraryRepository.save(toEntity(groupRequest));
        scheduleItineraryService.saveAll(groupRequest.listSchedule(), savedGroup);
        return toDto(savedGroup);

    }

    public GroupDto edit(GroupRequest groupDto, Integer groupItineraryId){
        GroupItinerary groupItineraryFromDB = groupItineraryRepository.findById(groupItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupDto.itineraryId());
        groupItineraryFromDB.setItinerary(itinerary);

        groupItineraryFromDB.setIdentifier(groupDto.identifier());
        groupItineraryFromDB.setRemark(groupDto.remark());

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId();
        subjectCurriculumId.setCurriculumId(groupDto.curriculumId());
        subjectCurriculumId.setSubjectId(groupDto.subjectId());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);

        groupItineraryFromDB.setSubjectCurriculum(subjectCurriculum);


        return toDto(groupItineraryRepository.save(groupItineraryFromDB));
    }

    @Override
    public GroupDto toDto(GroupItinerary groupItinerary) {
        Subject subject = new Subject();
        subject.setId(groupItinerary.getSubjectCurriculum().getSubjectCurriculumId().getSubjectId());

        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (groupItinerary.getScheduleItineraries() != null) {
            scheduleDtos = groupItinerary.getScheduleItineraries()
                    .stream()
                    .map(scheduleItineraryService::toDto)
                    .collect(Collectors.toList());
        }

        return new GroupDto(
                groupItinerary.getSubjectCurriculum().getLevel(),
                subject.getName(),
                subject.getInitials(),
                groupItinerary.getIdentifier(),
                scheduleDtos
        );
    }

    @Override
    public GroupItinerary toEntity(GroupDto groupDto) {
        return null;
    }

    public GroupItinerary toEntity(GroupRequest groupRequest) {
        GroupItinerary groupItinerary = new GroupItinerary();

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupRequest.itineraryId());
        groupItinerary.setItinerary(itinerary);

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId();
        subjectCurriculumId.setCurriculumId(groupRequest.curriculumId());
        subjectCurriculumId.setSubjectId(groupRequest.subjectId());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);

        groupItinerary.setSubjectCurriculum(subjectCurriculum);

        groupItinerary.setIdentifier(groupRequest.identifier());
        groupItinerary.setRemark(groupRequest.remark());

        return groupItinerary;
    }


}
