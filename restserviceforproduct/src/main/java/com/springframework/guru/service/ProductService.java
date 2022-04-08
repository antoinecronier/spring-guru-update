package com.springframework.guru.service;


import com.springframework.guru.model.MyPageableResponse;
import com.springframework.guru.model.Product;
import org.springframework.data.domain.Slice;

import java.util.Date;
import java.util.List;

public interface ProductService {
     Product addProduct(Product product);
     Product getProduct(int id);
     List<Product> getAllProducts();
     MyPageableResponse getAllPageableProducts(List<String> partenaires, Date start, Date end, int offset, int limit);

     void addProducts(List<Product> products);
}
