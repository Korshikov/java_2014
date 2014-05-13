/**
 * Created by delf on 18.03.14.
 */
public class Multiply extends Operation{
    public static final int MIN_OPERANDS_COUNT = 2;
    Multiply(Expression3 ... items){
        super(items);
    }


    protected int consider(int ... items) throws CalculateException {
        assert items.length>=MIN_OPERANDS_COUNT;
        long ret=items[0];
        for(int i=1;((i<items.length)&&(ret!=0));i++){
            ret*=items[i];
        }
        if(ret > Integer.MAX_VALUE){
            throw new CalculateException("Overflow");
        }
        return (int) ret;
    }
}
