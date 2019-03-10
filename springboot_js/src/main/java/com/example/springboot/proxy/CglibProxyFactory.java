package com.example.springboot.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory{
    //维护目标对象
    private Object target;

    public CglibProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method,
                                    Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("统一处理内容");
                Object value = method.invoke(target, args);
                System.out.println("统一内容结束");
                return value;
            }
        });
        return en.create();
    }

}
