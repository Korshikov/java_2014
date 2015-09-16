import java.util.HashMap;
import java.util.Map;

/**
 * Created by delf on 18.03.14.
 */
public abstract class Polynomial implements Expression, Expression3 {
    protected HashMap<Integer, Double> factors = new HashMap<Integer, Double>();

    public double evaluate(double varValue) {
        double ret = 0;
        for (Map.Entry<Integer, Double> entry : factors.entrySet()) {
            ret += Math.pow(varValue, entry.getKey()) * entry.getValue();
        }
        return ret;
    }

    protected Expression3 firstItem, secondItem;

    public double evaluate(double x, double y, double z) {
        if(secondItem!=null)
            return calculate(firstItem.evaluate(x, y, z), secondItem.evaluate(x, y, z));
        else
            return firstItem.evaluate(x,y,z);
    }

    protected abstract double calculate(double x, double y);

}
