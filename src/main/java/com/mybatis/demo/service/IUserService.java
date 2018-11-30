package com.mybatis.demo.service;

import com.mybatis.demo.entity.User;

public interface IUserService extends IBaseService<User> {
    public boolean isLogin(String userName, String password);
}