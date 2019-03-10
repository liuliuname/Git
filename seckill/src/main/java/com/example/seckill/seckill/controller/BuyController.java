package com.example.seckill.seckill.controller;

import com.example.seckill.seckill.bean.BuyRequest;
import com.example.seckill.seckill.queue.BuyQueue;
import com.example.seckill.seckill.queue.DealQueueThread;
import com.example.seckill.seckill.util.RedisUtil;
import com.example.seckill.seckill.util.ThreadPoolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/buy")
public class BuyController {
    private static BuyQueue<BuyRequest> buyqueue = null;

    final static JedisPool pool = RedisUtil.getPool();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < 1000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        //addOrders(111+"");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDownLatch.countDown();
    }

    public static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    //线程安全的请求队列
    @RequestMapping("/addOrders.do")
    @ResponseBody
    public Object addOrders(BuyRequest buyrequest) {
//        BuyRequest buyrequest = new BuyRequest();
//        buyrequest.setGood_id(Integer.parseInt(id));
        Map<String, Object> results = new HashMap<String, Object>();
        Jedis jedis = null;
        try {
            jedis =  pool.getResource();
            //下订单之前，先获取商品的剩余数量
            int residue = Integer.valueOf(jedis.get("residue" + buyrequest.getGood_id()));
            if (residue < 1) {
                //如果剩余数量不足，直接响应客户端“卖完了”
                System.out.println(Thread.currentThread()+"商品已抢光");
                results.put("msg", "卖完了");
                results.put("done", false);
                return results;
            }
            //TODO 并发 初始化队列大小是否会有问题   使用线程安全得单例模式  后期优化
            //如果还有剩余商品,就准备将请求放到请求队列中
            if (buyqueue == null) {
                //第一次初始化请求队列,队列的容量为当前的商品剩余数量
                buyqueue = new BuyQueue<BuyRequest>(residue);
            }
            //TODO 当并发量大时  会有超过队列数量
            //当队列的可用容量大于0时,将请求放到请求队列中
            if (buyqueue.remainingCapacity() > 0 && atomicBoolean.get()) {
                buyqueue.put(buyrequest);
            } else {
                atomicBoolean.set(false);
                System.out.println(Thread.currentThread()+"抢购队列已满，请稍候重试。");
                //当请求队列已满,本次请求不能处理,直接响应客户端提示请求队列已满
                results.put("msg", "抢购队列已满，请稍候重试。");
                results.put("done", false);
                return results;
            }
            if (!DealQueueThread.excute) {
                //如果线程类的当前执行标志为未执行,即空闲状态,通过线程池启动线程
                DealQueueThread dealQueue = new DealQueueThread(buyqueue);
                ThreadPoolExecutor pool = ThreadPoolUtil.pool;
                pool.execute(dealQueue);
                System.out.println(Thread.currentThread()+"---下订单成功");
                //请求放入到队列中，即完成下单请求
                results.put("done", true);
                results.put("msg", "下订单成功");
                return  results;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread()+"下单异常");
            results.put("done", false);
            results.put("msg", "下单失败");
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }
}