package com.xmind.mapper;

import com.xmind.entity.DemandEntity;
import com.xmind.models.dtos.demand.DemandRequest;
import com.xmind.models.dtos.demand.DemandResponse;
import com.xmind.security.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DemandMapper {

    public List<DemandResponse> toDto(List<DemandEntity> demand) {
        return demand.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DemandResponse toDto(DemandEntity demand) {
        return DemandResponse.builder()
                .id(demand.getId())
                .title(demand.getTitle())
                .description(demand.getDescription())
                .category(demand.getCategory())
                .status(demand.getStatus())
                .build();
    }

    public DemandEntity toEntity(DemandRequest request, UserEntity user, Long entityId) {
        return DemandEntity.builder()
                .id(entityId)
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(request.getStatus())
                .build();
    }
}
