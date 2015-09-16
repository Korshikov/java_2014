package ru.ifmo.ctddev.korshikov.iterativeparallelism;


/**
 * Created by delf on 02.04.15.
 * class for synchronized counter
 */
public class ParallelMapperCounter {
    /**
     * data field counter
     */
    public int counter = 0;

    /**
     * non synchronized return counter
     *
     * @return counter now
     */
    public final int getCounter() {
        return counter;
    }

    /**
     * non synchronized increment counter
     */
    public void incCounter() {
        ++this.counter;
    }

    /**
     * synchronized increment counter
     */
    public synchronized void synchronizedMethodIncCounter() {
        ++this.counter;
    }

    /**
     * synchronized return counter
     *
     * @return counter value now
     */
    public synchronized final int synchronizedMethodGetCounter() {
        return counter;
    }
}
