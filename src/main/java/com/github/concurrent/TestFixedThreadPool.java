package com.github.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huaaijia on 2014/8/21.
 *
 * http://daoger.iteye.com/blog/142485
 * 01
 * 上面是一个简单的例子，使用了2个大小的线程池来处理100个线程。但有一个问题：
 * 在for循环的过程中，会等待线程池有空闲的线程，所以主线程会阻塞的。
 * 为了解决这个问题，一般启动一个线程来做for循环，就是为了避免由于线程池满了造成主线程阻塞。
 * 不过在这里我没有这样处理。
 * [重要修正：经过测试，即使线程池大小小于实际线程数大小，线程池也不会阻塞的，
 * 这与Tomcat的线程池不同，它将Runnable实例放到一个“无限”的BlockingQueue中，
 * 所以就不用一个线程启动for循环，Doug Lea果然厉害]
 *
 * 另外它使用了Executors的静态函数生成一个固定的线程池，
 * 顾名思义，线程池的线程是不会释放的，即使它是Idle。
 * 这就会产生性能问题，比如如果线程池的大小为200，当全部使用完毕后，所有的线程会继续留在池中，相应的内存和线程切换（while(true)+sleep循环）都会增加。如果要避免这个问题，就必须直接使用ThreadPoolExecutor()来构造。可以像Tomcat的线程池一样设置“最大线程数”、“最小线程数”和“空闲线程keepAlive的时间”。通过这些可以基本上替换Tomcat的线程池实现方案。
 *
 * 需要注意的是线程池必须使用shutdown来显式关闭，否则主线程就无法退出。shutdown也不会阻塞主线程。
 *
 */
public class TestFixedThreadPool {

    public static void main(String args[]) throws InterruptedException {
        // only two threads
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for (int index = 0; index < 10; index++) {
            System.out.println("main:index=" + (index + 1));
            Runnable run = new Runnable() {
                public void run() {
                    long time = (long) (Math.random() * 1000);
                    System.out.println(Thread.currentThread().getName() + " Sleeping " + time + " ms");
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                    }
                }
            };
            exec.execute(run);
        }
//        System.out.println("main:before exec.shundown().");
//        // must shutdown,否则主线程无法结束
//        exec.shutdown();
//        System.out.println("main:after exec.shundown().");
    }

}
