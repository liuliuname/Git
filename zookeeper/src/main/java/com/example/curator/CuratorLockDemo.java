package com.example.curator;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 基于curator实现分布式锁
 */
public class CuratorLockDemo {

    public static Logger log = LoggerFactory.getLogger(CuratorLockDemo.class);
    private InterProcessMutex interProcessMutex;  //可重入排它锁
    private String lockName;  //竞争资源标志
    private static String root = "/curator/lock/";//根节点
    private boolean canUsed = false; //表识zookeeper服务是否可用
    private static CuratorFramework curatorFramework;
    private static String ZK_URL = "10.1.102.163:2181";
    static{
        curatorFramework= CuratorFrameworkFactory.newClient(ZK_URL,new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
    }


    /**
     * 实例化
     * @param lockName
     */
    public CuratorLockDemo(String lockName){
        try {
            this.lockName = lockName;
            interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
            canUsed = true;
        }catch (Exception e){
            log.error("initial InterProcessMutex exception="+e);
        }
    }


    /**
     * 获取锁
     */
    public void acquireLock(){
        int flag = 0;
        try {
            while (!interProcessMutex.acquire(2, TimeUnit.SECONDS)){
                flag++;
                if(flag>1){  //重试两次
                    break;
                }
            }
        } catch (Exception e) {
            log.error("distributed lock acquire exception="+e);
        }
        if(flag>1){
            log.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  busy");
        }else{
            log.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  success");
        }
    }


    /**
     * 释放锁
     */
    public void releaseLock(){
        try {
            if(interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()){
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(root+lockName);
                log.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  success");
            }
        }catch (Exception e){
            log.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  exception="+e);
        }
    }

    /**
     * 判断是否zookeeper服务可用
     * @return
     */
    public boolean isCanUsed(){
        return this.canUsed;
    }


    public static void main(String[] args) {
        final CuratorLockDemo curatorLock = new CuratorLockDemo("orderlock");
        final CountDownLatch down = new CountDownLatch(1);
        for(int i = 0; i < 1000; i ++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //down.await();
                        curatorLock.acquireLock();
                        // lock.acquire();
                    }catch(Exception e) {
                    }
                   // synchronized(this){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                        String orderNo = sdf.format(new Date());
                        System.out.println("生成的订单号是：" + orderNo);
                    //}
                    try {
                        curatorLock.releaseLock();
                       // lock.release();
                    } catch(Exception e){
                    }
                }
            }).start();
        }
        down.countDown();

        //testReadWriterLock();
    }



    private static void testReadWriterLock(){
        //创建多线程 循环进行锁的操作
        CuratorFramework client = getClient();
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, root+"readwriter");
        final  InterProcessMutex readLock = readWriteLock.readLock();
        final  InterProcessMutex writeLock = readWriteLock.writeLock();
        List<Thread> jobs = new ArrayList<Thread>();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread("写锁  " + i){
                public void run(){
                    readWriterLock(writeLock);
                }
            };
            jobs.add(t);
        }
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread("读锁  " + i){
                public void run(){
                    readWriterLock(readLock);
                }
            };
            jobs.add(t);
        }
        for (Thread thread : jobs) {
            thread.start();
        }
    }

    private static CuratorFramework getClient() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZK_URL,
                new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }

    private static void readWriterLock(InterProcessLock lock){
        System.out.println(Thread.currentThread().getName()+"   进入任务 " + System.currentTimeMillis());
        try {
            if(lock.acquire(2, TimeUnit.MILLISECONDS)){
                //System.err.println(Thread.currentThread().getName() + " 等待超时  无法获取到锁");
                //执行任务 --读取 或者写入
                System.out.println(Thread.currentThread().getName()+"   执行任务开始");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+"   执行任务完毕");
            }else{
                System.err.println(Thread.currentThread().getName() + " 等待超时  无法获取到锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(lock.isAcquiredInThisProcess())
                    lock.release();
            } catch (Exception e2) {

            }
        }
    }

}
