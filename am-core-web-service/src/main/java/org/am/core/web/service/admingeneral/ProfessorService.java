package org.am.core.web.service.admingeneral;


import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Professor;
import org.am.core.web.domain.entity.admingeneral.Schedule;
import org.am.core.web.dto.admingeneral.ProfessorDto;
import org.am.core.web.dto.admingeneral.ProfessorRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.ProfessorRepository;
import org.am.core.web.repository.jpa.admingeneral.ScheduleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorService implements CustomMap<ProfessorDto, Professor> {

    private final ProfessorRepository professorRepository;
    private final ScheduleRepository scheduleRepository;

    public List<ProfessorDto> getProfessorByAreaId() {
        List<Professor> professor = professorRepository.findAll();

        return professor.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProfessorDto save(ProfessorRequest professorRequest) {
        return toDto(professorRepository.save(this.toEntity(professorRequest)));
    }

    public ProfessorDto edit(ProfessorDto professorDto) {
        Professor professorFromDB = professorRepository.findById(professorDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        professorFromDB.setName(professorDto.name());
        professorFromDB.setLastName(professorDto.lastname());
        professorFromDB.setSecondLastName(professorDto.secondLastname());

        return toDto(professorRepository.save(professorFromDB));
    }

    public void delete(Integer id) {

        try {
            professorRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            List<Schedule> schedules = scheduleRepository.findByProfessorId(id);
            scheduleRepository.deleteAll(schedules);
            professorRepository.deleteById(id);
        }

    }

    public Optional<ProfessorDto> getProfessorById(Integer id) {
        return professorRepository.findById(id).map(this::toDto);
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

    @Override
    public Professor toEntity(ProfessorDto professorDto) {
        return null;
    }

    private Professor toEntity(ProfessorRequest professorRequest) {
        Professor professor = new Professor();
        professor.setName(professorRequest.name());
        professor.setLastName(professorRequest.lastName());
        professor.setSecondLastName(professorRequest.secondLastName());
        return professor;
    }
}
