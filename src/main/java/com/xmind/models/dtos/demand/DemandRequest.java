package com.xmind.models.dtos.demand;

import com.xmind.models.enums.DemandCategory;
import com.xmind.models.enums.DemandStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandRequest {
    @NotBlank(message = "validation.notnull")
    private String title;

    @NotBlank(message = "validation.notnull")
    private String description;

    @NotNull(message = "validation.notnull")
    private DemandCategory categoryKey;
}
