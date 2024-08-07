package com.hansliao.springboot_mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.hansliao.springboot_mall.dao.OrderDao;
import com.hansliao.springboot_mall.dao.ProductDao;
import com.hansliao.springboot_mall.dao.UserDao;
import com.hansliao.springboot_mall.dto.BuyItem;
import com.hansliao.springboot_mall.dto.CreateOrderRequest;
import com.hansliao.springboot_mall.model.Order;
import com.hansliao.springboot_mall.model.OrderItem;
import com.hansliao.springboot_mall.model.Product;
import com.hansliao.springboot_mall.model.User;
import com.hansliao.springboot_mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService{

    private final static Logger log= LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){

        // 檢查user是否存在
        User user= userDao.getUserById(userId);

        if(user== null){
            log.warn("該userId {} 不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount= 0;
        List<OrderItem> orderItemList= new ArrayList<>();

        for(BuyItem buyItem: createOrderRequest.getBuyItemList()){
            Product product= productDao.getProductById(buyItem.getProductId());

            // 檢查product是否存在、庫存是否足夠
            if(product== null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock()< buyItem.getQuantity()){
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock()- buyItem.getQuantity());

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
