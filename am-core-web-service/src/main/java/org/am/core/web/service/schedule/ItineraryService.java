package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.schedule.Itinerary;
import org.am.core.web.dto.schedule.ItineraryDto;
import org.am.core.web.dto.schedule.ItineraryRequest;
import org.am.core.web.repository.jdbc.schedule.ItineraryJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItineraryService implements CustomMap<ItineraryDto, Itinerary> {
    private final ItineraryRepository itineraryRepository;
    private final ItineraryJdbcRepository itineraryJdbcRepository;

    public ItineraryDto save(ItineraryRequest itineraryRequest){
        return toDto(itineraryRepository.save(toEntity(itineraryRequest)));
    }

    public List<ItineraryDto> listItinerariesByAreaId(Integer areaId) {
        return itineraryJdbcRepository.getItinerariesByAreaId(areaId);
    }

    public Optional<ItineraryDto> getItineraryById(Integer id){
        return itineraryRepository.findById(id).map(this::toDto);
    }

    public ItineraryDto edit(ItineraryRequest itineraryDto, Integer itineraryId){
        Itinerary itineraryFromDB = itineraryRepository.findById(itineraryId)
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
                itinerary.getCareer().getName(),
                itinerary.getCareer().getInitials()
        );
    }

    @Override
    public Itinerary toEntity(ItineraryDto itineraryDto) {
        return null;
    }

    public Itinerary toEntity(ItineraryRequest itineraryRequest) {
        Itinerary itinerary = new Itinerary();
        itinerary.setName(itineraryRequest.name());
        itinerary.setActive(Boolean.TRUE);

        Career career = new Career();
        career.setId(itineraryRequest.careerId());

        itinerary.setCareer(career);
        return itinerary;
    }

    public void delete(Integer id){
        itineraryRepository.deleteById(id);
    }
}
