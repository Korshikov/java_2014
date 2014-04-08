/**
 * Created by delf on 11.03.14.
 */
public class ArrayQueue {
    private Object queue[] = new Object[64];
    private int start = 0, end = 0;

    public void enqueue(Object element) {
        if ((start == end + 1) || ((start == 0) && (end == queue.length - 1))) {
            Object new_queue[] = new Object[2 * queue.length];
            if (start < end) {
                for (int i = start; i < end; i++) {
                    new_queue[i - start] = queue[i];
                }
                end = end - start;
                start = 0;
            } else {
                for (int i = start; i < queue.length; i++) {
                    new_queue[i - start] = queue[i];
                }
                for (int i = 0; i <= end; i++) {
                    new_queue[i + queue.length - start] = queue[i];
                }
                end = queue.length - 1;
                start = 0;
            }
            queue = new_queue;
        }
        queue[end++] = element;
        if (end == queue.length) {
            end = 0;
        }
    }


    public Object peek() {
        assert size() > 0;
        return queue[start];
    }


    public boolean isEmpty() {
        /*if ((end - start == 0) || ((start == queue.length - 1) && (end == 0))) {
            return true;
        } else {
            return false;
        }*/
        if (size()==0){
            return true;
        }
        return false;
    }

    public int size() {
        if (end >= start) {
            return end - start;
        } else {
            return (queue.length - start) + end;
        }
    }


    public Object dequeue() {
        assert size() > 0;
        Object ret = queue[start++];
        queue[start-1]=null;
        if (start == queue.length) {
            start = 0;
        }
        return ret;
    }
}

