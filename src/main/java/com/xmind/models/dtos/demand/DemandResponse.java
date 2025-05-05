package com.xmind.models.dtos.demand;

import com.xmind.models.enums.DemandCategory;
import com.xmind.models.enums.DemandStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandResponse {
    private Long id;
    private String title;
    private String description;
    private DemandCategory category;
    private DemandStatus status;
}
