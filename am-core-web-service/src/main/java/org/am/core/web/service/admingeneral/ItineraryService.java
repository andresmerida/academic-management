package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Itinerary;
import org.am.core.web.dto.admingeneral.ItineraryDto;
import org.am.core.web.dto.admingeneral.ItineraryRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CareerRepository;
import org.am.core.web.repository.jpa.admingeneral.ItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItineraryService implements CustomMap<ItineraryDto, Itinerary> {
    private final ItineraryRepository itineraryRepository;
    private final CareerRepository careerRepository;

    public ItineraryService(ItineraryRepository itineraryRepository, CareerRepository careerRepository) {
        this.itineraryRepository = itineraryRepository;
        this.careerRepository = careerRepository;
    }

    public List<ItineraryDto> getActiveIntineraries(){
        return itineraryRepository.findAllByActiveOrderByName(Boolean.TRUE)
                .stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public List<ItineraryDto> getActiveIntinerariesByCareer(Integer careerID){
        return itineraryRepository.findAllByActiveAndCareerIdOrderByName(Boolean.TRUE,careerID)
                .stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ItineraryDto> getItineraryById(Integer id){
        return itineraryRepository.findById(id).map(this::toDto);
    }

    public void delete(Integer id){
        itineraryRepository.deleteById(id);
    }

    public void editActive(Integer id){
        Itinerary itineraryFromDB=itineraryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        itineraryFromDB.setActive(Boolean.FALSE);
        itineraryRepository.save(itineraryFromDB);
    }

    public ItineraryDto save(ItineraryRequest itineraryRequest){
        return toDto(itineraryRepository.save(toEntity(itineraryRequest)));
    }

    public ItineraryDto edit(ItineraryDto itineraryDto){
        Itinerary itineraryFromDB=itineraryRepository.findById(itineraryDto.id())
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        itineraryFromDB.setName(itineraryDto.name());

        return toDto(itineraryRepository.save(itineraryFromDB));
    }

    @Override
    public ItineraryDto toDto(Itinerary itinerary) {

        return new ItineraryDto(
                itinerary.getId(),
                itinerary.getName(),
                itinerary.getCareer().getId(),
                itinerary.getCareer().getName()
        );
    }

    @Override
    public Itinerary toEntity(ItineraryDto itineraryDto) {
        Career career=careerRepository.findById(itineraryDto.careerId()).orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        Itinerary itinerary=new Itinerary();
        itinerary.setId(itineraryDto.id());
        itinerary.setName(itinerary.getName());
        itinerary.setCareer(career);

        return itinerary;
    }
    public Itinerary toEntity(ItineraryRequest itineraryRequest) {
        Career career=careerRepository.findById(itineraryRequest.careerId()).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        return new Itinerary(
                itineraryRequest.name(),
                Boolean.TRUE,
                career
        );
    }
}
