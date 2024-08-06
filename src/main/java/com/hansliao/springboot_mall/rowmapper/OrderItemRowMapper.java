package com.hansliao.springboot_mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hansliao.springboot_mall.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem>{
    
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException{
        OrderItem orderItem= new OrderItem();

        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductId(resultSet.getInt("product_id"));
        orderItem.setAmount(resultSet.getInt("amount"));
        orderItem.setQuantity(resultSet.getInt("quantity"));

        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setImageUrl(resultSet.getString("image_url"));

        return orderItem;
    }
}
