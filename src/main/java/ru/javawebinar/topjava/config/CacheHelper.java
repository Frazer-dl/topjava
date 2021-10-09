package ru.javawebinar.topjava.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import java.time.Duration;

public class CacheHelper {
    private static CacheHelper instance;
    private CacheManager cacheManager;
    private final CacheConfiguration<Long, Object> cacheConfiguration;
    public Long ttl = 10000L;

    private CacheHelper() {
        cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class, Object.class, ResourcePoolsBuilder.heap(40))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ttl)))
                .build();
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        putInCash();
    }

    public static CacheHelper getInstance() {
        if (instance == null) instance = new CacheHelper();
        return instance;
    }

    public void putInCash() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        cacheManager.createCache("cache", cacheConfiguration);
    }

    public Cache<Long, Object> getCache() {
        return cacheManager.getCache("cache", Long.class, Object.class);
    }

    public void clearCache() {
        cacheManager.getCache("cache", Long.class, Object.class).clear();
    }
}
