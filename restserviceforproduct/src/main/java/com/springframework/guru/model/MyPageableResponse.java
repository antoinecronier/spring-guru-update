package com.springframework.guru.model;

import java.util.ArrayList;
import java.util.List;

public class MyPageableResponse {
    private long count;
    private List<Product> products = new ArrayList<>();

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
