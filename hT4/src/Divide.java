import java.util.Map;

/**
 * Created by delf on 18.03.14.
 */
/*public class Divide extends Operation {
    Divide(Polynomial firstItem, Polynomial secondItem) {
        doOperation(firstItem, secondItem);
    }

    private void doOperation(Polynomial firstItem, Polynomial secondItem) {
        for (Map.Entry<Integer, Double> entry1 : firstItem.factors.entrySet()) {
            for (Map.Entry<Integer, Double> entry2 : secondItem.factors.entrySet()) {
                factors.put(entry1.getKey() - entry2.getKey(), entry1.getValue() / entry2.getValue());
            }
        }

    }
}
