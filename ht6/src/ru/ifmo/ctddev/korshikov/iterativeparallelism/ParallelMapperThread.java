package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.Queue;

/**
 * Created by delf on 02.04.15.
 *
 * parallel mapper thread class
 */
public class ParallelMapperThread extends Thread {
    private final Queue<Runnable> runQueue;

    public ParallelMapperThread(Queue<Runnable> runQueue) {
        this.runQueue = runQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Runnable job;
            synchronized (runQueue) {
                job = runQueue.poll();
            }
            if (job != null) {
                job.run();
            }
        }
    }
}
