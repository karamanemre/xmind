package com.xmind.models.dtos.demandAnswer;

import com.xmind.entity.Model;
import java.util.Date;
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
public class DemandAnswerResponse implements Model {
    private Long id;
    private Long demandId;
    private String answerText;
    private Date answeredDate;
}
