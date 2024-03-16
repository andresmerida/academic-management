package org.am.core.web.service.users;

import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.dto.users.ProfessorRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.users.ProfessorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements CustomMap<ProfessorDto, Professor> {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    @Transactional(readOnly = true)
    public List<ProfessorDto> listProfessorsActive(){
        return professorRepository.findProfessorsByActiveOrderByName(Boolean.TRUE)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Optional<ProfessorDto> getProfessorById(Integer professorId){
        return professorRepository.findById(professorId).map(this::toDto);
    }
    public ProfessorDto save(ProfessorRequest professorRequest){
        return toDto(professorRepository.save(toEntity(professorRequest)));
    }
    public ProfessorDto edit(ProfessorDto professorDto){
        Professor professorFromDB = professorRepository.findById(professorDto.id())
                .orElseThrow(()->new IllegalArgumentException("Invalid id"));
        professorFromDB.setName(professorDto.name());
        professorFromDB.setLastName(professorDto.lastName());
        professorFromDB.setSecondLastName(professorDto.secondLastName());
        return toDto(professorRepository.save(professorFromDB));
    }
    public void delete(Integer id){
        try{
            professorRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            Professor professor = professorRepository.findById(id)
                    .orElseThrow(()->new IllegalArgumentException("Invalid id"));
            professor.setActive(Boolean.FALSE);
            professorRepository.save(professor);
        }
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
                professorRequest.secondLastName(),
                Boolean.TRUE
        );
    }

    @Override
    public Professor toEntity(ProfessorDto professorDto) {
        return new Professor(
                professorDto.name(),
                professorDto.lastName(),
                professorDto.lastName(),
                Boolean.TRUE
        );
    }
}
