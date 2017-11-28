package com.jimmy.project.euler;

import org.junit.Test;

/**
 * Created by jinguochong on 23/11/2017.
 * 这个题有点像地图寻最短路径的样子了
 * http://eastsun.iteye.com/blog/248941
 * 82的图论思路
 * <p>
 * http://blog.sina.com.cn/s/blog_8f84f3400102w1gn.html
 */
/*
题意：给出一个80*80的矩阵，从最左栏任意一格出发，始终只向右、向上或向下移动，到最右栏任意一格结束的最小路径和。

        思路：81题的加强版

        因为以列为单位，方便起见，把81题中的行列调换一下

        f[j][i]表示从最左列起到第j列，第i行时的最小路径和

        则f[j][i] = min(f[j - 1][k]+sum[k,i])

        其中，sum[k,i]表示同一列中，从k行到i行的和，或者是从i行到k行的和（哪个数字大哪个在下面）

        三重循环后得到答案，再从f[80][i]中选一个最小的。这个方法不是效率最高的方法，但是是最容易理解的...
        */
public class Problem82 {

    @Test
    public void main() {

    }
}
