package com.hansliao.springboot_mall.middleware.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.hansliao.springboot_mall.dao.UserDao;
import com.hansliao.springboot_mall.dto.UserLoginRequest;
import com.hansliao.springboot_mall.dto.UserRegisterRequest;
import com.hansliao.springboot_mall.middleware.VarifyUser;
import com.hansliao.springboot_mall.model.User;

@Component
public class VarifyUserImpl implements VarifyUser{

    private final static Logger log= LoggerFactory.getLogger(VarifyUserImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public void checkDuplicateEmail(UserRegisterRequest userRegisterRequest){

        User user= userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user!= null){
            log.warn("該email {} 已存在", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void checkUserIfExisted(User user, UserLoginRequest userLoginRequest){
        if(user== null){
            log.warn("該email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User checkPasswordIsCorrect(User user, UserLoginRequest userLoginRequest){
        PasswordEncoder pe= new BCryptPasswordEncoder();

        if(pe.matches(userLoginRequest.getPassword(), user.getPassword())){
            return user;
        }
        else{
            log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
