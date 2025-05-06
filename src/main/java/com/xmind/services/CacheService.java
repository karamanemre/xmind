package com.xmind.services;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> void saveToCache(String cacheName, String key, T data) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, data);
            log.info("Veri cache'e kaydedildi - Cache: {}, Key: {}", cacheName, key);
        } else {
            log.warn("Cache bulunamadı: {}", cacheName);
        }
    }

    public <T> Optional<T> getFromCache(String cacheName, String key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (wrapper != null && wrapper.get() != null) {
                return Optional.of(type.cast(wrapper.get()));
            }
        }
        return Optional.empty();
    }

    public void evictCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            log.info("Cache temizlendi - Cache: {}, Key: {}", cacheName, key);
        }
    }

    public void evictAllCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            log.info("Tüm cache temizlendi - Cache: {}", cacheName);
        }
    }
}
