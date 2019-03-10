package com.example.springboot.spring.impl;

import com.example.springboot.spring.dao.UserDao;

public class UserDaoImpl implements UserDao {

    @Override
    public String getUserDetail(String name) {
        return "hello" + name;
    }
}
