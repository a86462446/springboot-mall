package com.hansliao.springboot_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hansliao.springboot_mall.dao.UserDao;
import com.hansliao.springboot_mall.dto.UserLoginRequest;
import com.hansliao.springboot_mall.dto.UserRegisterRequest;
import com.hansliao.springboot_mall.middleware.VarifyUser;
import com.hansliao.springboot_mall.model.User;
import com.hansliao.springboot_mall.service.UserService;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private VarifyUser varifyUser;

    @Override
    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){

        // 檢查email是否重複
        varifyUser.checkDuplicateEmail(userRegisterRequest);

        // 使用BCrypt生成密碼的雜湊值
        PasswordEncoder pe= new BCryptPasswordEncoder();
        userRegisterRequest.setPassword(pe.encode(userRegisterRequest.getPassword()));
        
        // 創建帳號
        return userDao.createUser(userRegisterRequest);
        
    }

    @Override
    public User login(UserLoginRequest userLoginRequest){

        User user= userDao.getUserByEmail(userLoginRequest.getEmail());
        
        // 檢查用戶是否存在
        varifyUser.checkUserIfExisted(user, userLoginRequest);
        
        // 檢查密碼是否正確
        varifyUser.checkPasswordIsCorrect(user, userLoginRequest);

        return user;
    }
}
