package com.xmind.models.dtos.demandAnswer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandAnswerRequest {
    @NotBlank(message = "validation.notnull")
    public String answerText;

    @NotNull(message = "validation.notnull")
    public Long demandId;
}
