/**
 * Created by delf on 11.03.14.
 */
public class ArrayQueueADT {
    private Object queue[] = new Object[64];
    private int start = 0, end = 0;

    public static void enqueue(ArrayQueueADT queu, Object element) {
        if ((queu.start == queu.end + 1) || ((queu.start == 0) && (queu.end == queu.queue.length - 1))) {
            Object new_queue[] = new Object[2 * queu.queue.length];
            if (queu.start < queu.end) {
                for (int i = queu.start; i < queu.end; i++) {
                    new_queue[i - queu.start] = queu.queue[i];
                }
                queu.end = queu.end - queu.start;
                queu.start = 0;
            } else {
                for (int i = queu.start; i < queu.queue.length; i++) {
                    new_queue[i - queu.start] = queu.queue[i];
                }
                for (int i = 0; i <= queu.end; i++) {
                    new_queue[i + queu.queue.length - queu.start] = queu.queue[i];
                }
                queu.end = queu.queue.length - 1;
                queu.start = 0;
            }
            queu.queue = new_queue;
        }
        queu.queue[queu.end++] = element;
        if (queu.end == queu.queue.length) {
            queu.end = 0;
        }
    }


    public static Object peek(ArrayQueueADT queu) {
        assert size(queu) > 0;
        return queu.queue[queu.start];
    }


    public static boolean isEmpty(ArrayQueueADT queu) {
        /*if ((queu.end - queu.start == 0) || ((queu.start == queu.queue.length - 1) && (queu.end == 0))) {
            return true;
        } else {
            return false;
        } */
        if (size(queu) == 0) {
            return true;
        }
        return false;
    }

    public static int size(ArrayQueueADT queu) {
        if (queu.end >= queu.start) {
            return queu.end - queu.start;
        } else {
            return (queu.queue.length - queu.start) + queu.end;
        }
    }


    public static Object dequeue(ArrayQueueADT queu) {
        assert size(queu) > 0;
        Object ret = queu.queue[queu.start++];
        queu.queue[queu.start-1]=null;
        if (queu.start == queu.queue.length) {
            queu.start = 0;
        }
        return ret;
    }

}
