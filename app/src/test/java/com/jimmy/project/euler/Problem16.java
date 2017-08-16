package com.jimmy.project.euler;

import java.math.BigInteger;

/**
 * Created by jinguochong on 2017/8/14.
 */
//1366
public class Problem16 {

    @org.junit.Test
    public void problem16() {
        BigInteger a = new BigInteger("2");
        BigInteger b = a.pow(1000);
        BigInteger sum = BigInteger.ZERO;
        while (b.compareTo(BigInteger.ZERO) > 0) {
            sum = sum.add((b.remainder(BigInteger.TEN)));
            b = b.divide(BigInteger.TEN);
        }
        //double a = 22;
//        int sum = 0;
//        System.out.println("a : " + a);
//        System.out.println("double max: " + Double.MAX_VALUE);
//        while (a > 0) {
//            sum += (int) (a % 10);
//            a /= 10;
//        }
        System.out.println("sum : " + sum);
    }
}
