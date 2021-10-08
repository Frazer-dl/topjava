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

    private CacheManager cacheManager;
    private Cache<Long, Object> cache;
    private CacheConfiguration<Long, Object> cacheConfiguration;
    public Long ttl = 10000L;

    public CacheHelper() {
        cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class, Object.class, ResourcePoolsBuilder.heap(40))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ttl)))
                .build();
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        putInCash();
    }

    public void putInCash() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        cache = cacheManager.createCache("cache", cacheConfiguration);
    }

    public Cache<Long, Object> getCache() {
        return cacheManager.getCache("cache", Long.class, Object.class);
    }
}
