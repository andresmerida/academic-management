package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.admingeneral.Schedule;
import org.am.core.web.dto.admingeneral.ClassroomDto;
import org.am.core.web.dto.admingeneral.ClassroomRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.ClassroomRepository;
import org.am.core.web.repository.jpa.admingeneral.ScheduleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassroomService implements CustomMap<ClassroomDto, Classroom> {
    private final ClassroomRepository classroomRepository;
    private final ScheduleRepository scheduleRepository;

    public ClassroomService(ClassroomRepository classroomRepository, ScheduleRepository scheduleRepository) {
        this.classroomRepository = classroomRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<ClassroomDto> getActiveClassroomByAreaId(Integer areaId){
        List<Classroom> classrooms = classroomRepository.findAllByArea_IdOrderById(areaId);
        List<Classroom> activeClassrooms = classrooms.stream()
                .filter(c->c.getActive().equals(Boolean.TRUE))
                .collect(Collectors.toList());
        return activeClassrooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public ClassroomDto save(ClassroomRequest classroomRequest){
        return toDto(classroomRepository.save(toEntity(classroomRequest)));
    }

    public ClassroomDto edit(ClassroomRequest classroomDto, Integer classroomId){
        Classroom classroomFromDB = classroomRepository.findById(classroomId)
                .orElseThrow(()->new IllegalArgumentException("Invalid id"));
        classroomFromDB.setInitials(classroomDto.initials());
        classroomFromDB.setName(classroomDto.name());
        classroomFromDB.setType(classroomDto.type());
        classroomFromDB.setAddress(classroomDto.address());

        return toDto(classroomRepository.save(classroomFromDB));
    }

    public ClassroomDto editActive(ClassroomDto classroomDto){
        Classroom classroomFromDB = classroomRepository.findById(classroomDto.id())
                .orElseThrow(()-> new IllegalArgumentException("Invalid id"));
        classroomFromDB.setActive(false);
        return toDto(classroomRepository.save(classroomFromDB));
    }

    public void delete(Integer id){
        try {
            classroomRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            List<Schedule> schedules = scheduleRepository.findByClassroomId(id);
            scheduleRepository.deleteAll(schedules);
            Classroom classroomFromDB = classroomRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
            classroomFromDB.setActive(Boolean.FALSE);
            classroomRepository.save(classroomFromDB);
        }
    }

    public Optional<ClassroomDto> getClassroomById(Integer id){
        return classroomRepository.findById(id).map(this::toDto);
    }
    @Override
    public ClassroomDto toDto(Classroom classroom) {
        return new ClassroomDto(
                classroom.getId(),
                classroom.getInitials(),
                classroom.getName(),
                classroom.getType(),
                classroom.getAddress(),
                classroom.getArea().getId()
        );
    }

    @Override
    public Classroom toEntity(ClassroomDto classroomDto) {
        return null;
    }

    private Classroom toEntity(ClassroomRequest classroomRequest){
        Classroom classroom = new Classroom();
        classroom.setInitials(classroomRequest.initials());
        classroom.setName(classroomRequest.name());
        classroom.setType(classroomRequest.type());
        classroom.setAddress(classroomRequest.address());
        classroom.setActive(Boolean.TRUE);

        Area area= new Area();
        area.setId(classroomRequest.areaId());

        classroom.setArea(area);

        return classroom;
    }
}
