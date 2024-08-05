package com.hansliao.springboot_mall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.hansliao.springboot_mall.constant.ProductCategory;
import com.hansliao.springboot_mall.dao.ProductDao;
import com.hansliao.springboot_mall.dto.ProductQueryParams;
import com.hansliao.springboot_mall.dto.ProductRequest;
import com.hansliao.springboot_mall.model.Product;
import com.hansliao.springboot_mall.rowmapper.ProductRowMapper;

/**
 * ProductDaoImpl
 */

@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql= "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id= :productId";

        Map<String, Object> map= new HashMap<>();
        map.put("productId", productId);

        List<Product> productList= namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()> 0){
            return productList.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        String sql= "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:productName, :category, :imageURL, :price, :stock, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map= new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageURL", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now= new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder= new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId= keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest){
        String sql= "UPDATE product SET product_name= :productName, category= :category, image_url= :imageUrl, price= :price, stock= :stock, description= :description, last_modified_date= :lastModifiedDate WHERE product_id= :productId";

        Map<String, Object> map= new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId){
        String sql= "DELETE FROM product WHERE product_id= :productId";

        Map<String, Object> map= new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams){
        String sql= "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE 1= 1";

        Map<String, Object> map= new HashMap<>();

        ProductCategory category= productQueryParams.getCategory();
        String search= productQueryParams.getSearch();
        String orderBy= productQueryParams.getOrderBy();
        String sort= productQueryParams.getSort();
        Integer limit= productQueryParams.getLimit();
        Integer offset= productQueryParams.getOffset();

        // 查詢條件
        if(category!= null){
            sql= sql+ " AND category= :category";
            map.put("category", category.name());
        }

        if(search!= null){
            sql= sql+ " AND product_name LIKE :search";
            map.put("search", "%"+ search+ "%");
        }

        // 排序
        // orderBy 不能用:orderBy，要用字串拼接
        sql= sql+ " ORDER BY "+ orderBy+ " "+ sort;


        // 分頁
        sql= sql+ " LIMIT :limit OFFSET :offset";
        map.put("limit", limit);
        map.put("offset", offset);

        List<Product> productList= namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }
}