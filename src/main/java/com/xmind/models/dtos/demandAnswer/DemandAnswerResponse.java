package com.xmind.models.dtos.demandAnswer;

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
public class DemandAnswerResponse {
    private Long id;
    private String answerText;
    private Long userId;
}
