/**
 * Created by delf on 18.03.14.
 */
public class Subtract extends Operation {
    Subtract(Polynom ... items){
        constructor(items);
    }


    protected double consider(double ... items) {
        double ret=items[0];
        for(int i=1;i<items.length;i++){
            ret-=items[i];
        }
        return ret;
    }

}
