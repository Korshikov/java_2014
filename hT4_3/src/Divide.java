/**
 * Created by delf on 18.03.14.
 */
public class Divide extends Operation {
    Divide(Expression3 ... items){
        super(items);
    }


    protected double consider(double ... items) {
        double ret=items[0],buf;
        assert items[1]!= 0;
        buf = items[1];
        for(int i=2;i<items.length;i++){
            assert items[i]!= 0;
            buf*=items[i];
        }
        return ret/buf;
    }
}
