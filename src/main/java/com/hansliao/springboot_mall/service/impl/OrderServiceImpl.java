package com.hansliao.springboot_mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hansliao.springboot_mall.dao.OrderDao;
import com.hansliao.springboot_mall.dao.ProductDao;
import com.hansliao.springboot_mall.dto.BuyItem;
import com.hansliao.springboot_mall.dto.CreateOrderRequest;
import com.hansliao.springboot_mall.model.Order;
import com.hansliao.springboot_mall.model.OrderItem;
import com.hansliao.springboot_mall.model.Product;
import com.hansliao.springboot_mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){

        int totalAmount= 0;
        List<OrderItem> orderItemList= new ArrayList<>();

        for(BuyItem buyItem: createOrderRequest.getBuyItemList()){
            Product product= productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount= product.getPrice()* buyItem.getQuantity();
            totalAmount+= amount;

            // 轉換BuyItem to OrderItem
            OrderItem orderItem= new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }


        // 創建訂單
        Integer orderId= orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId){
        Order order= orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList= orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);
        
        return order;
    }
}
