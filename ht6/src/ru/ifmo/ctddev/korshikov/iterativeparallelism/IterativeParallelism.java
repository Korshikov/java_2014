package ru.ifmo.ctddev.korshikov.iterativeparallelism;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by delf on 19.03.15.
 */
public class IterativeParallelism implements ScalarIP {
    private ParallelMapper mapper;

    /**
     * constructor Iterative
     *
     * @param mapper ParallelMapper witch will be use to calculate jobs
     */
    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * main function for test run
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        IterativeParallelism ip = new IterativeParallelism(new ParallelMapperImpl(10));
        List<Integer> data = new ArrayList<Integer>();
    }

    /**
     * split List to parts part
     *
     * @param parts count of result part
     * @param job   List with all data
     * @param <T>   Type of list
     * @return List of List data
     */
    private <T> List<List<? extends T>> splitJob(int parts, List<? extends T> job) {
        List<List<? extends T>> jobsParts = new ArrayList<>();
        int step = job.size() / parts;
        int i, t;
        for (i = 0, t = 0; t < parts - 1; i += step, t++) {
            jobsParts.add(job.subList(i, i + step));
        }

        jobsParts.add(job.subList(i, job.size()));

        return jobsParts;
    }

    /**
     * return result for task with comparator
     *
     * @param threads   number of tread
     * @param values    list of input data
     * @param cmp       comparator for jobs
     * @param operation class contain methods for using operation
     * @param <R>       return type
     * @param <T>       type of data list
     * @return return value for thid operation
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws InterruptedException
     */
    private <R, T> R getResultForTaskWithComparator(int threads, List<? extends T> values, Comparator<? super T> cmp, Class<? extends TaskWithComparatorExecutor> operation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {
        threads = Math.min(threads, values.size());
        ArrayList<TaskExecuter<R, T>> runs = new ArrayList<>(threads);
        List<List<? extends T>> jobsParts = splitJob(threads, values);
        for (int i = 0; i < threads; i++) {
            TaskExecuter<R, T> curr = (TaskExecuter<R, T>) operation.getDeclaredConstructor(TaskWithComparatorExecutor.constructorArgs).newInstance(jobsParts.get(i), cmp);
            runs.add(curr);
        }
        List<R> results = parallelExecute(runs);
        for (TaskExecuter<R, T> run : runs) {
            results.add(run.returnResult());
        }
        return runs.get(0).unionResults(results);
    }

    /**
     * return result for predicate jobs
     *
     * @param threads   number of tread
     * @param values    list of input data
     * @param predicate predicate for jobs
     * @param operation class contain methods for using operation
     * @param <T>       type of input list
     * @return return value for operation
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws InterruptedException
     */
    private <T> Boolean getResultForTaskWithPredicate(int threads, List<? extends T> values, Predicate<T> predicate, Class<? extends TaskWithPredicateExecuter> operation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {
        threads = Math.min(threads, values.size());
        ArrayList<TaskExecuter<Boolean, T>> runs = new ArrayList<>(threads);
        List<List<? extends T>> jobsParts = splitJob(threads, values);
        for (int i = 0; i < threads; i++) {
            TaskExecuter<Boolean, T> curr = (TaskExecuter<Boolean, T>) operation.getDeclaredConstructor(TaskWithPredicateExecuter.constructorArgs).newInstance(jobsParts.get(i), predicate);
            runs.add(curr);
        }

        List<Boolean> results = parallelExecute(runs);

        return runs.get(0).unionResults(results);
    }

    /**
     * run tasks in threads
     *
     * @param runs List of taskExecuter
     * @param <R>  return tupe
     * @param <T>  input list tupe
     * @throws InterruptedException
     */
    private <R, T> void runInThreads(List<TaskExecuter<R, T>> runs) throws InterruptedException {
        ArrayList<Thread> threadsList = new ArrayList<>(runs.size());
        for (TaskExecuter<R, T> run : runs) {
            Thread currThread = new Thread(run);
            currThread.start();
            threadsList.add(currThread);
        }
        for (Thread thread : threadsList) {
            thread.join();
        }
    }

    @Override
    /**
     * calculate maximum
     */
    public <T> T maximum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        try {
            return getResultForTaskWithComparator(threads, list, comparator, Maximum.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * calculate minimum
     */
    public <T> T minimum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        try {
            return getResultForTaskWithComparator(threads, list, comparator, Minimum.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    /**
     * calculate all predicate
     */
    public <T> boolean all(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        try {
            return getResultForTaskWithPredicate(threads, list, predicate, AllPredicate.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    /**
     * calculate any predicate
     */
    public <T> boolean any(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        try {
            return getResultForTaskWithPredicate(threads, list, predicate, AnyPredicate.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * execute in paralel maper
     *
     * @param jobs list of taskExecuters
     * @param <R>  return type
     * @param <T>  input data list type
     * @return return valur for this TaskExecuters
     * @throws InterruptedException
     */
    private <R, T> List<R> parallelExecute(List<TaskExecuter<R, T>> jobs) throws InterruptedException {
        if (mapper != null) {
            return mapper.map(new Function<TaskExecuter<R, T>, R>() {
                @Override
                public R apply(TaskExecuter<R, T> tJob) {
                    tJob.run();
                    return tJob.returnResult();
                }
            }, jobs);
        } else {
            List<R> result = new ArrayList<>(jobs.size());
            List<Thread> threads = new ArrayList<>(jobs.size());
            for (TaskExecuter<R, T> job : jobs) {
                Thread curr = new Thread(job);
                curr.start();
                threads.add(curr);
            }
            for (Thread thread : threads) {
                thread.join();
            }
            for (TaskExecuter<R, T> job : jobs) {
                result.add(job.returnResult());
            }
            return result;
        }
    }


}
