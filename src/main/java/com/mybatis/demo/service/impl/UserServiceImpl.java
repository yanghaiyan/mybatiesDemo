package com.mybatis.demo.service.impl;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Override
    public boolean isLogin(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return false;
        }

        User user = findByUserName(userName);
        if (password.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
