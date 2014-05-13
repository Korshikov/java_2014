/**
 * Created by delf on 18.03.14.
 */
abstract public class Polynom implements Expression3{
    //abstract public double evaluate(double var1, double var2, double var3);
    abstract public int evaluate(int var1, int var2, int var3)  throws CalculateException;
    //abstract public <T extends Number> T evaluate(T var1,T var2,T var3);

}
