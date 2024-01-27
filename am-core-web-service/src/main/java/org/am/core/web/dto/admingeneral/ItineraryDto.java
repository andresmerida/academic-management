package org.am.core.web.dto.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Career;

public record ItineraryDto(Integer id, String name, Integer careerId,String careerName ) {
}
