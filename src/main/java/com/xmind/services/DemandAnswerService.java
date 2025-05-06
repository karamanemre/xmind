package com.xmind.services;

import com.xmind.annonation.Logging;
import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import com.xmind.exception.EntityNotFoundException;
import com.xmind.exception.UnexpectedException;
import com.xmind.mapper.DemandAnswerMapper;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerRequest;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.DemandStatus;
import com.xmind.repository.DemandAnswerRepository;
import com.xmind.security.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemandAnswerService {

    private final DemandService demandService;
    private final DemandAnswerMapper mapper;
    private final DemandAnswerRepository repository;

    @Transactional
    @Logging
    public DemandAnswerResponse create(DemandAnswerRequest request, UserEntity user) {
        DemandEntity demand = demandService.findByIdOrElseThrow(request.demandId);
        DemandAnswerEntity demandAnswer = mapper.toEntity(request, null, user, demand);

        try {
            DemandAnswerEntity savedEntity = repository.save(demandAnswer);
            demandService.setStatus(demand.getId(), DemandStatus.RESPONDED);
            return mapper.toDto(savedEntity);
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
    }

    @Transactional
    @Logging
    public DemandAnswerResponse update(Long id, DemandAnswerRequest request, UserEntity user) {
        DemandAnswerEntity entityInDb = findByIdOrElseThrow(id);
        DemandEntity demand = demandService.findByIdOrElseThrow(request.demandId);

        try {
            DemandAnswerEntity demandAnswer = mapper.toEntity(request, entityInDb.getId(), user, demand);
            return mapper.toDto(repository.save(demandAnswer));
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
    }

    @Logging
    public void delete(Long id) {
        DemandAnswerEntity entityInDb = findByIdOrElseThrow(id);

        try {
            repository.delete(entityInDb);
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected exception, detail: ", e);
            throw new UnexpectedException();
        }
    }

    private DemandAnswerEntity findByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı."));
    }
}
