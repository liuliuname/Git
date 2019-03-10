package com.example.springboot.proxy;

import com.example.springboot.spring.dao.UserDao;

public class ProxyTest {

    public static void main(String[] args) {
        //目标对象
        UserDaoProxy target = new UserDaoProxy();

        //代理对象
        UserDaoProxy proxy = (UserDaoProxy)new CglibProxyFactory(target).getProxyInstance();

        //执行代理对象的方法
        proxy.save();




        // 目标对象
        carDaoInte target1 = new carDao();
        // 【原始的类型 class cn.itcast.b_dynamic.UserDao】
        System.out.println(target1.getClass());
        // 给目标对象，创建代理对象
        carDaoInte proxy1 = (carDaoInte) new ProxyFactory(target1).getProxyInstance();
        // 执行方法   【代理对象】
        proxy1.save();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy1.getClass());
    }
}
