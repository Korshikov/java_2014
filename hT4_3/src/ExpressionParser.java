/**
 * Created by delf on 31.03.14.
 */
public class ExpressionParser {
    static private String str;
    static private int pointer=0;

    static public Expression3 parse(final String a) {
        pointer = 0;
        str = a.replaceAll("mod", "%").replaceAll("\\s+", "");
        //str = str.replaceAll("\\s", "");
        //str = str.replaceAll("mod", "%");
        assert (str.length() > 0);
        return parseAddOrSub();
    }

    static private Expression3 parseAddOrSub() {
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

    static private Expression3 parseMulDivMod() {
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


    static private Expression3 parseBrackets() {
        if ((str.length()>pointer)&&(str.charAt(pointer) == '(')) {
            //str = str.substring(1);
            pointer++;
            Expression3 ret = parseAddOrSub();
            //str = str.substring(1);
            pointer++;
            return ret;
        } else {
            return parseUnaryOperation();
        }
    }

    static private Expression3 parseUnaryOperation() {
        char tmp = str.charAt(pointer);
        Expression3 inside;
        switch (tmp) {
            case '~':
                //str = str.substring(1);
                pointer++;
                inside = parseUnaryOperation();
                return new Neg(inside);
            case '-':
                if ((str.charAt(pointer+1) >= '0') && (str.charAt(pointer+1) <= '9') || (str.charAt(pointer+1) == '.')) {
                    return parseNum();
                }
                //str = str.substring(1);
                pointer++;
                inside = parseUnaryOperation();
                return new UnarySubtract(inside);
        }
        return parseNum();
    }

    static private Expression3 parseNum() {
        if ((str.length() > pointer) && (str.charAt(pointer) == '(')) {
            return parseBrackets();
        }
        Expression3 ret = null;
        if ((str.length() > pointer) && ((str.charAt(pointer) >= '0') && (str.charAt(pointer) <= '9') || (str.charAt(pointer) == '.') || (str.charAt(pointer) == '-'))) {
            int numLen = 1;
            while ((str.length() > numLen+pointer) && ((str.charAt(numLen+pointer) >= '0') && (str.charAt(numLen+pointer) <= '9') || (str.charAt(numLen+pointer) == '.'))) {
                numLen++;
            }
            ret = new Const(Integer.parseInt(str.substring(pointer,pointer+ numLen)));
            //str = str.substring(numLen);
            pointer+=numLen;
        } else {
            ret = new Variable(str.substring(pointer, pointer+1));
            //str = str.substring(1);
            pointer++;
        }
        return ret;
    }

}
