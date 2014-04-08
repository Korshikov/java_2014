/**
 * Created by delf on 18.03.14.
 */
abstract public class Polynom implements Expression3{
    abstract public double evaluate(double var1, double var2, double var3);
    public int evaluate(int var1, int var2, int var3){
            return (int) evaluate((double) var1,(double) var2,(double) var3);
    }
}
