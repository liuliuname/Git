package com.example.seckill.seckill.util;

import org.springframework.core.task.TaskExecutor;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class ThreadPoolUtil {

    public static ThreadPoolExecutor pool;

    static{
        pool = new ThreadPoolExecutor(100,200,10,
                TimeUnit.HOURS,new ArrayBlockingQueue<Runnable>(100));
    }

    public static ThreadPoolExecutor getInstance(){
        pool = new ThreadPoolExecutor(100,200,10,
                TimeUnit.HOURS,new ArrayBlockingQueue<Runnable>(100));
        return pool;
    }

}
