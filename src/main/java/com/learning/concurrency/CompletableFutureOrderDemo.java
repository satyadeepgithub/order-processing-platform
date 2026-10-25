
package com.learning.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureOrderDemo {

    public static void main(String[] args) {

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(2)) {

            CompletableFuture<Boolean> inventoryFuture =
                    CompletableFuture.supplyAsync(() -> {
                        System.out.println(
                                "Checking inventory on "
                                        + Thread.currentThread().getName());

                        return true;
                    }, executor);

            CompletableFuture<Double> shippingFuture =
                    CompletableFuture.supplyAsync(() -> {
                        System.out.println(
                                "Calculating shipping on "
                                        + Thread.currentThread().getName());

                        return 75.0;
                    }, executor);

            CompletableFuture<String> orderResult =
                    inventoryFuture.thenCombine(
                            shippingFuture,
                            (inStock, shippingCost) -> {
                                if (inStock) {
                                    return "Order can proceed. Shipping: "
                                            + shippingCost;
                                }

                                return "Order rejected: out of stock";
                            });

            System.out.println(orderResult.join());
        }
    }
}
