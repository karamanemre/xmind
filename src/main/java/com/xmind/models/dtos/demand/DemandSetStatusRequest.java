package com.xmind.models.dtos.demand;

import com.xmind.models.enums.DemandStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandSetStatusRequest {
    @NotNull(message = "validation.notnull")
    private DemandStatus status;
}
