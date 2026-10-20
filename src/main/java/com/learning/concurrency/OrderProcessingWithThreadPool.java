
package com.learning.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessingWithThreadPool {

    public static void main(String[] args) {

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(3)) {

            for (int orderId = 101; orderId <= 110; orderId++) {
                int currentOrderId = orderId;

                executor.submit(
                        () -> processOrder(currentOrderId)
                );
            }

            System.out.println("All orders submitted.");
        }

        System.out.println("All order tasks completed.");
    }

    private static void processOrder(int orderId) {
        String threadName =
                Thread.currentThread().getName();

        System.out.println(
                "Started order " + orderId
                        + " on " + threadName);

        try {
            // Simulate a slow operation, such as processing work.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            System.out.println(
                    "Order " + orderId + " was interrupted.");
            return;
        }

        System.out.println(
                "Completed order " + orderId
                        + " on " + threadName);
    }
}
