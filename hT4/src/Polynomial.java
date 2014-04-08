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

    abstract public double evaluate(double x, double y, double z);

}
