/**
 * Created by delf on 01.04.14.
 */
public class UnarySubtract extends Operation{
    UnarySubtract(Expression3... items){
        super(items);
    }


    protected double consider(double ... items) {
        assert items.length ==1;
        return -(items[0]);
    }
}
