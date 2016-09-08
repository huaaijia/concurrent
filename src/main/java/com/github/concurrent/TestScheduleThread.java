package com.github.concurrent;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by huaaijia on 2014/8/21.
 *
 * 02
 */
public class TestScheduleThread {
//    public static void main(String[] args) {
//        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//        final Runnable beeper = new Runnable() {
//            int count = 0;
//            public void run() {
//
//                System.out.println(new Date() + " beep " + (++count));
//            }
//        };
//        // 1秒钟后运行，并每隔2秒运行一次
//        final ScheduledFuture beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 2, SECONDS);
//        // 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行
//        final ScheduledFuture beeperHandle2 = scheduler.scheduleWithFixedDelay(beeper, 2, 5, SECONDS);
//        // 30秒后结束关闭任务，并且关闭Scheduler
//        scheduler.schedule(new Runnable() {
//            public void run() {
//                beeperHandle.cancel(true);
//                beeperHandle2.cancel(true);
//                scheduler.shutdown();
//            }
//        }, 30, SECONDS);
//    }

    public static void main(String[] args) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        final Runnable beeper = new Runnable() {
            int count = 0;
            public void run() {
                try{
                    Thread.sleep(1000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(new Date() + " beep " + (++count));
            }
        };
        final Runnable beeper2 = new Runnable() {
            int count = 0;
            public void run() {
                try{
                    Thread.sleep(1000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(new Date() + " beep2 " + (++count));
            }
        };
        // 1秒钟后运行，并每隔2秒运行一次
        final ScheduledFuture beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 2, SECONDS);
        // 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行(考虑当前runner的执行时间,执行完在计算5秒)
        final ScheduledFuture beeperHandle2 = scheduler.scheduleWithFixedDelay(beeper2, 2, 5, SECONDS);
        // 30秒后结束关闭任务，并且关闭Scheduler
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
                beeperHandle2.cancel(true);
                scheduler.shutdown();
            }
        }, 30, SECONDS);
    }
}
