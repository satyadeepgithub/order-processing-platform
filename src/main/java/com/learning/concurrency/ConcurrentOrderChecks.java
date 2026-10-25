
package com.learning.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentOrderChecks {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(2)) {

            Callable<Boolean> inventoryTask = () -> {
                System.out.println("Checking inventory...");
                Thread.sleep(2000);
                return true;
            };

            Callable<Double> shippingTask = () -> {
                System.out.println("Calculating shipping...");
                Thread.sleep(2000);
                return 75.0;
            };

            Future<Boolean> inventoryFuture =
                    executor.submit(inventoryTask);

            Future<Double> shippingFuture =
                    executor.submit(shippingTask);

            // Both tasks have been submitted before we wait.
            boolean inStock = inventoryFuture.get();
            double shippingCost = shippingFuture.get();

            System.out.println("In stock: " + inStock);
            System.out.println("Shipping cost: " + shippingCost);
        }

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time: " + elapsed + " ms");
    }
}
