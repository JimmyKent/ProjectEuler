package com.jimmy.project.euler;

/**
 * Created by jinguochong on 2017/8/20.
 * Answer:
 * 906609
 */

public class Problem4 {
    public static void main(String[] args) {
        int max = 0;
        int current = 0;
        for (int i = 100; i < 999; i++) {
            for (int j = 100; j < 999; j++) {
                current = i * j;
                if (max < current && palindrome(current)) {
                    max = current;
                }
            }
        }

        System.out.print("max  :  " + max);
    }


    private static boolean palindrome(int num) {//回文
        int x = 0;
        int y = num;

        while (y > 0) {
            x = y % 10 + x * 10;
            y /= 10;
        }


        return x == num;
    }
}
