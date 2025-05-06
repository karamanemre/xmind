package com.xmind.elasticsearch.document;

import com.xmind.entity.Model;
import com.xmind.models.dtos.demandAnswer.DemandAnswerResponse;
import com.xmind.models.enums.DemandCategory;
import com.xmind.models.enums.DemandStatus;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "demands")
public class DemandDocument implements Model {
    @Id
    private String id;
    private Long entityId;
    private Long userId;
    private String title;
    private String description;
    private DemandCategory category;
    private DemandStatus status;
    protected Date createdDate;
}
