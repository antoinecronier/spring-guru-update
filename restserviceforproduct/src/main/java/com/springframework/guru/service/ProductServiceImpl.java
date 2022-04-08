package com.springframework.guru.service;

import com.springframework.guru.configuration.CacheConfiguration;
import com.springframework.guru.model.MyPageableResponse;
import com.springframework.guru.model.Product;
import com.springframework.guru.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;


    public ProductServiceImpl() {
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }


    @Caching(
            evict = {
                @CacheEvict(value = CacheConfiguration.BASE_CACHE, key = "#product.id", cacheManager = CacheConfiguration.BASE_CACHE_MANAGER)
            }
    )
    @Override
    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    @Cacheable(value = CacheConfiguration.BASE_CACHE, key ="#id", cacheManager = CacheConfiguration.BASE_CACHE_MANAGER)
    @Override
    public Product getProduct(int id) {

        System.out.println("Data is about to be retrieved from database ");
        Product retrievedProduct = null;
        retrievedProduct = productRepository.getOne(id);
        System.out.println("Data retrieved from database");
        return retrievedProduct;
    }

    @Cacheable(value = CacheConfiguration.BASE_CACHE, cacheManager = CacheConfiguration.BASE_CACHE_MANAGER)
    @Override
    public List<Product> getAllProducts() {

        System.out.println("Data is retrieved from database ");
        return (List<Product>) productRepository.findAll();
    }

    @Cacheable(value = CacheConfiguration.BASE_CACHE, cacheManager = CacheConfiguration.BASE_CACHE_MANAGER)
    @Override
    public MyPageableResponse getAllPageableProducts(List<String> partenaires, Date start, Date end, int offset, int limit) {
        MyPageableResponse result = new MyPageableResponse();
        result.getProducts().addAll(productRepository.getAllWithOffsetAndLimite(offset, limit));
        result.setCount(productRepository.countByPartenaires(partenaires, start, end));
        return result;
    }

    @Override
    public void addProducts(List<Product> products) {
        productRepository.saveAll(products);
    }
}
