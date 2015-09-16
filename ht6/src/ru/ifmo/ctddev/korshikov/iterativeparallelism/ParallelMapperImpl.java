package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
     * @see java.util.concurrent.CountDownLatch
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

        CountDownLatch latch = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            pool.addJob(new ParallelMapperJob<>(function, list.get(i), result, i, latch));
        }

        latch.await();

        return result;
    }

    @Override
    public void close() throws InterruptedException {
        pool.clear();
    }


}
