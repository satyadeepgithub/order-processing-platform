
package com.learning.concurrency;

import java.util.concurrent.CountDownLatch;

public class RaceConditionDemo {

    private static int stock = 100;

    private static final CountDownLatch bothRead =
            new CountDownLatch(2);

    private static final CountDownLatch allowUpdate =
            new CountDownLatch(1);

    public static void main(String[] args)
            throws InterruptedException {

        Thread worker1 = new Thread(
                RaceConditionDemo::reserveOneItem,
                "Customer-A");

        Thread worker2 = new Thread(
                RaceConditionDemo::reserveOneItem,
                "Customer-B");

        worker1.start();
        worker2.start();

        // Wait until both threads have read stock.
        bothRead.await();

        // Allow both threads to write their results.
        allowUpdate.countDown();

        worker1.join();
        worker2.join();

        System.out.println("Expected stock: 98");
        System.out.println("Actual stock: " + stock);
    }

    private static void reserveOneItem() {
        // Both threads read the same shared value.
        int currentStock = stock;

        System.out.println(
                Thread.currentThread().getName()
                        + " read stock = " + currentStock);

        bothRead.countDown();

        try {
            allowUpdate.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // Both calculate and write based on the old value.
        stock = currentStock - 1;

        System.out.println(
                Thread.currentThread().getName()
                        + " wrote stock = " + stock);
    }
}
