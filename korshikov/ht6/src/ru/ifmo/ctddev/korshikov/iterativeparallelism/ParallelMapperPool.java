package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by delf on 02.04.15.
 */
public class ParallelMapperPool {
    private final Queue<Runnable> runQueue = new LinkedList<>();
    private List<Thread> threads = new ArrayList<>();

    /**
     * constructor for poll for work
     * @param threadsN number of thread
     */
    public ParallelMapperPool(final int threadsN) {
        for (int i = 0; i < threadsN; i++) {
            Thread curr = new ParallelMapperThread(runQueue);
            curr.start();
            threads.add(curr);
        }

    }

    /**
     * add new job to poll
     * @param newJob job to add
     */
    public void addJob(Runnable newJob) {
        synchronized (runQueue) {
            runQueue.add(newJob);
        }

    }

    /**
     * clear pool
     */
    public void clear() {
        synchronized (runQueue) {
            runQueue.clear();
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }
    }


}
