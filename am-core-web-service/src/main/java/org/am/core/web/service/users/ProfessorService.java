package org.am.core.web.service.users;

import org.am.core.web.domain.entity.users.Professor;
import org.am.core.web.dto.users.ProfessorDto;
import org.am.core.web.dto.users.ProfessorRequest;
import org.am.core.web.repository.jdbc.schedule.AreaProfessorJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.users.ProfessorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfessorService implements CustomMap<ProfessorDto, Professor> {
    private final ProfessorRepository professorRepository;
    private final AreaProfessorJdbcRepository areaProfessorJdbcRepository;
    public ProfessorService(ProfessorRepository professorRepository, AreaProfessorJdbcRepository areaProfessorJdbcRepository) {
        this.professorRepository = professorRepository;
        this.areaProfessorJdbcRepository = areaProfessorJdbcRepository;
    }
    @Transactional(readOnly = true)
    public Optional<ProfessorDto> getProfessorById(Integer id){
        return professorRepository.findById(id).map(this::toDto);
    }

    public List<ProfessorDto> listProfessorsByAreaIdAndActive(Integer areaId){
        return areaProfessorJdbcRepository.getProfessorsByAreaIdAndActive(areaId);
    }
    public ProfessorDto save(ProfessorRequest professorRequest){
        return toDto((professorRepository.save(toEntity(professorRequest))));
    }
    public void addProfessorToArea(Integer professorId, Integer areaId){
        areaProfessorJdbcRepository.addProfessorToArea(professorId, areaId);
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
        if(areaProfessorJdbcRepository.getAreaCountByProfessorId(id)>1){
            throw new IllegalArgumentException("Professor is registered in more than one area");
        }else {
            try {
                professorRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new IllegalArgumentException("Invalid id");
            }
        }
    }
    public void deleteFromArea(Integer id, Integer areaId){
        if (areaProfessorJdbcRepository.findProfessorByAreaIdAndActive(id, areaId) == null){
            throw new IllegalArgumentException("Invalid id");
        }else{
            areaProfessorJdbcRepository.desactiveAreaProfessor(id, areaId);
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

    @Override
    public Professor toEntity(ProfessorDto professorDto) {return null;}

    public Professor toEntity(ProfessorRequest professorRequest) {
        Professor professor = new Professor();
        professor.setName(professorRequest.name());
        professor.setLastName(professorRequest.lastName());
        professor.setSecondLastName(professorRequest.secondLastName());

        return professor;
    }
}
