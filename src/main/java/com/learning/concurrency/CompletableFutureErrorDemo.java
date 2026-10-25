
package com.learning.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureErrorDemo {

    public static void main(String[] args) {

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(2)) {

            CompletableFuture<String> inventoryFuture =
                    CompletableFuture.supplyAsync(() -> {
                        throw new RuntimeException(
                                "Inventory system unavailable");
                    }, executor);

            CompletableFuture<String> handledFuture =
                    inventoryFuture.exceptionally(exception -> {
                        System.out.println(
                                "Inventory check failed: "
                                        + exception.getMessage());

                        return "INVENTORY_CHECK_FAILED";
                    });

            System.out.println(handledFuture.join());
        }
    }
}
