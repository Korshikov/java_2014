import java.util.Map;

/**
 * Created by delf on 18.03.14.
 */
public class Add extends Operation {
    Add(Polynomial firstItem, Polynomial secondItem) {
        doOperation(firstItem, secondItem);
    }

    private void doOperation(Polynomial firstTerm, Polynomial secondTerm) {
        for (Map.Entry<Integer, Double> entry : firstTerm.factors.entrySet()) {
            factors.put(entry.getKey(), entry.getValue() + ((secondTerm.factors.containsKey(entry.getKey())) ? secondTerm.factors.get(entry.getKey()) : 0.));
        }
        for (Map.Entry<Integer, Double> entry : secondTerm.factors.entrySet()) {
            if (!factors.containsKey(entry.getKey())) {
                factors.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Add(Expression3 firstTerm, Expression3 secondTerm) {
        firstItem = firstTerm;
        secondItem = secondTerm;
    }

    protected double calculate(double x, double y) {
        return x+y;
    }


}
