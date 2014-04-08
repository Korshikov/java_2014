public class Variable implements Expression3 {
    private final String val;
    public Variable(String s) {
        val = s;
    }

    public double evaluate(double var1, double var2, double var3) {
        if (val.equals("x"))
            return var1;
        else if (val.equals("y"))
            return var2;
        else if (val.equals("z"))
            return var3;
        return 0;
    }
}
