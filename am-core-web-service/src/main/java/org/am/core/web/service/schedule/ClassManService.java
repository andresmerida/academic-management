package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jdbc.schedule.GroupItineraryClassJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupItineraryClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassManService implements CustomMap<ClassManDto, ClassMan> {

    private final GroupItineraryClassRepository groupItineraryClassRepository;
    private final ScheduleItineraryService scheduleItineraryService;
    private final GroupItineraryClassJdbcRepository groupItineraryClassJdbcRepository;

    public List<ClassManDto> getClassManByCareerAndItinerary(Integer careerId, Integer itineraryId) {
        List<ClassManDto> classManDtoList = new ArrayList<>();
        ClassManAuxDto previousClassManAuxDto = null;
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (ClassManAuxDto auxDto: groupItineraryClassJdbcRepository.getClassManByCareer(careerId, itineraryId)) {
            if (previousClassManAuxDto != null && previousClassManAuxDto.groupItineraryId().intValue() != auxDto.groupItineraryId().intValue()) {
                classManDtoList.add(new ClassManDto(
                        previousClassManAuxDto.groupItineraryId(),
                        previousClassManAuxDto.level(),
                        previousClassManAuxDto.careerName(),
                        previousClassManAuxDto.subjectName(),
                        previousClassManAuxDto.subjectInitials(),
                        new ArrayList<>(scheduleDtoList)));
                scheduleDtoList.clear();
            }

            String fullName = NOT_ASSIGNED_YET;
            if (auxDto.professorName() != null) {
                fullName = getFullName(auxDto.professorName(), auxDto.professorLastName(), auxDto.professorSecondLastName());
            } else if (auxDto.assistant() != null && !auxDto.assistant().isEmpty()) {
                fullName = auxDto.assistant();
            }

            scheduleDtoList.add(new ScheduleDto(
                    auxDto.scheduleItineraryId(),
                    DayOfWeek.of(auxDto.dayOfWeek()).toString(),
                    auxDto.startTime(),
                    auxDto.endTime(),
                    auxDto.classroomName(),
                    auxDto.classroomInitials(),
                    fullName
            ));
            previousClassManAuxDto = auxDto;
        }

        if (previousClassManAuxDto != null) {
            classManDtoList.add(new ClassManDto(
                    previousClassManAuxDto.groupItineraryId(),
                    previousClassManAuxDto.level(),
                    previousClassManAuxDto.subjectName(),
                    previousClassManAuxDto.subjectInitials(),
                    previousClassManAuxDto.groupIdentifier(),
                    new ArrayList<>(scheduleDtoList)));
            scheduleDtoList = null;
        }

        return classManDtoList;
    }

    @Transactional(readOnly = true)
    public Optional<ClassManDto> getItineraryById(Integer id){
        return groupItineraryClassRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ClassManDto> getClassManById(Integer id){
        return groupItineraryClassRepository.findById(id).map(this::toDto);
    }

    public ClassManDto save(GroupRequest groupRequest) {
        ClassMan savedGroup = groupItineraryClassRepository.save(toEntity(groupRequest));
        scheduleItineraryService.saveAll(groupRequest.listSchedule(), savedGroup);

        return toDto(savedGroup);
    }



    @Override
    public ClassManDto toDto(ClassMan classMan) {
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (classMan.getScheduleItineraries() != null) {
            scheduleDtos = classMan.getScheduleItineraries()
                    .stream()
                    .map(scheduleItineraryService::toDto)
                    .collect(Collectors.toList());
        }
        return new ClassManDto(
                classMan.getId(),
                classMan.getSubjectCurriculum().getLevel(),
                classMan.getSubjectCurriculum().getSubject().getName(),
                classMan.getSubjectCurriculum().getSubject().getInitials(),
                classMan.getIdentifier(),
                scheduleDtos
        );
    }


    @Override
    public ClassMan toEntity(ClassManDto classManDto) {
        return null;
    }
}