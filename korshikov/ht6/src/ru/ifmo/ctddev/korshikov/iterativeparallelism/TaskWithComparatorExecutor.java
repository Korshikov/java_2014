package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.Comparator;
import java.util.List;

/**
 * Created by delf on 19.03.15.
 *
 * @See ru.ifmo.ctddev.korshikov.iterativeparallelism.TaskExecuter
 * Abstract base class for jobs with comparator
 */
abstract public class TaskWithComparatorExecutor<R, T> extends TaskExecuter<R, T> {
    /**
     * Comparator for job
     */
    protected final Comparator<T> cmp;

    /**
     * constructor eit data for work and comparator
     *
     * @param data job data list
     * @param cmp  job comparator
     */
    protected TaskWithComparatorExecutor(List<T> data, Comparator<T> cmp) {
        super(data);
        this.cmp = cmp;
    }

    /**
     * field with result of job
     */
    protected R result;

    /**
     * method witch return result from protected field
     *
     * @return
     */
    abstract R returnResult();

    /**
     * field with contain constructor classes args
     */
    public static final Class[] constructorArgs = new Class[]{List.class, Comparator.class};
}
