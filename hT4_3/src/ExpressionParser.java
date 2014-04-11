/**
 * Created by delf on 31.03.14.
 */
public class ExpressionParser {
    static private String str;


    static public Expression3 parse(String a){
        a.replaceAll("\\s+","");
        a.replaceAll("mod", "%");
        str = a;
        parseAddOrSub();
        return null;
    }

    static private Expression3 parseAddOrSub(){
        Expression3 left= parseMulDivMod(),right=null;
        //while (str.length() > 0) {
            /*if ((str.charAt(0) != '+') && (str.charAt(0) != '-')) {
                break;
            }*/
            if(str.length()==0){
                return left;
            }
            char sign=str.charAt(0);
            str = str.substring(1);
            left = parseMulDivMod();
            switch (sign){
                case '+':
                    return new Add(left,right);
                case '-':
                    return new Subtract(left,right);
            }

        //}
        return left;
        }

    static private Expression3 parseMulDivMod() {
        Expression3 left = parseBrackets(), right = null;
        //while (str.length() > 0) {
            /*if ((str.charAt(0) != '*') && (str.charAt(0) != '/') && (str.charAt(0) != '%')) {
                break;
            }*/
            if(str.length()==0){
                return left;
            }
            char sign = str.charAt(0);
            str = str.substring(1);
            right = parseBrackets();
            switch (sign) {
                case '*':
                    return new Multiply(left, right);
                case '/':
                    return new NumDiv(left, right);
                case '%':
                    return new Mod(left, right);
            }
        //}
        return  left;
    }


    static private Expression3 parseBrackets(){
        if(str.charAt(0)=='('){
            str = str.substring(1);
            Expression3 ret=parseAddOrSub();
            str = str.substring(1);
            return ret;
        }else{
            return parseUnaryOperation();
        }
    }

    static private Expression3 parseUnaryOperation(){
        char tmp =str.charAt(0);
        Expression3 inside;
        switch (tmp){
            case '~':
                str = str.substring(1);
                inside = parseUnaryOperation();
                return new Neg(inside);
            case '-':
                str = str.substring(1);
                inside = parseUnaryOperation();
                return new UnarySubtract(inside);
        }
        return parseNum();
    }

    static private Expression3 parseNum() {
        if((str.length()>0)&&(str.charAt(0)=='(')){
            return parseBrackets();
        }
        Expression3 ret=null;
        if((str.length()>0)&&((str.charAt(0)>='0')&&(str.charAt(0)<='9')||(str.charAt(0)=='.'))){
            int numLen=1;
            while((str.length()>numLen)&&((str.charAt(numLen)>='0')&&(str.charAt(numLen)<='9')||(str.charAt(numLen)=='.'))){
                numLen++;
            }
            ret = new Const(Integer.parseInt(str.substring(0,numLen)));
            str = str.substring(numLen);
        }else{
            ret = new Variable(str.substring(0,1));
            str = str.substring(1);
        }
        return ret;
    }

}
