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

import com.hansliao.springboot_mall.dao.UserDao;
import com.hansliao.springboot_mall.dto.UserRegisterRequest;
import com.hansliao.springboot_mall.model.User;
import com.hansliao.springboot_mall.rowmapper.UserRowMapper;

@Component
public class UserDaoImpl implements UserDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Override
    public User getUserById(Integer userId){
        String sql= "SELECT user_id, password, email, created_date, last_modified_date FROM user WHERE user_id= :userId";

        Map<String, Object> map= new HashMap<>();
        map.put("userId", userId);

        List<User> userList= namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList.size()> 0){
            return userList.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){
        String sql= "INSERT INTO user(email, password, created_date, last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        String email= userRegisterRequest.getEmail();
        String password= userRegisterRequest.getPassword();

        Map<String, Object> map= new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        
        Date now= new Date();

        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder= new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer userId= keyHolder.getKey().intValue();

        return userId;
    }
}
