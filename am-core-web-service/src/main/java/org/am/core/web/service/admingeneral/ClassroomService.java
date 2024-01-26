package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.dto.admingeneral.ClassroomDto;
import org.am.core.web.dto.admingeneral.ClassroomRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.ClassroomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassroomService implements CustomMap<ClassroomDto, Classroom> {
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
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

    public Optional<ClassroomDto> getClassroomByIdByArea(Integer id){
        Classroom classroom = classroomRepository.findById(id).orElseThrow();
        Integer areaId = classroom.getArea().getId();

        List<ClassroomDto> classrooms = getActiveClassroomByAreaId(areaId);

        ClassroomDto foundClassroom = classrooms.stream()
                .filter(c-> c.id().equals(id))
                .findFirst()
                .orElseThrow();

        return Optional.of(foundClassroom);
    }

    public ClassroomDto save(ClassroomRequest classroomRequest){
        return toDto(classroomRepository.save(this.toEntity(classroomRequest)));
    }

    public ClassroomDto edit(ClassroomDto classroomDto){
        Classroom classroomFromDB = classroomRepository.findById(classroomDto.id())
                .orElseThrow(()-> new IllegalArgumentException("Invalid id"));
        classroomFromDB.setInitials(classroomDto.initials());
        classroomFromDB.setName(classroomDto.name());
        classroomFromDB.setType(classroomDto.type());
        classroomFromDB.setAddress(classroomDto.address());
        classroomFromDB.setArea(classroomDto.area());
        return toDto(classroomRepository.save(classroomFromDB));
    }

    public ClassroomDto editActive(ClassroomDto classroomDto){
        Classroom classroomFromDB = classroomRepository.findById(classroomDto.id())
                .orElseThrow(()-> new IllegalArgumentException("Invalid id"));
        classroomFromDB.setActive(false);
        return toDto(classroomRepository.save(classroomFromDB));
    }

    public void delete(Integer id){
        classroomRepository.deleteById(id);
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
                classroom.getActive(),
                classroom.getArea()
        );
    }

    @Override
    public Classroom toEntity(ClassroomDto classroomDto) {
        Classroom classroom= new Classroom();
        classroom.setId(classroomDto.id());
        classroom.setInitials(classroomDto.initials());
        classroom.setName(classroomDto.name());
        classroom.setType(classroomDto.type());
        classroom.setAddress(classroomDto.address());
        classroom.setActive(classroomDto.active());
        classroom.setArea(classroomDto.area());
        return classroom;
    }

    private Classroom toEntity(ClassroomRequest classroomRequest){
        return new Classroom(
                classroomRequest.initials(),
                classroomRequest.name(),
                classroomRequest.type(),
                Boolean.TRUE,
                classroomRequest.area(),
                classroomRequest.address()
        );
    }
}
