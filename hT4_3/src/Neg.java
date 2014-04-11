/**
 * Created by delf on 08.04.14.
 */
public class Neg extends Operation {
    final int MAX_OPERANDS_COUNT=1;
    Neg(Expression3... items) {
        super(items);
    }


    protected int consider(int... items) {
        assert (items.length<=MAX_OPERANDS_COUNT);
        return ~(items[0]);
    }
}
