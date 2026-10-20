
package com.learning.concurrency;

public class OrderProcessingDemo {

    public static void main(String[] args) {

        Runnable orderTask = () -> {
            String threadName =
                    Thread.currentThread().getName();

            System.out.println(
                    "Processing order on " + threadName);
        };

      /*  Runnable orderTask = new Runnable(){
            @Override
                  public void run(){
                String threadName =
                        Thread.currentThread().getName();

                System.out.println(
                        "Processing order on " + threadName);
            }
        };*/

        Thread worker1 = new Thread(orderTask);
        Thread worker2 = new Thread(orderTask);

        worker1.start();
        worker2.start();

        System.out.println("Main thread finished submitting tasks");
    }
}
