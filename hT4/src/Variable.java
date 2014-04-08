/**
 * Created by delf on 18.03.14.
 */
public class Variable extends Polynomial {
    private final String val;



    public double evaluate(double x, double y, double z) {
        if (val.equals("x"))
            return x;
        else if (val.equals("y"))
            return y;
        else if (val.equals("z"))
            return z;
        else
            return 0;
    }

    Variable(String varName) {
        factors.put(1, 1.);
        val = varName;
        firstItem = this;
    }

}
