package com.jimmy.project.euler;

/**
 * Created by jinguochong on 2017/8/20.
 */

public class Problem3 {
    public static void main(String[] args) {
        long n = 600851475143L;
        int k = (int) Math.sqrt(n);//一个数的最大公因数不会超过它的开平方数
        if (0 == k % 2) {
            k++;//如果k为一个偶数，则k=k+1
        }
        System.out.println(k);
        for (int i = k; i > 0; i -= 2) {
            if (0 == n % i) {
                if (prime(i)) {
                    System.out.print(i + ", ");
                }
            }
        }
    }

    public static boolean prime(long x) {
        //判定一个数是否为素数
        for (long i = 3; i < Math.sqrt(x); i += 2) {
            if (0 == x % i) {
                return false;
            }
        }
        return true;
    }
}
