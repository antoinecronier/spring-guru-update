package com.springframework.guru.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {

    public static final String BASE_CACHE = "baseCache";
    public static final String BASE_CACHE_MANAGER = "baseCacheManager";

    public static final String COUNT_CACHE = "countCache";
    public static final String COUNT_CACHE_MANAGER = "countCacheManager";

    @Bean(COUNT_CACHE_MANAGER)
    public CacheManager countCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(COUNT_CACHE);

        return cacheManager;
    }

    @Primary
    @Bean(BASE_CACHE_MANAGER)
    public CacheManager baseCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(BASE_CACHE);

        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {COUNT_CACHE}, cacheManager = COUNT_CACHE_MANAGER)
    @Scheduled(fixedDelay = 2 * 60 * 1000 ,  initialDelay = 500) // 2 minutes delay
    public void reportCountCacheEvict() {
        System.out.println("Flush reportCountCacheEvict " + new Date());
    }

    @CacheEvict(allEntries = true, value = {BASE_CACHE}, cacheManager = BASE_CACHE_MANAGER)
    @Scheduled(fixedDelay = 1 * 60 * 1000 ,  initialDelay = 500) // 1 minutes delay
    public void reportBaseCacheEvict() {
        System.out.println("Flush reportBaseCacheEvict " + new Date());
    }
}
