package com.xmind.mapper;

import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerRequest;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.security.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class DemandAnswerMapper {

    public DemandAnswerResponse toDto(DemandAnswerEntity demand) {
        return DemandAnswerResponse.builder()
                .answerText(demand.getAnswerText())
                .userId(demand.getUser().getId())
                .id(demand.getId())
                .build();
    }

    public DemandAnswerEntity toEntity(DemandAnswerRequest request, Long entityId, UserEntity user, DemandEntity demand) {
        return DemandAnswerEntity.builder()
                .id(entityId)
                .user(user)
                .answerText(request.getAnswerText())
                .demand(demand)
                .build();
    }
}
