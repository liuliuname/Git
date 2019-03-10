package com.example.singl;

import java.io.Serializable;

/**
 * 使用静态内部类 实现单例模式
 *
 *
 */
public class InteriorSingleExpale implements Serializable {

    private InteriorSingleExpale(){}


    private static class InteriorSingleClassInstace{
        private static InteriorSingleExpale interiorSingleExpale = new InteriorSingleExpale();
    }

    public static InteriorSingleExpale getInstance(){
        return InteriorSingleClassInstace.interiorSingleExpale;
    }
}
