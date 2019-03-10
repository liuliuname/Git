package com.example.secondskill;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟高并发秒杀活动
 *
 * 秒杀难点  高并发不能阻塞 系统吞吐量 数据库瓶颈  库存保证超卖现象
 *
 *web服务器层卸载流量 比如 10个请求 打会9个 放行1个
 *
 * 使用zk加锁保证共享资源数据一致性
 *
 *将参加秒杀商品的库存 提前预热存储到redis中
 *
 *如果redis中库存大于用户所购买量则返回用户购买成功系统并发送mq通知系统处理订单与真正的库存
 *
 */
public class SecondKillDemo {

    public static final String SECONDLOCK = "secondLock";
    private static InterProcessMutex interProcessMutex;  //可重入排它锁
    private String lockName;  //竞争资源标志
    private static String root = "/curator/lock/";//根节点
    private static CuratorFramework curatorFramework;
    private static String ZK_URL = "10.1.102.164:2181";

    private static Map<String,Integer> inventoryMap = new HashMap<String, Integer>();
    private static void initInventoryMap(){
        inventoryMap.put("0000011111",10);
    }

    public static InterProcessMutex getInstance(){
        // retryPolicy参数是指在连接ZK服务过程中重新连接测策略.
        // 在它的实现类ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries)中,baseSleepTimeMs参数代表两次连接的等待时间,maxRetries参数表示最大的尝试连接次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        curatorFramework= CuratorFrameworkFactory.newClient(ZK_URL,retryPolicy);
        curatorFramework.start();
        interProcessMutex = new InterProcessMutex(curatorFramework, root + SECONDLOCK);
        return interProcessMutex;
    }

    /**
     * 库存扣减方法
     */
    private static void buySecondKillMethod(String productId,InterProcessMutex zkLock){
        try {
           zkLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //得到锁以后进行库存操作
        //根据商品查询出Redis中库存数量
        Integer Inventory = inventoryMap.get(productId);
        if(Inventory > 0){
            inventoryMap.put(productId,Inventory-1);
            System.out.println("商品库存数量为" + (Inventory-1));
        }else{
            System.out.println("库存已强光 请期待下次活动" );
        }
        try {
            zkLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        final InterProcessMutex zkLock = getInstance();
        initInventoryMap();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final CountDownLatch down = new CountDownLatch(100);
        for(int i = 0; i < 100; i ++) {
            executorService.execute(new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    buySecondKillMethod("0000011111",zkLock);
                }
            }));
            down.countDown();
            System.out.println("aa");
        }
        executorService.shutdown();

    }

}
