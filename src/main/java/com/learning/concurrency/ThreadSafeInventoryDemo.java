
package com.learning.concurrency;

public class ThreadSafeInventoryDemo {

    private static int stock = 100;

    private static synchronized void reserveOneItem() {
        if (stock > 0) {
            stock--;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Thread worker1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                reserveOneItem();
            }
        }, "Customer-A");

        Thread worker2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                reserveOneItem();
            }
        }, "Customer-B");

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        System.out.println("Final stock: " + stock);
    }
}
