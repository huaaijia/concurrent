package com.github.concurrent;

import jdk.nashorn.internal.ir.CallNode;
import sun.nio.cs.ext.MacThai;

import java.util.concurrent.*;

/**
 * Created by huaaijia on 2017/4/19.
 *
 * 起10个线程，要求这10个线程做完各自的任务后，最后有一个线程来汇总结果
 */
public class TestCountDownLatch2 {
    public static void main(String[] args) throws InterruptedException{

        ExecutorService exec = Executors.newFixedThreadPool(10);
        final CompletionService comp = new ExecutorCompletionService(exec);

        final CountDownLatch countDownLatch = new CountDownLatch(9);

        for(int i=0; i<9; i++){
            final int no = (i+1);
            Callable call = new Callable() {
                public String call() {
                    try {
                        Thread.sleep((long)((Math.random()*10000)));
                        System.out.println("no "+no+" countdown.");
                        countDownLatch.countDown();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return "no "+no+" finished.";
                }
            };
            comp.submit(call);
        }

        Runnable run = new Runnable() {
            public void run() {
                try{
                    countDownLatch.await();
                    System.out.println("10th thread:");
                    for(int i=0; i<9; i++){
                        Future<String> result = comp.take();
                        System.out.println(result.get());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        exec.submit(run);

        exec.shutdown();

    }
}
