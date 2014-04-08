public abstract class BinaryOperator implements Expression3 {
    protected Expression3 arg1, arg2;

    public double evaluate(double var1, double var2, double var3) {
        double ret1 = arg1.evaluate(var1, var2, var3);
        double ret2 = arg2.evaluate(var1, var2, var3);

        return calculate(ret1, ret2);
    }

    protected abstract double calculate(double x, double y);
}
