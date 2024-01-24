package org.am.core.web.service.admingeneral;


import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.dto.admingeneral.CareerDto;
import org.am.core.web.dto.admingeneral.CareerRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CareerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CareerService implements CustomMap<CareerDto, Career> {
    private final CareerRepository careerRepository;


    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public List<CareerDto> getActiveCareersByAreaId(Integer areaID) {
        List<Career> careers = careerRepository.findAllByArea_IdOrderById(areaID);

        List<Career> activeCareers = careers.stream()
                .filter(c -> c.getActive().equals(Boolean.TRUE))
                .collect(Collectors.toList());

        return activeCareers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CareerDto> getCareerByIdByArea(Integer id) {

        Career career = careerRepository.findById(id).orElseThrow();
        Integer areaId = career.getArea().getId();

        List<CareerDto> careers = getActiveCareersByAreaId(areaId);

        CareerDto foundCareer = careers.stream()
                .filter(c -> c.id().equals(id))
                .findFirst()
                .orElseThrow();

        return Optional.of(foundCareer);

    }




    public CareerDto save(CareerRequest careerRequest) {
        return toDto(careerRepository.save(this.toEntity(careerRequest)));
    }

    public CareerDto edit(CareerDto careerDto) {
        Career careerFromDB = careerRepository.findById(careerDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        careerFromDB.setName(careerDto.name());
        careerFromDB.setInitials(careerDto.initials());
        careerFromDB.setDescription(careerDto.description());
        careerFromDB.setCreationDate(careerDto.creationDate());
        careerFromDB.setCode(careerDto.code());
        careerFromDB.setArea(careerDto.area());
        return toDto(careerRepository.save(careerFromDB));
    }

    public CareerDto editActive(CareerDto careerDto) {
        Career careerFromDB = careerRepository.findById(careerDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        careerFromDB.setActive(false);
        return toDto(careerRepository.save(careerFromDB));
    }


    public void delete(Integer id) {
        careerRepository.deleteById(id);
    }

    public Optional<CareerDto> getCareerById(Integer id) {
        return careerRepository.findById(id).map(this::toDto);
    }

    @Override
    public CareerDto toDto(Career career) {
        return new CareerDto(
                career.getId(),
                career.getName(),
                career.getInitials(),
                career.getDescription(),
                career.getCreationDate(),
                career.getActive(),
                career.getCode(),
                career.getArea()
        );
    }

    @Override
    public Career toEntity(CareerDto careerDto) {
        Career career = new Career();
        career.setId(careerDto.id());
        career.setName(careerDto.name());
        career.setInitials(careerDto.initials());
        career.setDescription(careerDto.description());
        career.setCreationDate(careerDto.creationDate());
        career.setActive(careerDto.active());
        career.setArea(careerDto.area());
        return career;
    }

    private Career toEntity(CareerRequest careerRequest) {
        return new Career(
                careerRequest.name(),
                careerRequest.initials(),
                careerRequest.description(),
                Boolean.TRUE,
                careerRequest.code(),
                LocalDate.now(),
                careerRequest.area()
        );
    }
}
