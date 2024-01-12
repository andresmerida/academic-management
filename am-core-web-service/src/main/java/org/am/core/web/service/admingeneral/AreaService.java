package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.dto.admingeneral.AreaDto;
import org.am.core.web.dto.admingeneral.AreaRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService implements CustomMap<AreaDto, Area> {
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaDto> findAllActive() {
        return areaRepository.findAllByActiveOrderByName(Boolean.TRUE)
                .stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public AreaDto save(AreaRequest areaRequest) {
        return toDto(areaRepository.save(this.toEntity(areaRequest)));
    }

    public AreaDto edit(AreaDto areaDto) {
        Area areaFromDB = areaRepository.findById(areaDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        areaFromDB.setName(areaDto.name());
        areaFromDB.setInitials(areaDto.initials());
        return toDto(areaRepository.save(areaFromDB));
    }

    public Optional<AreaDto> getAreaById(Integer id) {
        return areaRepository.findById(id).map(this::toDto);
    }

    public void delete(Integer id) {
        areaRepository.deleteById(id);
    }

    @Override
    public AreaDto toDto(Area area) {
        return new AreaDto(
                area.getId(),
                area.getName(),
                area.getInitials()
        );
    }

    @Override
    public Area toEntity(AreaDto areaDto) {
        Area area = new Area();
        area.setId(areaDto.id());
        area.setName(areaDto.name());
        area.setInitials(areaDto.initials());
        return area;
    }

    private Area toEntity(AreaRequest areaRequest) {
        return new Area(
                areaRequest.name(),
                areaRequest.initials(),
                Boolean.TRUE
        );
    }
}
