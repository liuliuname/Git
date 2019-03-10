package com.example.thred.lock;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 *
 * 你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保持它的完整性，你会怎样去实现它？
 *
 * 用ReadWriteLock读写锁来实现一个高效的Map缓存
 */
public class ReaderAndWriterExaple {


    //基于ReadWriteLock 子类ReentrantReadWriteLock实现
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //根据ReentrantReadWriteLock 获取 WriteLock  ReadLock
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();
    //根据写锁读锁 操作相应逻辑 即可实现


    private Map<String,String> map = new HashMap<String, String>();

    /*public void put(String key,String value){
        try {
            writeLock.lock();
            map.put(key,value);
            System.out.println("put方法 key = " + key + "value:" + value);
        }finally {
            writeLock.unlock();
        }
    }

    public String get(String key){
        try {
            readLock.lock();
            String value = map.get(key);
            System.out.println("get方法 value:" + value);
            return value;
        }finally {
            readLock.unlock();
        }
    }*/

    public void put(String key,String value){
        while (true){
            if(writeLock.tryLock()){
                 try {
                     map.put(key,value);
                     System.out.println("put方法 key = " + key + "value:" + value);
                }finally {
                     writeLock.unlock();
                     break;
                }
            }
        }
    }

    public String get(String key){
        while (true){
            if(readLock.tryLock()){
                try {
                    String value = map.get(key);
                    System.out.println("get方法 value:" + value);
                    return value;
                }finally {
                    readLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ReaderAndWriterExaple rw = new ReaderAndWriterExaple();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            exec.execute(new RWThred(rw));
        }
        exec.shutdown();
    }

    static class RWThred implements Runnable{
        final String KEY = "key";
        ReaderAndWriterExaple map;
        public RWThred(ReaderAndWriterExaple map){
            this.map = map;
        }

        @Override
        public void run() {
            Random random = new Random();
            int r = random.nextInt(100);
            //生成随机数，小于30的写入缓存，大于等于30则读取数字
            if (r < 30){
                map.put(KEY, r+"");
            } else {
                map.get(KEY);
            }
        }

    }
}
