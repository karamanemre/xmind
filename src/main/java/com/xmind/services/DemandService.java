package com.xmind.services;

import com.xmind.annonation.Logging;
import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import com.xmind.exception.EntityNotFoundException;
import com.xmind.mapper.DemandAnswerMapper;
import com.xmind.mapper.DemandMapper;
import com.xmind.models.dtos.CommonEnumResponse;
import com.xmind.models.dtos.demand.DemandAdminResponse;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.CacheNames;
import com.xmind.models.enums.DemandCategory;
import com.xmind.models.enums.DemandStatus;
import com.xmind.repository.DemandAnswerRepository;
import com.xmind.repository.DemandRepository;
import com.xmind.security.entity.UserEntity;
import com.xmind.utils.AuthUtils;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandMapper mapper;
    private final DemandAnswerMapper answerMapper;
    private final DemandAnswerRepository demandAnswerRepository;
    private final DemandRepository repository;

    @Logging
    public DemandResponse create(DemandRequest request, UserEntity user) {
        DemandEntity demand = mapper.toEntity(request, user, null, DemandStatus.OPEN);
        return mapper.toDto(repository.save(demand));
    }

    @Logging
    public DemandResponse update(Long id, DemandRequest request, UserEntity user) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());
        DemandEntity convertedEntity = mapper.toEntity(request, demand.getUser(), demand.getId(), demand.getStatus());
        return mapper.toDto(repository.save(convertedEntity));
    }

    @Logging
    public void delete(Long id) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());
        repository.delete(demand);
    }

    public List<DemandResponse> getAll(Pageable pageable, DemandStatus status, UserEntity user) {
        List<DemandEntity> demands = repository.getByParameters(status, pageable, user);
        return mapper.toDto(demands);
    }

    public List<DemandAdminResponse> getAllDemandsForAdmin(Pageable pageable, DemandStatus status) {
        List<DemandEntity> demands = repository.getByParameters(status, pageable);
        return mapper.toDemandAdminDto(demands);
    }

    public DemandResponse getById(Long id) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());

        if (demand.getStatus() == DemandStatus.RESPONDED) {
            DemandAnswerEntity demandAnswer = demandAnswerRepository.findByDemand(demand);
            DemandAnswerResponse answerResponse = answerMapper.toDto(demandAnswer);
            return mapper.toDto(demand, answerResponse);
        }
        return mapper.toDto(demand);
    }

    public void setStatus(Long id, DemandStatus status) {
        repository.updateStatus(status, id);
    }

    public DemandEntity findByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı."));
    }

    @Cacheable(value = CacheNames.DEMAND_STATUS, key = "#root.methodName")
    public List<CommonEnumResponse> getStatuses() {
        return Stream.of(DemandStatus.values())
                .map(category -> CommonEnumResponse.builder()
                        .desc(TranslationService.translate("demand." + category.name().toLowerCase(Locale.ENGLISH)))
                        .key(category.name())
                        .id(category.getCode())
                        .build())
                .collect(Collectors.toList());
    }
}
