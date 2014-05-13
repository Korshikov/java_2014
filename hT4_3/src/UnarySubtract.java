/**
 * Created by delf on 01.04.14.
 */
public class UnarySubtract extends Operation {
    UnarySubtract(Expression3... items) {
        super(items);
    }


    protected int consider(int... items) throws CalculateException {
        assert (items.length == 1):"unexpected operands count";
        if(items[0]==Integer.MIN_VALUE)
        {
            throw new CalculateException("Overflow");
        }
        return -(items[0]);
    }
}