public class Main {
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);
        double result =
        (new Add(
            new Subtract(
                new Multiply(
                    new Variable("x"),
                    new Variable("y")
                ),
                new Multiply(
                    new Const(2),
                    new Variable("z")
                )
            ),
            new Const(1)
        )).evaluate(x, y, z);
        System.out.println(result);
    }
}
