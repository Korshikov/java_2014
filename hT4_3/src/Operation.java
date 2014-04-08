import java.util.ArrayList;
import java.util.List;

/**
 * Created by delf on 18.03.14.
 */
abstract public class Operation extends Polynom {
    protected final Polynom[] operands;

    Operation(Polynom[] operands) {
        this.operands = operands;
    }

    protected abstract double consider(final double ... items);

    public double evaluate(final double var1,final double var2,final double var3) {
        double[] considerItems = new double[operands.length];
        for(int i=0;i<operands.length;i++){
            considerItems[i]=operands[i].evaluate(var1, var2, var3);
        }
        return consider(considerItems);
    }
}
