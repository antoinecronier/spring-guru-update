package com.springframework.guru.controller;

import com.springframework.guru.configuration.CacheConfiguration;
import com.springframework.guru.model.MyPageableRequest;
import com.springframework.guru.model.MyPageableResponse;
import com.springframework.guru.model.Product;
import com.springframework.guru.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/version1/")
public class ProductController {
    private ProductService productService;

    //private static final Logger logger = LoggerFactory.getL(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("product")
    public ResponseEntity<Product> saveAProduct(@RequestBody Product product){
    Product saveProduct = productService.addProduct(product);
    return new ResponseEntity<>(saveProduct,HttpStatus.OK);
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProducts(){

        return new ResponseEntity<List<Product>>(
                (List <Product>) productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product retrievedProduct = productService.getProduct(id);
        return new ResponseEntity<Product>(retrievedProduct, HttpStatus.OK);
    }

    @PostMapping("products")
    public ResponseEntity<MyPageableResponse> getProductById(@RequestBody MyPageableRequest request){
        MyPageableResponse result = new MyPageableResponse();

        result = productService.getAllPageableProducts(request.getPartenaires(), request.getStart(), request.getEnd(), request.getOffset(), request.getLimit());

        return new ResponseEntity<MyPageableResponse>(result, HttpStatus.OK);
    }

    @PostMapping("bulkproducts")
    public ResponseEntity<String> bulkProducts(){
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Product p = new Product();
            p.setPrice(i*3/2);
            p.setPName("p"+i);
            p.setPartenaire("partenaire" + i%7);

            Date start = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.DATE, -i%10);
            start = c.getTime();
            p.setStart(start);

            Date end = new Date();
            Calendar cEnd = Calendar.getInstance();
            cEnd.setTime(end);
            cEnd.add(Calendar.DATE, i%10);
            end = c.getTime();
            p.setEnd(end);

            products.add(p);
        }

        productService.addProducts(products);

        return new ResponseEntity<String>("Well god", HttpStatus.OK);
    }

    @Autowired
    @Qualifier(CacheConfiguration.COUNT_CACHE_MANAGER)
    private CacheManager countCache;

    @GetMapping("cache")
    public ResponseEntity<ConcurrentHashMap<SimpleKey, Long>> getProductById(){

        Cache cache = countCache.getCache(CacheConfiguration.COUNT_CACHE);
        ConcurrentHashMap<SimpleKey, Long> data = (ConcurrentHashMap<SimpleKey, Long>) cache.getNativeCache();

        return new ResponseEntity<ConcurrentHashMap<SimpleKey, Long>>(data, HttpStatus.OK);
    }
}
