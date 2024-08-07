package com.hansliao.springboot_mall.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hansliao.springboot_mall.dto.CreateOrderRequest;
import com.hansliao.springboot_mall.dto.OrderQueryParams;
import com.hansliao.springboot_mall.model.Order;
import com.hansliao.springboot_mall.service.OrderService;
import com.hansliao.springboot_mall.util.Page;


@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
        @PathVariable Integer userId, 
        @RequestParam(defaultValue= "2") @Max(1000) @Min(0) Integer limit, 
        @RequestParam(defaultValue= "0") @Min(0) Integer offset
    ){

        OrderQueryParams orderQueryParams= new OrderQueryParams();

        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
            
        // 取得order list
        List<Order> orderList= orderService.getOrders(orderQueryParams);

        // 取得order總數
        Integer count= orderService.countOrder(orderQueryParams);

        // 分頁
        Page<Order> page= new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId, @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId= orderService.createOrder(userId, createOrderRequest);

        Order order= orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
}
