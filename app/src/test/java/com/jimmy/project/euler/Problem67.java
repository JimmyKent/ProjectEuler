package com.jimmy.project.euler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jinguochong on 2017/8/15.
 * Dynamic Programming
 * 动态方程的体现。
 * 思路：如果使用Brute Force算法，需要把每条路径遍历一遍
 * 如果使用dp思路，如下图：
 *
 *     1
 *   2   3
 * 4   5   6
 *
 * 最后是要从2或者3出发，选取2+4，2+5，3+5，3+6中最大的
 * 可简化为
 *
 *    1
 *  7   9 // max(2+4，2+5)   max(3+5，3+6)
 *
 * 从后往前递推，得解。
 *
 * 分析BF和DP的时间复杂度
 * BF : 有 {2 pow(h -1) //h 树高} 这么多条路径，乘以（h-1）次的加法
 * DP : 第n层，需要计算2(n-1)加法，(n-1)次比较，总：3(n-1)+3(n-2)+...+3*1 n是树宽，正好等于树高
 * DP把BF算法中的pow降低到n平方
 * {@link Problem18}
 */

public class Problem67 {

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
