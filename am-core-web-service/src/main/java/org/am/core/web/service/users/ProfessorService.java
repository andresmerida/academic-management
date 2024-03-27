package org.am.core.web.service.users;

import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.dto.users.ProfessorRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AreaRepository;
import org.am.core.web.repository.jpa.users.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements CustomMap<ProfessorDto, Professor> {
    private final ProfessorRepository professorRepository;
    private final AreaRepository areaRepository;

    public ProfessorService(ProfessorRepository professorRepository, AreaRepository areaRepository) {
        this.professorRepository = professorRepository;
        this.areaRepository = areaRepository;
    }
    @Transactional(readOnly = true)
    public List<ProfessorDto> findAll(){
        return professorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Optional<ProfessorDto> findProfessorById(Integer professorId){
        return professorRepository.findById(professorId).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ProfessorDto> findProfessorByAreaId(Integer areaId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new IllegalArgumentException("Invalid area Id " + areaId));
        return area.getProfessors()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public ProfessorDto toDto(Professor professor) {
        return new ProfessorDto(
                professor.getId(),
                professor.getName(),
                professor.getLastName(),
                professor.getSecondLastName()
        );
    }
    public Professor toEntity(ProfessorRequest professorRequest){
        return new Professor(
                professorRequest.name(),
                professorRequest.lastName(),
                professorRequest.secondLastName()
        );
    }

    @Override
    public Professor toEntity(ProfessorDto professorDto) {
        Professor professor = new Professor();
        professor.setId(professorDto.id());
        professor.setName(professorDto.name());
        professor.setLastName(professorDto.lastName());
        professor.setSecondLastName(professorDto.secondLastName());
        return professor;
    }
}
