package com.jimmy.project.euler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinguochong on 2017/8/20.
 * 友好数：要改造约数算法
 */

public class Problem21 {


    @Test
    public void test() {
        long time = System.currentTimeMillis();
        int SIZE = 10000;
        int pSum = 0;
        //TODO 这个从3开始不太和谐
        for (int i = 3; i < SIZE + 1; i++) {
            List<Integer> divisors = new ArrayList<>();
            getDivisor2(i, divisors);
            //友好数
            int amicable = 0;
            for (int j = 0; j < divisors.size(); j++) {
                amicable += divisors.get(j);
            }
            divisors.clear();
            getDivisor2(amicable, divisors);
            int aAmicable = 0;//友好数的友好数
            for (int j = 0; j < divisors.size(); j++) {
                aAmicable += divisors.get(j);
            }
            if (aAmicable == i && amicable != aAmicable) {
                System.out.print("i : " + i);
                System.out.print("  amicable : " + amicable);
                System.out.println("  aAmicable : " + aAmicable);
                pSum += i;
            }
        }
        System.out.println("pSum : " + pSum);
        System.out.println(System.currentTimeMillis() - time);

    }


    /**
     * @param num      要求因数的数
     * @param divisors 所有因数的总和
     */
    private static void getDivisor(int num, List<Integer> divisors) {
        int i = 1;

        int square = (int) Math.sqrt(num);
        while (i < square) {

            if (num % i == 0) {
                divisors.add(i);
                divisors.add(num / i);
            }
            i++;
        }

        if (square * square == num) {
            divisors.add(square);
        }

    }

    private static void getDivisor2(int num, List<Integer> divisors) {
        int i = 2;
        divisors.add(1);
        int square = (int) Math.sqrt(num);
        while (i < square) {

            if (num % i == 0) {
                divisors.add(i);
                divisors.add(num / i);
            }
            i++;
        }

        if (square * square == num) {
            divisors.add(square);
        }

    }

}
