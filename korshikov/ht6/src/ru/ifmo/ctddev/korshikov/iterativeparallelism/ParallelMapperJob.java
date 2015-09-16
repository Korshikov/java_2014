package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.List;
import java.util.function.Function;

/**
 * Created by delf on 02.04.15.
 *
 *
 * one job for parallel mapper
 */
public class ParallelMapperJob<R, T> implements Runnable {
    /**
     * feeld for function for job
     */
    private Function<? super T, ? extends R> function;
    private T element;
    private List<R> outList;
    private int index;
    private final ParallelMapperCounter counter;


    /**
     * create new job
     * @param function job function
     * @param element job element
     * @param outList fob out list
     * @param index job index
     * @param counter job counter
     */
    public ParallelMapperJob(Function<? super T, ? extends R> function, T element, List<R> outList, int index, final ParallelMapperCounter counter) {
        this.function = function;
        this.element = element;
        this.outList = outList;
        this.index = index;
        this.counter = counter;
    }


    @Override
    public void run() {
        outList.set(index, function.apply(element));
        counter.synchronizedMethodIncCounter();
    }
}
