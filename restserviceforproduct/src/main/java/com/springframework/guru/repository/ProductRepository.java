package com.springframework.guru.repository;

import com.springframework.guru.configuration.CacheConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springframework.guru.model.Product;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(
            value = "SELECT * FROM products ORDER BY id ASC LIMIT :limit OFFSET :offset ",
            nativeQuery = true
    )
    List<Product> getAllWithOffsetAndLimite(int offset, int limit);


    @Cacheable(
            value = CacheConfiguration.COUNT_CACHE,
            cacheManager = CacheConfiguration.COUNT_CACHE_MANAGER
    )
    @Query(
            value = "SELECT COUNT(*) FROM products WHERE partenaire IN (:partenaires) AND start >= :start AND end <= :end",
            nativeQuery = true
    )
    long countByPartenaires(List<String> partenaires, Date start, Date end);
}
