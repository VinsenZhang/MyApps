package com.tag.man.mylibrary.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程统一管理，避免乱开线程，造成浪费
 * Created by Vinsen on 17/3/8.
 */

public class ThreadManager {


    private static ThreadPoolExecutor executor;

    private static Handler handler;

    static {
        if (executor == null) {
            executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1, 2 * Runtime.getRuntime().availableProcessors() + 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        }

        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
    }


    // 后台执行一个任务
    public static void doInBackGround(Runnable runnable) {
        executor.execute(runnable);
    }


    public static void doInMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static void postDelayInMainThread(Runnable runnable, long time) {
        handler.postDelayed(runnable, time);
    }

    public static void removeMainHanlderRunnable(Runnable runnable){
        handler.removeCallbacks(runnable);
    }


    // 判断线程池内是否还有线程在工作
    public static boolean isAllFinish() {
        return executor.getActiveCount() == 0;
    }


    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
