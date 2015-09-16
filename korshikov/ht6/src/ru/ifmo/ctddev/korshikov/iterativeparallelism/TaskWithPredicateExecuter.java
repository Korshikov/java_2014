package ru.ifmo.ctddev.korshikov.iterativeparallelism;


import java.util.List;
import java.util.function.Predicate;

/**
 * Created by delf on 19.03.15.
 */
abstract public class TaskWithPredicateExecuter<T> extends TaskExecuter<Boolean, T> {
    /**
     * Predicate for job
     */
    final protected Predicate<T> predicate;

    /**
     * constructor eit data for work and comparator
     *
     * @param data      job data list
     * @param predicate job predicate
     */
    protected TaskWithPredicateExecuter(List<T> data, Predicate<T> predicate) {
        super(data);
        this.predicate = predicate;
    }

    /**
     * field with result of job
     */
    protected Boolean result;

    /**
     * method witch return result from protected field
     *
     * @return
     */
    abstract Boolean returnResult();

    /**
     * field with contain constructor classes args
     */
    public static final Class[] constructorArgs = new Class[]{List.class, Predicate.class};
}
