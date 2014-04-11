/**
 * Created by delf on 18.03.14.
 */
public class Const extends Polynom{
    protected final int Value;

    Const(int constValue){
        Value=constValue;
    }

    public int evaluate(int var1, int var2, int var3) {
        return Value;
    }
}
