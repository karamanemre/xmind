package com.xmind.services;

import com.xmind.models.dtos.CommonEnumResponse;
import com.xmind.models.enums.CacheNames;
import com.xmind.models.enums.DemandCategory;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Cacheable(value = CacheNames.CATEGORIES, key = "#root.methodName")
    public List<CommonEnumResponse> getAllCategories() {
        return Stream.of(DemandCategory.values())
                .map(category -> CommonEnumResponse.builder()
                        .desc(TranslationService.translate("category." + category.name().toLowerCase(Locale.ENGLISH)))
                        .key(category.name())
                        .id(category.getCode())
                        .build())
                .collect(Collectors.toList());
    }
}
