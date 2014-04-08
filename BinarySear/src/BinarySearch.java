/**
 * Created by delf on 03.03.14.
 */
public class BinarySearch {

    //pre args - array; args.length> 0; any i Integer.parseInt(args[i]) = x by int; any args.length>i>j>=1; Integer.parseInt(args[i])>= Integer.parseInt(args[j])
    public static void main(String[] args) {
        int x, array[] = new int[args.length - 1];
        x = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            array[i - 1] = Integer.parseInt(args[i]);
        }

        //System.out.println(recursBinarySearch(array, x));

        System.out.println(iterativBinarySearch(array, x));
    }

    static int recursBinarySearch(int[] array, int x) {
        return recursBinarySearch(array, x, 0, array.length);
    }

    //pre  any array.length>i>j>=0; array[i]>= array[j]; right-left >= 0;
    //post return =if x in array: max e: array'[e-1]<x; при предположении что array[-1]=-inf
    //else -max e(: array'[e-1]<x) -1;
    static int recursBinarySearch(int[] array, int x, int left, int right) {
        //pre right'-left'>=0
        if (right - left == 0) {
            //pre right'-left'=0 && min e:x<=e in a[right..left] предпологая что a[a.length] = inf
            if (array.length > right && array[right] == x) {
                //pre x in array && right = max e: array'[e-1]<x; при предположении что array[-1]=-inf
                return right;
                //post max e: array'[e-1]<x; при предположении что array[-1]=-inf
            } else {
                //pre x not in array && right = max e: array'[e-1]<x; при предположении что array[-1]=-inf
                return -1 - right;
                //post return = -max e(: array'[e-1]<x) -1;
            }
        } else {
            //pre right'-left'>0
            if (array[(left + right) / 2] < x) {
                //pre (left'+right')/2 < x
                return recursBinarySearch(array, x, (left + right) / 2 + 1, right);
                //post (left'+right')/2+1<=return<=right'; return =if x in array: max e: array'[e-1]<x; при предположении что array[-1]=-inf
                //else -max e(: array'[e-1]<x) -1;
            } else {
                //pre (left'+right')/2 >= x
                return recursBinarySearch(array, x, left, (left + right) / 2);
                //post left'<=return<=(left'+right')/2; return =if x in array: max e: array'[e-1]<x; при предположении что array[-1]=-inf
                //else -max e(: array'[e-1]<x) -1;
            }
        }
    }

    //pre  any array.length>i>j>=0; array[i]>= array[j];
    //post return =if x in array: max e: array'[e-1]<x; при предположении что array[-1]=-inf
    //else -max e(: array'[e-1]<x) -1;
    static int iterativBinarySearch(int[] array, int x) {
        int left = 0, right = array.length;
        //post left=0; right = array.length;
        //inv right'-left'>right-left; && min e in a(:x<=e) содержится в a[right..left] предпологая что a[a..length] = inf
        //pre right-left>0
        while (right - left > 0) {
            //pre right-left>0
            if (array[(left + right) / 2] < x) {
                //pre array[(left + right) / 2]<x
                left = (left + right) / 2 + 1;
                //post left'<left = (left + right) / 2 + 1
            } else {
                //pre array[(left + right) / 2] >= x
                right = (left + right) / 2;
                //post right'>right= (left + right) / 2
            }
            //post right'-left'>right-left;
        }
        //pre right =  max e: array'[e-1]<x; при предположении что array[-1]=-inf
        if (array.length > right && array[right] == x) {
            //pre x in array && right = max e: array'[e-1]<x; при предположении что array[-1]=-inf
            return right;
            //post max e: array'[e-1]<x; при предположении что array[-1]=-inf
        } else {
            //pre x not in array && right = max e: array'[e-1]<x; при предположении что array[-1]=-inf
            return -1 - right;
            //post return = -max e(: array'[e-1]<x) -1;
        }

        //post return =if x in array: max e: array'[e-1]<x; при предположении что array[-1]=-inf
        //else -max e(: array'[e-1]<x) -1;
    }


}
