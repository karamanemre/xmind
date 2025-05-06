package com.xmind.mapper;

import com.xmind.entity.DemandAnswerEntity;
import com.xmind.entity.DemandEntity;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerRequest;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.security.entity.UserEntity;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DemandAnswerMapper {

    public List<DemandAnswerResponse> toDto(List<DemandAnswerEntity> demand) {
        return demand.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DemandAnswerResponse toDto(DemandAnswerEntity demand) {
        return DemandAnswerResponse.builder()
                .answerText(demand.getAnswerText())
                .demandId(demand.getDemand().getId())
                .answeredDate(demand.getCreatedDate())
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
