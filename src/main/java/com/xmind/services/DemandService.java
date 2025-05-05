package com.xmind.services;

import com.xmind.annonation.Logging;
import com.xmind.entity.DemandEntity;
import com.xmind.exception.EntityNotFoundException;
import com.xmind.mapper.DemandMapper;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.enums.DemandStatus;
import com.xmind.repository.DemandRepository;
import com.xmind.security.entity.UserEntity;
import com.xmind.utils.AuthUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandMapper mapper;
    private final DemandRepository repository;

    @Logging
    public DemandResponse create(DemandRequest request, UserEntity user) {
        DemandEntity demand = mapper.toEntity(request, user, null);
        return mapper.toDto(repository.save(demand));
    }

    @Logging
    public DemandResponse update(Long id, DemandRequest request, UserEntity user) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());
        DemandEntity convertedEntity = mapper.toEntity(request, demand.getUser(), demand.getId());
        return mapper.toDto(repository.save(convertedEntity));
    }

    @Logging
    public void delete(Long id) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());
        repository.delete(demand);
    }

    public List<DemandResponse> getAll(Pageable pageable, DemandStatus status) {
        List<DemandEntity> demands = repository.getByParameters(status, pageable);
        return mapper.toDto(demands);
    }

    public DemandResponse getById(Long id) {
        DemandEntity demand = findByIdOrElseThrow(id);
        AuthUtils.isRealOwner(demand.getUser().getId());
        return mapper.toDto(demand);
    }

    public void setStatus(Long id, DemandStatus status) {
        repository.updateStatus(status, id);
    }

    public DemandEntity findByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı."));
    }
}
