/**
 * Created by delf on 18.03.14.
 */
public class Subtract extends Operation {
    public static final int MIN_OPERANDS_COUNT = 2;

    Subtract(Expression3... items) {
       super(items);
    }



    protected int consider(int... items) throws CalculateException {
        assert (items.length >= MIN_OPERANDS_COUNT):"unexpected operands count";
        int ret = items[0];
        for (int i = 1; i < items.length; i++) {
            ret = operation(ret,items[i]);
        }
        return ret;
    }
    /*protected <T extends Number> T consider(T... items) {
        assert items.length >= MIN_OPERANDS_COUNT;
        T ret = items[0];
        for (int i = 1; i < items.length; i++) {
            ret = operation(ret,items[i]);
        }
        return ret;
    } */

    private int operation(int first, int second) throws CalculateException {
        int c=first-second;
        if(((first^c)&(second^c))>0){
            throw new CalculateException("Overflow");
        }

        return (c);
    }
}
