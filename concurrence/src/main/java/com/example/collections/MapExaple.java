package com.example.collections;


import java.util.*;

/**
 * Map 实现类 关系
 */
public class MapExaple {

    public static void main(String[] args) {
        Map map = new LinkedHashMap();
        new TreeMap();
        new WeakHashMap();
        new HashMap();
        new IdentityHashMap();
        //AbstractMap(); 抽象类 不能被实例化  hashmap(linkedHashMap)  treemap weakHashmap identityhashmap
        // 除了treemap实现navagleMap接口 剩余都实现了Map接口
        }


}
