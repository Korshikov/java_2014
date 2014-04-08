/**
 * Created by delf on 18.03.14.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(  new Add( new Const(-2),new Variable("x")).evaluate(1, 0, 0));
        /*if (args.length > 0) {


            System.out.println(new Add(
                    new Subtract(
                            new Multiply(
                                    new Variable("X"),
                                    new Variable("X")
                            ),
                            new Multiply(
                                    new Variable("X"),
                                    new Const(2)
                            )

                    ),
                    new Const(1)
            ).evaluate(Integer.parseInt(args[0])));
        }*/
    }
}
