package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by delf on 19.03.15.
 * Class for all predicate calculate
 */
public class AllPredicate<T> extends TaskWithPredicateExecuter<T> {
    /**
     * constructor, set args
     *
     * @param data      data for job
     * @param predicate predicate for job
     */
    protected AllPredicate(List<T> data, Predicate<T> predicate) {
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
            if (!result) {
                return false;
            }
        }
        return true;
    }


    @Override
    /**
     * run job
     */
    public void run() {
        for (T element : data) {
            if (!predicate.test(element)) {
                result = false;
                return;
            }
        }
        result = true;
    }
}
