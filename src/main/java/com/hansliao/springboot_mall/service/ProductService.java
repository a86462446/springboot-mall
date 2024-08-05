package com.hansliao.springboot_mall.service;

import com.hansliao.springboot_mall.dto.ProductRequest;
import com.hansliao.springboot_mall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
