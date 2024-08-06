package com.hansliao.springboot_mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hansliao.springboot_mall.model.User;

public class UserRowMapper implements RowMapper<User>{
    
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException{
        User user= new User();

        user.setUserId(resultSet.getInt("user_id"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setCreatedDate(resultSet.getTimestamp("created_date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return user;

    }
}
