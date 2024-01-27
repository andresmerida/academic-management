package org.am.core.web.repository.jpa.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary,Integer> {
    List<Itinerary> findAllByActiveOrderByName(Boolean active);

    List<Itinerary> findAllByActiveAndCareerIdOrderByName(Boolean active, Integer careerId);
}
