/**
 * Created by delf on 18.03.14.
 */
public class Const extends Polynomial{
    private double var;
    Const(double constValue){
        factors.put(0,constValue);
        var = constValue;
        firstItem=this;
    }

    public double evaluate(double x, double y, double z)
    {
        return var;
    }
}
