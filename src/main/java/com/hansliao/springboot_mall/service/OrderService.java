package com.hansliao.springboot_mall.service;

import com.hansliao.springboot_mall.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
