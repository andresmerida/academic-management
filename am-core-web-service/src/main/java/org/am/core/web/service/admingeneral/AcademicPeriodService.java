package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.dto.admingeneral.AcademicPeriodDto;
import org.am.core.web.dto.admingeneral.AcademicPeriodRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AcademicPeriodRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicPeriodService implements CustomMap<AcademicPeriodDto, AcademicPeriod> {
    private final AcademicPeriodRepository academicPeriodRepository;

    public AcademicPeriodService(AcademicPeriodRepository academicPeriodRepository) {
        this.academicPeriodRepository = academicPeriodRepository;
    }

    public List<AcademicPeriodDto> getActiveAcademicPeriodByAreaId(Integer areaID) {
        List<AcademicPeriod> listaAcademicPeriods = academicPeriodRepository.findAllByArea_IdOrderById(areaID);

        List<AcademicPeriod> academicPeriods = listaAcademicPeriods.stream()
                .filter(c -> c.getActive().equals(Boolean.TRUE))
                .collect(Collectors.toList());


        return academicPeriods.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public List<AcademicPeriodDto> findAll(){
        return  academicPeriodRepository.findAll()
                .stream()
                .map(this::toDto).collect(Collectors.toList());

    }

    public AcademicPeriodDto save (AcademicPeriodRequest academicPeriodRequest){
        return toDto(academicPeriodRepository.save(this.toEntity(academicPeriodRequest)));
    }

    public AcademicPeriodDto edit(AcademicPeriodDto academicPeriodDto){
        AcademicPeriod academicPeriod=academicPeriodRepository.findById(academicPeriodDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        academicPeriod.setYear(academicPeriodDto.year());
        academicPeriod.setName(academicPeriodDto.name());
        academicPeriod.setStartDate(academicPeriodDto.startDate());
        academicPeriod.setEndDate(academicPeriodDto.endDate());
        academicPeriod.setActive(academicPeriodDto.active());
        return toDto(academicPeriodRepository.save(academicPeriod));

    }
    public void delete(Integer id){
        academicPeriodRepository.deleteById(id);
    }

    public Optional<AcademicPeriodDto> getAcademicPeriodById(Integer id){
        Optional<AcademicPeriod> academicPeriodOptional = academicPeriodRepository.findById(id);
        if (academicPeriodOptional.isPresent()) {
            AcademicPeriod academicPeriod = academicPeriodOptional.get();
            return Optional.of(toDto(academicPeriod));
        } else {
            return Optional.empty();
        }
    }

    public List<AcademicPeriodDto> findAllSortedByYearDescending() {

        List<AcademicPeriod> academicPeriods = academicPeriodRepository.findAll(Sort.by(Sort.Direction.DESC, "year"));
        return academicPeriods.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AcademicPeriodDto toDto(AcademicPeriod academicPeriod) {
        return new AcademicPeriodDto(
                academicPeriod.getId(),
                academicPeriod.getYear(),
                academicPeriod.getName(),
                academicPeriod.getStartDate(),
                academicPeriod.getEndDate(),
                academicPeriod.getActive()
        );
    }

    @Override
    public AcademicPeriod toEntity(AcademicPeriodDto academicPeriodDto) {
       AcademicPeriod academicPeriod= new AcademicPeriod();
       academicPeriod.setId(academicPeriodDto.id());
       academicPeriod.setYear(academicPeriodDto.year());
       academicPeriod.setName(academicPeriodDto.name());
       academicPeriod.setStartDate(academicPeriodDto.startDate());
       academicPeriod.setEndDate(academicPeriodDto.endDate());
       academicPeriod.setActive(academicPeriodDto.active());
       return academicPeriod;
    }
    private AcademicPeriod toEntity(AcademicPeriodRequest academicPeriodRequest) {
        return new AcademicPeriod(
                academicPeriodRequest.year(),
                academicPeriodRequest.name(),
                academicPeriodRequest.startDate(),
                academicPeriodRequest.endDate(),
                academicPeriodRequest.active()
        );
    }
}