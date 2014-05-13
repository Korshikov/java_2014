import java.util.*;
/**
 * Created by delf on 31.03.14.
 */
public class ExpressionParser {
    static private String str;
    static private int pointer = 0;

    public static void main(String[] args) {
        try{
            Expression3 a= parse("1+x");
            System.out.println(a.evaluate(1,1,1));

        }catch (CalculateException e){
            System.out.print(e.getMessage());
        }catch (ParserException e){
            System.out.print(e.getMessage());
        }
    }

    static public Expression3 parse(final String a) throws ParserException {
        pointer = 0;
        str = a.replaceAll("mod", "%").replaceAll("\\s+", "");
        assert (str.length() > 0);
        return parseAddOrSub();
    }

    static private Expression3 parseAddOrSub() throws ParserException {
        Expression3 left = parseMulDivMod(), right = null;

        while (str.length() > pointer) {
            if ((str.charAt(pointer) != '+') && (str.charAt(pointer) != '-')) {
                break;
            }


            char sign = str.charAt(pointer);
            switch (sign) {
                case '+':
                    //str = str.substring(1);
                    pointer++;
                    right = parseMulDivMod();
                    left = new Add(left, right);
                    break;
                case '-':
                    //str = str.substring(1);
                    pointer++;
                    right = parseMulDivMod();
                    left = new Subtract(left, right);
                    break;
            }
        }

        return left;
    }

    static private Expression3 parseMulDivMod() throws ParserException {
        Expression3 left = parseBrackets(), right = null;

        while (str.length() > pointer) {
            if ((str.charAt(pointer) != '*') && (str.charAt(pointer) != '/') && (str.charAt(pointer) != '%')) {
                break;
            }


            char sign = str.charAt(pointer);
            switch (sign) {
                case '*':
                    //str = str.substring(1);
                    pointer++;
                    right = parseBrackets();
                    left = new Multiply(left, right);
                    break;
                case '/':
                    //str = str.substring(1);
                    pointer++;
                    right = parseBrackets();
                    left = new Divide(left, right);
                    break;
                case '%':
                    //str = str.substring(1);
                    pointer++;
                    right = parseBrackets();
                    left = new Mod(left, right);
                    break;
            }
        }

        return left;
    }


    static private Expression3 parseBrackets() throws ParserException {
        if ((str.length() > pointer) && (str.charAt(pointer) == '(')) {
            //str = str.substring(1);
            pointer++;
            Expression3 ret = parseAddOrSub();
            //str = str.substring(1);
            if ((str.length() > pointer) && (str.charAt(pointer) != ')')) {
                throw new ParserException("brackets exception");
            }
            pointer++;
            return ret;
        } else {
            return parseUnaryOperation();
        }
    }

    static private Expression3 parseUnaryOperation() throws  ParserException{
        char tmp = str.charAt(pointer);
        Expression3 inside;
        switch (tmp) {
            case '~':
                //str = str.substring(1);
                pointer++;
                inside = parseUnaryOperation();
                return new Neg(inside);
            case '-':
                if ((str.charAt(pointer + 1) >= '0') && (str.charAt(pointer + 1) <= '9') || (str.charAt(pointer + 1) == '.')) {
                    return parseNum();
                }
                //str = str.substring(1);
                pointer++;
                inside = parseUnaryOperation();
                return new UnarySubtract(inside);
        }
        return parseNum();
    }

    static private Expression3 parseNum() throws ParserException {
        if ((str.length() > pointer) && (str.charAt(pointer) == '(')) {
            return parseBrackets();
        }
        if ((str.length() > pointer) && ((str.charAt(pointer) >= '0') && (str.charAt(pointer) <= '9') || (str.charAt(pointer) == '.') || (str.charAt(pointer) == '-'))) {
            int numLen = 1;
            while ((str.length() > numLen + pointer) && ((str.charAt(numLen + pointer) >= '0') && (str.charAt(numLen + pointer) <= '9') || (str.charAt(numLen + pointer) == '.'))) {
                numLen++;
            }
            pointer += numLen;
            long ret = Long.parseLong(str.substring(pointer - numLen, pointer));
            if((ret>=Integer.MAX_VALUE)||(ret<Integer.MIN_VALUE)){
                throw new ParserException("Number too big");
            }
            return new Const(Integer.parseInt(str.substring(pointer - numLen, pointer)));


            //str = str.substring(numLen);
        } else if ((str.charAt(pointer) == 'x') || (str.charAt(pointer) == 'y') || (str.charAt(pointer) == 'z') || (str.charAt(pointer) == 'X') || (str.charAt(pointer) == 'Y') || (str.charAt(pointer) == 'Z')) {
            return new Variable(str.substring(pointer++, pointer));
            //str = str.substring(1);
        }
        throw new ParserException("unexpected input");
    }

}
