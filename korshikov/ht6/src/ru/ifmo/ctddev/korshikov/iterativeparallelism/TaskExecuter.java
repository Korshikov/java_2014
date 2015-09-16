package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.List;

/**
 * Created by delf on 19.03.15.
 * abstract class for all jobs
 */
abstract public class TaskExecuter<R, T> implements Runnable {
    /**
     * constructor set list data - input values
     *
     * @param data - list input values
     */
    protected TaskExecuter(List<T> data) {
        this.data = data;
    }

    /**
     * function for return result of job
     *
     * @return job's result
     */
    abstract R returnResult();

    /**
     * union results from list with results
     *
     * @param results
     * @return
     */
    abstract R unionResults(List<R> results);

    /**
     * list with data for work
     */
    protected final List<T> data;
}
