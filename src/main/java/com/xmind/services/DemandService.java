package com.xmind.services;

import com.xmind.annonation.Logging;
import com.xmind.elasticsearch.document.DemandDocument;
import com.xmind.elasticsearch.service.DemandSearchService;
import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import com.xmind.exception.EntityNotFoundException;
import com.xmind.exception.UnexpectedException;
import com.xmind.mapper.DemandAnswerMapper;
import com.xmind.mapper.DemandMapper;
import com.xmind.models.dtos.CommonEnumResponse;
import com.xmind.models.dtos.demand.DemandAdminResponse;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.CacheNames;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandService {

    private final DemandMapper mapper;
    private final DemandSearchService demandSearchService;
    private final DemandAnswerMapper answerMapper;
    private final DemandAnswerRepository demandAnswerRepository;
    private final DemandRepository repository;

    @Logging
    public DemandResponse create(DemandRequest request, UserEntity user) {
        DemandEntity demand = mapper.toEntity(request, user, null, DemandStatus.OPEN);

        try {
            DemandEntity savedEntity = repository.save(demand);
            demandSearchService.save(mapper.toDocument(savedEntity));
            return mapper.toDto(savedEntity);
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
    }

    @Logging
    public DemandResponse update(Long id, DemandRequest request, UserEntity user) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());

        try {
            DemandEntity convertedEntity = mapper.toEntity(request, demand.getUser(), demand.getId(), demand.getStatus());
            return mapper.toDto(repository.save(convertedEntity));
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
    }

    @Logging
    public void delete(Long id) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());

        try {
            demandSearchService.deleteByEntityId(id);
            repository.delete(demand);
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
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
        this.findByIdOrElseThrow(id);
        repository.updateStatus(status, id);
    }

    public List<DemandDocument> getBySearchParam(UserEntity user, String search) {
        List<DemandDocument> demandDocuments = demandSearchService.searchByTitle(search);
        return demandDocuments;
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
