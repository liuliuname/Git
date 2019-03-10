package com.example.springboot.spring.impl;


import com.example.springboot.spring.dao.UserDao;
import com.example.springboot.spring.service.UserService;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getUser(String name){
        return userDao.getUserDetail(name);
    }
}
