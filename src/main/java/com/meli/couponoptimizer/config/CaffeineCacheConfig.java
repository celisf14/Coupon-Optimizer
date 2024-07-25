package com.meli.couponoptimizer.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCacheConfig {

  @Value("${cache.items.ttl}")
  private long ttl;

  @Value("${cache.items.max-size}")
  private int maxSize;

  public static final String ITEMS_CACHE = "itemsCache";

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager(ITEMS_CACHE);
    cacheManager.setCaffeine(buildCache());
    return cacheManager;
  }

  Caffeine<Object, Object> buildCache() {
    return Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(maxSize)
            .expireAfterAccess(ttl, TimeUnit.MINUTES)
            .recordStats();
  }


}