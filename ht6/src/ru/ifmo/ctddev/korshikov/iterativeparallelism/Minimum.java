package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by delf on 19.03.15.
 */
public class Minimum<R> extends TaskWithComparatorExecutor<R, R> {

    protected Minimum(List<R> data, Comparator<R> cmp) {
        super(data, cmp);
    }


    @Override
    R returnResult() {
        return result;
    }


    @Override
    /**
     * run job
     */
    public void run() {
        result = Collections.min(data,cmp);
    }

    @Override
    R  unionResults(List<R> results) {
        return  Collections.min(results, cmp);
    }
}
