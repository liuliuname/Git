package com.example.seckill.seckill.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class BuyQueue<T>{

    private LinkedBlockingQueue<T> queue ;

    /**
     * 构造购买队列
     *
     * @param residue 初始化队列大小
     */
    public BuyQueue(int residue) {
        queue = new LinkedBlockingQueue<T>(residue);
    }

    /**
     * 获取队列大小
     * @return
     */
    public int size() {
        return queue.size();
    }

    /**
     * 弹出队列
     * @return 返回队列内容
     * @throws InterruptedException
     */
    public T take() throws InterruptedException {
        return queue.take();
    }

    /**
     * 队列可用容量
     * @return 返回队列容量值
     */
    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    /**
     * 添加队列
     * @param buyrequest 添加队列对象
     * @throws InterruptedException
     */
    public void put(T buyrequest) throws InterruptedException {
        queue.put(buyrequest);
    }
}
