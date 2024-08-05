package com.hansliao.springboot_mall.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hansliao.springboot_mall.constant.ProductCategory;
import com.hansliao.springboot_mall.dto.ProductQueryParams;
import com.hansliao.springboot_mall.dto.ProductRequest;
import com.hansliao.springboot_mall.model.Product;
import com.hansliao.springboot_mall.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢商品列表功能
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required= false) ProductCategory category, 
            @RequestParam(required= false) String search, 

            // 排序 Sorting
            @RequestParam(defaultValue= "created_date") String orderBy, 
            @RequestParam(defaultValue= "desc") String sort
        ){

        ProductQueryParams productQueryParams= new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        List<Product> productList= productService.getProducts(productQueryParams);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    
    // 取得商品功能
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product= productService.getProductById(productId);

        if(product!= null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 新增商品功能
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId= productService.createProduct(productRequest);

        Product product= productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // 更新商品功能
    @PostMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest){
        // 檢查product是否存在
        Product product= productService.getProductById(productId);
        if(product== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        // 修改商品數據
        productService.updateProduct(productId, productRequest);

        Product updatedProduct= productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    // 刪除商品功能
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
