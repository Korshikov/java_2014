/**
 * Created by delf on 18.03.14.
 */
public class Add extends Operation {
    Add(Expression3... items) {
        super(items);
    }



    protected int consider(int... items) {
        int ret =  items[0];
        for (int i = 1; i < items.length; i++) {
            ret += items[i];
        }
        return ret;
    }
    /*protected <T extends Number> T consider(T... items) {
        T ret = (T) items[0];
        for (int i = 1; i < items.length; i++) {
            ret = operation(ret,items[i]);
        }
        return ret;
    }

    protected static double operation(final double a, final double b){
        return a+b;
    }*/
}
