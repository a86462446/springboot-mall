package com.hansliao.springboot_mall.dao;

import com.hansliao.springboot_mall.dto.ProductRequest;
import com.hansliao.springboot_mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
