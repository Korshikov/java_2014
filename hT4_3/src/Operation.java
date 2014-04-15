import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by delf on 18.03.14.
 */
abstract public class Operation extends Polynom {
    protected final Expression3[] operands;


    Operation(Expression3[] operands) {
        this.operands = operands;
    }

    /*
    protected <T extends Number> T operation(final T ... operands){
        if(operands instanceof BigDecimal[]){
            return (T) operation((BigDecimal[]) operands);
        }
        if(operands instanceof BigInteger[]){
            return (T) operation((BigInteger[]) operands);
        }
        if(operands instanceof Integer[]){
            return (T) operation((Integer[]) operands);
        }
        if(operands instanceof Double[]){
            return (T) operation((Double[]) operands);
        }
        throw new UnsupportedOperationException("SumAggregator only supports official subclasses of Number");
    }*/

    //protected abstract <T extends Number> T consider(final T... items);
    protected abstract int consider(final int... items);

    /*public <T extends Number> T evaluate(final T var1, final T var2, final T var3) {
        T[] considerItems = (T[]) new Object[operands.length];
        for (int i = 0; i < operands.length; i++) {
            considerItems[i] = operands[i].evaluate(var1, var2, var3);
        }
        return consider(considerItems);
    }*/
    public int evaluate(final int var1, final int var2, final int var3) {
        int[] considerItems = new int[operands.length];
        for (int i = 0; i < operands.length; i++) {
            considerItems[i] = operands[i].evaluate(var1, var2, var3);
        }
        return consider(considerItems);
    }
}
