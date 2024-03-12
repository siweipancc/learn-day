package com.pancc.learn.jdks.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Siweipancc
 */
public class VirtualThreads {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        int count = 200;
        Random random = new Random();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < count; i++) {
                final int id = i;
                executorService.submit(() -> {
                    try {
                        int nexted = random.nextInt(1, 3);
                        TimeUnit.SECONDS.sleep(nexted);
                        System.out.printf("Thread[%03d] invoke after %d seconds%n", id, nexted);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        System.out.printf("%d threads used time %d(ms)", count, (System.currentTimeMillis() - start));
    }

}
