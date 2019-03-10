package com.example.seckill.seckill.queue;

import com.example.seckill.seckill.bean.BuyOrders;
import com.example.seckill.seckill.bean.BuyRequest;
import com.example.seckill.seckill.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DealQueueThread implements Runnable {
    private static DealQueueThread dealQueueThread;

    final static JedisPool pool = RedisUtil.getPool();
//
//    @Autowired
//    BuyGoodService buyGoodService;
//    @Autowired
//    BuyOrdersService buyOrdersService;
//    @Autowired
//    JedisPool jedisPool;

    private Jedis jedis;
    private BuyQueue<BuyRequest> buyqueue;

    //线程的默认执行标志为未执行,即空闲状
    public static boolean excute = false;
    public DealQueueThread() {}

    public DealQueueThread(BuyQueue<BuyRequest> buyqueue) {
        this.buyqueue = buyqueue;
        jedis = pool.getResource();
    }

    @PostConstruct
    public void init() {
        dealQueueThread = this;
//        dealQueueThread.buyGoodService = this.buyGoodService;
//        dealQueueThread.buyOrdersService = this.buyOrdersService;
        //dealQueueThread.jedisPool = this.jedisPool;
    }

    @Override
    public void run() {
        try {
            excute = true;//修改线程的默认执行标志为执行状态
            // 开始处理请求队列中的请求,按照队列的FIFO的规则,先处理先放入到队列中的请求
            while (buyqueue != null && buyqueue.size() > 0) {
                BuyRequest buyreq = buyqueue.take();//取出队列中的请求
                dealWithQueue(buyreq);//处理请求
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            excute = false;
        }
    }

    public void dealWithQueue(BuyRequest buyreq) {
        try {
            //为了尽量确保数据的一致性,处理之前先从redis中获取当前抢购商品的剩余数量
            int residue = Integer.valueOf(jedis.get("residue" + buyreq.getGood_id()));
            if (residue < 1) {
                //如果没有剩余商品,就直接返回
                buyreq.setResponse_status(3);
                System.out.println(Thread.currentThread()+"扣减库存不足");
                return;
            }
            //如果有剩余商品,先在redis中将剩余数量减一,再开始下订单
            jedis.decr("residue" + buyreq.getGood_id());
            //将数据库中将剩余数量减一,这一步处理可以在队列处理完成之后一次性更新剩余数量
            //dealQueueThread.buyGoodService.minusResidue(buyreq.getGood_id());

            //deductingInventory(buyreq.getGood_id());

            //处理请求,下订单
            BuyOrders bo = new BuyOrders();
            bo.setGood_id(buyreq.getGood_id());
            bo.setUser_id(buyreq.getUser_id());
           // int order_id = dealQueueThread.buyOrdersService.insert(bo);
           // BuyOrders orders = dealQueueThread.buyOrdersService.getById(order_id);
            buyreq.setOrder_id(1);//订单id
            buyreq.setBuyOrders(new BuyOrders());//订单信息
            buyreq.setResponse_status(1);//处理完成状态
        } catch (Exception e) {
            buyreq.setResponse_status(2);//异常状态
            e.printStackTrace();
        }
    }

    /**
     * 根据商品编号扣减库存
     * @param good_id
     * @return
     */
    private void deductingInventory(int good_id) {
        Jedis jedis = pool.getResource();
        int residue = Integer.valueOf(jedis.get("residue" + good_id));
        jedis.set("residue" + good_id, (residue-1)+"");
        System.out.println("线程"+Thread.currentThread()+"操作商品 " + good_id +"-->原库存为"+residue +"),扣减后为=" + (residue-1));
    }
}
