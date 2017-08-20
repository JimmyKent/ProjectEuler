package com.jimmy.project.euler;

/**
 * Created by jinguochong on 2017/8/20.
 * Answer:
 * 4613732
 */

public class Problem2 {

    public static void main(String[] args) {
        int m = 1;
        int n = 1;
        int sum = 0;
        int total = 4000000;
        while (m < total && n < total) {
            m = m + n;
            n = m + n;
            if (m % 2 == 0)
                sum += m;
            if (n % 2 == 0)
                sum += n;
        }
        System.out.print("sum : " + sum);
    }

}
