/**
 * Created by delf on 31.03.14.
 */
public class ExpressionParser {
    static private String str;


    static public Expression3 parse(final String a) {
        str = a.replaceAll("\\s+", "");
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("mod", "%");
        assert (str.length() > 0);
        return parseAddOrSub();
    }

    static private Expression3 parseAddOrSub() {
        Expression3 left = parseMulDivMod(), right = null;

        while (str.length() > 0) {
            if ((str.charAt(0) != '+') && (str.charAt(0) != '-')) {
                break;
            }


            char sign = str.charAt(0);
            switch (sign) {
                case '+':
                    str = str.substring(1);
                    right = parseMulDivMod();
                    left = new Add(left, right);
                    break;
                case '-':
                    str = str.substring(1);
                    right = parseMulDivMod();
                    left = new Subtract(left, right);
                    break;
            }
        }

        return left;
    }

    static private Expression3 parseMulDivMod() {
        Expression3 left = parseBrackets(), right = null;

        while (str.length() > 0) {
            if ((str.charAt(0) != '*') && (str.charAt(0) != '/') && (str.charAt(0) != '%')) {
                break;
            }


            char sign = str.charAt(0);
            switch (sign) {
                case '*':
                    str = str.substring(1);
                    right = parseBrackets();
                    left = new Multiply(left, right);
                    break;
                case '/':
                    str = str.substring(1);
                    right = parseBrackets();
                    left = new Divide(left, right);
                    break;
                case '%':
                    str = str.substring(1);
                    right = parseBrackets();
                    left = new Mod(left, right);
                    break;
            }
        }

        return left;
    }


    static private Expression3 parseBrackets() {
        if (str.charAt(0) == '(') {
            str = str.substring(1);
            Expression3 ret = parseAddOrSub();
            str = str.substring(1);
            return ret;
        } else {
            return parseUnaryOperation();
        }
    }

    static private Expression3 parseUnaryOperation() {
        char tmp = str.charAt(0);
        Expression3 inside;
        switch (tmp) {
            case '~':
                str = str.substring(1);
                inside = parseUnaryOperation();
                return new Neg(inside);
            case '-':
                if ((str.charAt(1) >= '0') && (str.charAt(1) <= '9') || (str.charAt(1) == '.')) {
                    return parseNum();
                }
                str = str.substring(1);
                inside = parseUnaryOperation();
                return new UnarySubtract(inside);
        }
        return parseNum();
    }

    static private Expression3 parseNum() {
        if ((str.length() > 0) && (str.charAt(0) == '(')) {
            return parseBrackets();
        }
        Expression3 ret = null;
        if ((str.length() > 0) && ((str.charAt(0) >= '0') && (str.charAt(0) <= '9') || (str.charAt(0) == '.') || (str.charAt(0) == '-'))) {
            int numLen = 1;
            while ((str.length() > numLen) && ((str.charAt(numLen) >= '0') && (str.charAt(numLen) <= '9') || (str.charAt(numLen) == '.'))) {
                numLen++;
            }
            ret = new Const(Integer.parseInt(str.substring(0, numLen)));
            str = str.substring(numLen);
        } else {
            ret = new Variable(str.substring(0, 1));
            str = str.substring(1);
        }
        return ret;
    }

}
