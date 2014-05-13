/**
 * Created by delf on 08.04.14.
 */
public class Neg extends Operation {
    final int MAX_OPERANDS_COUNT=1;
    Neg(Expression3... items) {
        super(items);
    }


    protected int consider(int... items) throws CalculateException {
        assert (items.length<=MAX_OPERANDS_COUNT):"unexpected operands count";
        return operation(items[0]);
    }
    /*protected <T extends Number> T consider(T... items) {
        assert (items.length<=MAX_OPERANDS_COUNT);
        return operation(items[0]);
    }*/

    private int operation(int item) throws CalculateException {
        if(item==Integer.MIN_VALUE){
            throw new CalculateException("Overflow");
        }
        return ~item;
    }
}
