package org.am.core.web.service.admingeneral;

import org.am.core.web.repository.jpa.admingeneral.AreaRepository;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }
}
