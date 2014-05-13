/**
 * Created by delf on 08.04.14.
 */
public class Mod extends Operation {
    final int MIN_OPERANDS_COUNT = 2;
    final int MAX_OPERANDS_COUNT = 2;

    Mod(Expression3... items) {
        super(items);
    }


    protected int consider(int... items) throws CalculateException{
        assert (items.length >= MIN_OPERANDS_COUNT) && (items.length <= MAX_OPERANDS_COUNT);
        return operation(items[0], items[1]);
    }

    /*protected <T extends Number> T consider(T... items) {
        assert (items.length >= MIN_OPERANDS_COUNT)&&(items.length<=MAX_OPERANDS_COUNT);
        return operation(items[0],items[1]);
    } */


    int operation(int first, int second) throws CalculateException{
        if(second==0){
            throw new DivByZeroException("mod by zero");
        }
        return (first % second);
    }
}
