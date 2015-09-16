package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by delf on 19.03.15.
 */
public class Maximum<R> extends TaskWithComparatorExecutor<R, R> {
    protected Maximum(List<R> data, Comparator<R> cmp) {
        super(data, cmp);
    }

    @Override
    R returnResult() {
        return result;
    }

    @Override
    R unionResults(List<R> results) {
        return Collections.max(results,cmp);
    }


    @Override
    /**
     * run job
     */
    public void run() {
        result = Collections.max(data,cmp);
    }
}
