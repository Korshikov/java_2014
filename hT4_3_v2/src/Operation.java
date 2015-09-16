/**
 * Created by delf on 18.03.14.
 */
abstract public class Operation extends Polynom {
    protected Polynom firstItem, secondItem;

    void constructor(Polynom ... items) {
        firstItem = items[0];
        secondItem = items[1];
    }

    protected abstract double consider(double ... items);

    public double evaluate(double var1, double var2, double var3) {
        return consider(firstItem.evaluate(var1, var2, var3), secondItem.evaluate(var1, var2, var3));
    }
}
