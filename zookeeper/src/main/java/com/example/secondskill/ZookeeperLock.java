package com.example.secondskill;

import com.example.curator.CuratorLockDemo;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ZookeeperLock {

    public static Logger log = LoggerFactory.getLogger(ZookeeperLock.class);
    private InterProcessMutex interProcessMutex;  //可重入排它锁
    private String lockName;  //竞争资源标志
    private static String root = "/curator/lock/";//根节点
    private boolean canUsed = false; //表识zookeeper服务是否可用
    private static CuratorFramework curatorFramework;
    private static String ZK_URL = "10.1.102.163:2181";
    static{
        curatorFramework= CuratorFrameworkFactory.
                newClient(ZK_URL,new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
    }


    private static CuratorFramework getClient() {

        //第一个参数 等待时间 第二个参数 重试次数
        //RetryPolicy policy = new ExponentialBackoffRetry(2000, 10);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZK_URL,
                new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }


    /**
     * 实例化
     * @param lockName
     */
    public ZookeeperLock(String lockName){
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
    public void acquireLock() throws Exception {
       interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
       interProcessMutex.acquire();
    }

    /**
     * 释放锁
     */
    public void releaseLock() throws Exception {
        interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
        if(interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()){
            interProcessMutex.release();
            curatorFramework.delete().inBackground().forPath(root+lockName);
        }
    }

    public void close(){
        curatorFramework.close();
    }


}
