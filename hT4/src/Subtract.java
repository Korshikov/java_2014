import java.util.Map;

/**
 * Created by delf on 18.03.14.
 */
public class Subtract extends Operation {
    Subtract(Polynomial firstItem,Polynomial secondItem){
        doOperation(firstItem,secondItem);
    }

    private void doOperation(Polynomial firstItem, Polynomial secondItem) {
        for (Map.Entry<Integer,Double> entry : firstItem.factors.entrySet()) {
            factors.put(entry.getKey(),entry.getValue()-((secondItem.factors.containsKey(entry.getKey()))?secondItem.factors.get(entry.getKey()):0.));
        }
        for (Map.Entry<Integer,Double> entry : secondItem.factors.entrySet()) {
            if(!factors.containsKey(entry.getKey())){
                factors.put(entry.getKey(),-entry.getValue());
            }
        }
    super.firstItem=firstItem;
    super.secondItem=secondItem;
    }

    protected double calculate(double x, double y){
        return x-y;
    }
}
