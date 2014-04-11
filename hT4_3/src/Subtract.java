/**
 * Created by delf on 18.03.14.
 */
public class Subtract extends Operation {
    public static final int MIN_OPERANDS_COUNT = 2;

    Subtract(Expression3... items) {
       super(items);
    }



    protected int consider(int... items) {
        assert items.length >= MIN_OPERANDS_COUNT;
        int ret = items[0];
        for (int i = 1; i < items.length; i++) {
            ret -= items[i];
        }
        return ret;
    }

}
