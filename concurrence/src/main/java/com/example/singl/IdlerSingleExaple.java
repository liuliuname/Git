package com.example.singl;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 线程安全的 懒汉式
 */
public class IdlerSingleExaple implements Serializable {

    volatile private static IdlerSingleExaple idlerSingleExaple;

    private IdlerSingleExaple(){
    }

    /**
     * 非线程安全
     * @return
     */
    public static IdlerSingleExaple getInstance(){
        if (idlerSingleExaple == null) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            idlerSingleExaple = new IdlerSingleExaple();
        }
        return idlerSingleExaple;
    }

    /**
     * 线程安全  双重检验锁
     * @return
     */
    public static IdlerSingleExaple getInstance1(){
        if (idlerSingleExaple == null) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(IdlerSingleExaple.class){
                if(idlerSingleExaple == null) {//二次检查
                    idlerSingleExaple = new IdlerSingleExaple();
                }
            }
        }
        return idlerSingleExaple;
    }

    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
    protected Object readResolve() throws ObjectStreamException {
        System.out.println("调用了readResolve方法！");
        return IdlerSingleExaple.getInstance1();
    }

}
