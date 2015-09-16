package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by delf on 19.03.15.
 * class for any predicate calculate
 */
public class AnyPredicate<T> extends TaskWithPredicateExecuter<T> {
    /**
     * constructor, set args
     *
     * @param data      data for job
     * @param predicate predicate for job
     */
    protected AnyPredicate(List<T> data, Predicate<T> predicate) {
        super(data, predicate);
    }

    @Override
    /**
     * return result
     */
    Boolean returnResult() {
        return result;
    }

    @Override
    /**
     * union result from list
     */
    Boolean unionResults(List<Boolean> results) {
        for (Boolean result : results) {
            if (result) {
                return true;
            }
        }
        return false;
    }


    @Override
    /**
     * run job
     */
    public void run() {
        for (T element : data) {
            if (predicate.test(element)) {
                result = true;
                return;
            }
        }
        result = false;
    }
}
