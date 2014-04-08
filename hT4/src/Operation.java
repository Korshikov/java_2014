/**
 * Created by delf on 17.03.14.
 */
public abstract class Operation extends Polynomial {

    public double evaluate(double x, double y, double z) {
            return calculate(firstItem.evaluate(x, y, z), secondItem.evaluate(x, y, z));
    }

    protected abstract double calculate(double x, double y);

}
