/**
 * Created by root on 22.04.14.
 */
public class DivByZeroException extends CalculateException{
    DivByZeroException(String message){
        super(message);
    }

    DivByZeroException(){
        super();
    }
}
