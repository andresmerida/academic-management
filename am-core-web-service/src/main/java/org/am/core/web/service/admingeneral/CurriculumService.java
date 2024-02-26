package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculumService implements CustomMap<CurriculumDto, Curriculum> {
    private final CurriculumRepository curriculumRepository;

    @Transactional(readOnly = true)
    public List<CurriculumDto> getCurriculumsByCareerId(Integer careerId) {
        return curriculumRepository.findCurriculumByCareer_IdAndActiveOrderByName(careerId, Boolean.TRUE)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CurriculumDto toDto(Curriculum curriculum) {
        return new CurriculumDto(
                curriculum.getId(),
                curriculum.getName(),
                curriculum.getMinApprovedSubjeccts(),
                curriculum.getStarDate(),
                curriculum.getEndDate()
        );
    }

    @Override
    public Curriculum toEntity(CurriculumDto curriculumDto) {
        return null;
    }
}
