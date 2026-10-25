
package com.learning.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureDemo {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(2)) {

            Callable<Integer> inventoryTask = () -> {
                System.out.println(
                        "Checking inventory on "
                                + Thread.currentThread().getName());

                Thread.sleep(1000);

                return 25;
            };

            Future<Integer> future =
                    executor.submit(inventoryTask);

            System.out.println("Inventory task submitted.");

            Integer availableStock = future.get();

            System.out.println(
                    "Available stock: " + availableStock);
        }
    }
}
