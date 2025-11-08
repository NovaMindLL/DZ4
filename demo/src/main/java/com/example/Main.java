package com.example;

public class Main {

    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread locked lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread waiting lock2");
            }
            synchronized (lock2) {
                System.out.println("Thread locked lock2");
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread locked lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread waiting lock1");
            }
            synchronized (lock1) {
                System.out.println("Thred locked lock1");
            }
        });
        thread1.start();
        thread2.start();
    }
}
