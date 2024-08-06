package com.hansliao.springboot_mall.dao;

import com.hansliao.springboot_mall.dto.UserRegisterRequest;
import com.hansliao.springboot_mall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
