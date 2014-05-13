/**
 * Created by delf on 18.03.14.
 */
public class Divide extends Operation {
    Divide(Expression3 ... items){
        super(items);
    }


    protected int consider(int ... items) throws CalculateException{
        int ret=items[0],buf;
//        assert items[1]!= 0;
        buf = items[1];
        for(int i=2;i<items.length;i++){
            //assert items[i]!= 0;
            if(Math.abs(items[i])<1e-7){
                throw new DivByZeroException("DivByZero");
            }
            buf*=items[i];
        }
        if((ret==Integer.MIN_VALUE)&&(buf==-1))
        {
            throw new CalculateException("Overflow");
        }
        return ret/buf;
    }
}
