package com.hansliao.springboot_mall.service;

import com.hansliao.springboot_mall.dto.CreateOrderRequest;
import com.hansliao.springboot_mall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
