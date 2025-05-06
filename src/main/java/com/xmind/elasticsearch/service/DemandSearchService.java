package com.xmind.elasticsearch.service;

import com.xmind.elasticsearch.document.DemandDocument;
import com.xmind.elasticsearch.repository.DemandSearchRepository;
import com.xmind.utils.AuthUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final DemandSearchRepository repository;

    public DemandDocument save(DemandDocument eventDocument) {
        return repository.save(eventDocument);
    }

    public List<DemandDocument> searchByTitle(String title) {
        if (AuthUtils.isAdmin()) {
            return repository.findByTitleContainingIgnoreCase(title);
        } else {
            return repository.findByTitleContainingIgnoreCaseAndUserId(
                    title,
                    AuthUtils.getCurrentUserId()
            );
        }
    }

    public void deleteByEntityId(Long id) {
        repository.deleteByEntityId(id);
    }
}
