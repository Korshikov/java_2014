public class Const implements Expression3 {
    private double var;

    public Const(double x) {
        var = x;
    }

    public double evaluate(double var1, double var2, double var3) {
        return var;
    }
}
