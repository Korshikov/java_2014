/**
 * Created by delf on 18.03.14.
 */
public class Add extends Operation{
    Add(Expression3 ... items){
        super(items);
    }


    protected int consider(int ... items) {
        int ret= items[0];
        for(int i=1;i<items.length;i++){
            ret+=items[i];
        }
        return ret;
    }
}
