/**
 * Created by delf on 18.03.14.
 */
abstract public class Operation extends Polynom {
    protected final Expression3[] operands;

    Operation(Expression3[] operands) {
        this.operands = operands;
    }

    protected abstract int consider(final int ... items);

    /*
    protected abstract double consider(final double ... items);

    public double evaluate(final double var1,final double var2,final double var3) {
        double[] considerItems = new double[operands.length];
        for(int i=0;i<operands.length;i++){
            considerItems[i]=operands[i].evaluate(var1, var2, var3);
        }
        return consider(considerItems);
    }
    */
    public int evaluate(final int var1,final int var2,final int var3) {
        int[] considerItems = new int[operands.length];
        for(int i=0;i<operands.length;i++){
            considerItems[i]=operands[i].evaluate(var1, var2, var3);
        }
        return consider(considerItems);
    }
}
