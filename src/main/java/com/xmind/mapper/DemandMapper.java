package com.xmind.mapper;

import com.xmind.entity.DemandEntity;
import com.xmind.models.dtos.demand.DemandAdminResponse;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.DemandStatus;
import com.xmind.security.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DemandMapper {

    public List<DemandResponse> toDto(List<DemandEntity> demand) {
        return demand.stream()
                .map(demandItem -> toDto(demandItem, null))
                .collect(Collectors.toList());
    }

    public List<DemandAdminResponse> toDemandAdminDto(List<DemandEntity> demand) {
        return demand.stream()
                .map(demandItem -> toDemandAdminDto(demandItem, null))
                .collect(Collectors.toList());
    }

    public DemandResponse toDto(DemandEntity demand, DemandAnswerResponse answerResponse) {
        return DemandResponse.builder()
                .id(demand.getId())
                .answer(answerResponse)
                .title(demand.getTitle())
                .description(demand.getDescription())
                .category(demand.getCategory())
                .status(demand.getStatus())
                .createdDate(demand.getCreatedDate())
                .build();
    }

    public DemandAdminResponse toDemandAdminDto(DemandEntity demand, DemandAnswerResponse answerResponse) {
        return DemandAdminResponse.builder()
                .id(demand.getId())
                .answer(answerResponse)
                .title(demand.getTitle())
                .description(demand.getDescription())
                .category(demand.getCategory())
                .status(demand.getStatus())
                .createdDate(demand.getCreatedDate())
                .requesterId(demand.getUser().getId())
                .requesterMail(demand.getUser().getEmail())
                .build();
    }

    public DemandResponse toDto(DemandEntity demand) {
        return DemandResponse.builder()
                .id(demand.getId())
                .title(demand.getTitle())
                .description(demand.getDescription())
                .category(demand.getCategory())
                .status(demand.getStatus())
                .createdDate(demand.getCreatedDate())
                .build();
    }

    public DemandEntity toEntity(DemandRequest request, UserEntity user, Long entityId, DemandStatus status) {
        return DemandEntity.builder()
                .id(entityId)
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategoryKey())
                .status(status)
                .build();
    }
}
