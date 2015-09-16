package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Performs jobs in several threads.
 *
 * Created by delf on 02.04.15.
 */
public class ParallelMapperImpl implements ParallelMapper {
    /**
     * pull of jobs
     */
    ParallelMapperPool pool;

    /**
     * Init Mapper with number of thread
     *
     * @param threads number of thread with will use mapper
     */
    public ParallelMapperImpl(final int threads) {
        pool = new ParallelMapperPool(threads);
    }

    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> function, List<? extends T> list) throws InterruptedException {
        List<R> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            result.add(null);
        }

        final ParallelMapperCounter count = new ParallelMapperCounter();
        for (int i = 0; i < list.size(); i++) {
            pool.addJob(new ParallelMapperJob<>(function, list.get(i), result, i, count));
        }

        while (true) {
            synchronized (count) {
                if (count.counter == list.size()) {
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public void close() throws InterruptedException {
        pool.clear();
    }


}
