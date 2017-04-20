package com.github.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huaaijia on 2017/4/20.
 *
 * 起三个线程A，B，C，线程A打印输出A，线程B打印输出B，线程C打印输出C，要求：三个线程轮流打印，输出形势为ABCABCABCABC
 */
public class TestReenTrainLock {

    static ReentrantLock lock = new ReentrantLock();
    static Condition accessA = lock.newCondition();
    static Condition accessB = lock.newCondition();
    static Condition accessC = lock.newCondition();

    static String sign = "A";

    private static class A implements Runnable{
        public void run() {
            while(true){
                try{
                    lock.lock();
                    while (!sign.equals("A")){
                        accessA.await();
                    }
                    System.out.println("A");
                    sign = "B";
                    accessB.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    private static class B implements Runnable{
        public void run() {
           while(true){
                try{
                    lock.lock();
                    while (!sign.equals("B")){
                        accessB.await();
                    }
                    System.out.println("B");
                    sign = "C";
                    accessC.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class C implements Runnable{
        public void run() {
            while(true){
                try{
                    lock.lock();
                    while (!sign.equals("C")){
                        accessC.await();
                    }
                    System.out.println("C");
                    sign = "A";
                    accessA.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newFixedThreadPool(3);
        exec.execute(new A());
        exec.execute(new B());
        exec.execute(new C());



    }
}
