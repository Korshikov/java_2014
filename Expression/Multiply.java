public class Multiply extends BinaryOperator {
    public Multiply(Expression3 a1, Expression3 a2) {
        arg1 = a1;
        arg2 = a2;
    }

    protected double calculate(double x, double y) {
        return x * y;
    }
}
