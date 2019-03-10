package com.example.springboot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建动态代理对象
 * 动态代理不需要实现接口,但是需要指定接口类型
 *
 * 1.代理对象,不需要实现接口
 * 2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 * 3.动态代理也叫做:JDK代理,接口代理
 *
 */
public class ProxyFactory {
    //维护一个目标对象
    private Object target;
    public ProxyFactory(Object target){
        this.target=target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                //:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                target.getClass().getClassLoader(),
                //目标对象实现的接口的类型,使用泛型方式确认类型
                target.getClass().getInterfaces(),
                //事件处理,执行目标对象的方法时,会触发事件处理器的方法,会把当前执行目标对象的方法作为参数传入
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务2");
                        //运用反射执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("提交事务2");
                        return returnValue;
                    }
                }
        );
    }
}
