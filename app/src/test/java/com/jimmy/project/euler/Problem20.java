package com.jimmy.project.euler;

import java.math.BigInteger;

/**
 * Created by jinguochong on 2017/8/20.
 * 之前我有个想法：如果某些计算可以通过移位来快速运算，那么把数转化为二进制是最优算法。
 * 而如果一个数需要计算十进制：比如求余，或者按位取，那么使用对10求余或者char-'0'
 */

public class Problem20 {
    public static void main(String[] args) {
        BigInteger fact = factorial(new BigInteger("100"));
        int sum = sum(fact);
        System.out.println("ans: " + sum);
    }

    private static int sum(BigInteger n) {
        String num = n.toString();
        int sum = 0;
        for (char c : num.toCharArray())
            sum += c - '0';//char 比如：5对应char是53，0对应是48，使用char - '0'其实就是对应了求余
        return sum;
    }

    private static BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0)
            return BigInteger.ONE;
        BigInteger num = factorial(n.subtract(BigInteger.ONE)).multiply(n);
        return num;
    }
}
