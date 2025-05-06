package com.xmind.elasticsearch.repository;

import com.xmind.elasticsearch.document.DemandDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandSearchRepository extends ElasticsearchRepository<DemandDocument, String> {

    List<DemandDocument> findByTitleContainingIgnoreCase(String title);

    List<DemandDocument> findByTitleContainingIgnoreCaseAndUserId(String title, Long userId);

    void deleteByEntityId(Long entityId);
}
