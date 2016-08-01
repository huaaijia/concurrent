package com.github.concurrent;

import java.util.concurrent.*;

/**
 * Created by huaaijia on 2014/8/21.
 *
 * 06
 */
public class TestFutureTask {
    public static void main(String[] args)throws InterruptedException,ExecutionException {

        final ExecutorService exec = Executors.newFixedThreadPool(5);
        Callable call1 = new Callable() {
            public String call() throws Exception {
                for(int i=0; i<5; i++){
                    System.out.println("task1: deal "+(i+1));
                    Thread.sleep(1000);
                }
                System.out.println("less important but longtime things.1");
                return "true";
            }
        };
        Callable call2 = new Callable() {
            public String call() throws Exception {
                for(int i=0; i<4; i++){
                    System.out.println("task2: deal "+(i+1));
                    Thread.sleep(1000);
                }
                System.out.println("less important but longtime things.2");
                return "true";
            }
        };
        Future task_1 = exec.submit(call1);
        Future task_2 = exec.submit(call2);
//        exec.shutdown();
        // 重要的事情
        for(int i=0; i<3; i++){
            System.out.println("important : deal "+(i+1));
            Thread.sleep(1000);
        }
        System.out.println("finish important things.");
        // 其他不重要的事情
        String result_1 = (String)task_1.get();//阻塞的
        String result_2 = (String)task_2.get();//阻塞的
        System.out.println("result_1 is :"+result_1);
        System.out.println("result_2 is :"+result_2);
        // 关闭线程池,放在上面注释掉的位置也是一样的效果
        exec.shutdown();
    }
}
