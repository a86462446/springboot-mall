package com.hansliao.springboot_mall.middleware;

import com.hansliao.springboot_mall.dto.UserLoginRequest;
import com.hansliao.springboot_mall.dto.UserRegisterRequest;
import com.hansliao.springboot_mall.model.User;

public interface VarifyUser {

    void checkDuplicateEmail(UserRegisterRequest userRegisterRequest);

    void checkUserIfExisted(User user, UserLoginRequest userLoginRequest);

    User checkPasswordIsCorrect(User user, UserLoginRequest userLoginRequest);
}
