package com.jimmy.project.euler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jinguochong on 2017/8/15.
 * 233168
 */

public class Problem1 {

    @Test
    public void error() {//有重复，比如15
        int N = 1000;
        int x = 3;
        int y = 5;
        int sum = 0;
        int current = 0;
        for (int i = 1; i < N / 3 + 1; i++) {
            current = x * i;
            if (current < N) {
                sum += current;
            }
            current = y * i;
            if (current < N) {
                sum += current;
            }
        }

        System.out.print("sum = " + sum);

        assertEquals(233168, sum);


    }

    @Test
    public void correct() {
        int N = 1000;
        int sum = 0;
        for (int i = 1; i < N; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }

        System.out.print("sum = " + sum);
        assertEquals(233168, sum);
    }
}
