package com.jimmy.project.euler;


import java.util.Arrays;

/**
 * Created by jinguochong on 2017/8/11.
 */

public class StringMatchTest {

    @org.junit.Test
    public void StringMath() {

        String[][] test = {
                {"abcdefgabcd", "aa", "11"},//11
                {"abcdefgabcd", "bcd", "1"},//1
                {"000100001", "00001", "4"},//1
        };

        /*for (String[] s : test) {
            char[] t = s[0].toCharArray();
            char[] p = s[1].toCharArray();
            int position = Integer.parseInt(s[2]);

            int bf2 = bf2(t, p);
            System.out.println("bf2 : " + bf2);
            assertEquals(bf2, position);

            int kmp = kmp(t, p);
            System.out.println("kmp : " + kmp);
            assertEquals(kmp, position);

            int kmp2 = kmp2(t, p);
            System.out.println("kmp2 : " + kmp2);
            assertEquals(kmp2, position);
        }*/

        int[]  nextTest = next("12123".toCharArray());
        int[]  nextTest2 = next2("12123".toCharArray());

        System.out.println("nextTest : " + Arrays.toString(nextTest));
        System.out.println("nextTest : " + Arrays.toString(nextTest2));

        //nextTest : [-1, 0]
        //nextTest : [-1, 0, 1]
        //nextTest : [-1, 0]

    }


    private int[] nexttt(int[] p) {
        int[] N = new int[p.length];
        int j = 0;
        int t = N[0] = -1;
        while(j < p.length - 1){

            if (t < 0 || p[j] == p[t]){
                N[++j] = ++t;
            }else{
                t = N[t];
            }
        }
        return N;
    }

    /**
     * Brute force algorithm 蛮力算法
     *
     * @param t text 文本串
     * @param p pattern 模式串
     * @return position 位置
     */
    private static int bf(char[] t, char[] p) {
        int i = 0;
        int j = 0;
        while (i < t.length && j < p.length) {
            if (t[i] == (p[j])) {
                i++;
                j++;
            } else {
                i = i - j + 1;//i往后移一个单位
                j = 0;
            }
        }
        return i - j;
    }

    private static int bf2(char[] t, char[] p) {
        int i = 0, j = 0;
        while (i < t.length && j < p.length) {
            if (t[i] == p[j]) {
                i++;
                j++;
            } else {
                i -= j - 1;//i往后移一个单位
                j = 0;
            }
        }
        return i - j;
    }


    private static int kmp(char[] t, char[] p) {
        int i = 0, j = 0;
        int[] next = next(p);

        System.out.println("kmp next : " + Arrays.toString(next));

        while (i < t.length && j < p.length) {
            if (j < 0 || t[i] == p[j]) {
                i++;
                j++;
            } else { //p右移，t不回退
                j = next[j];
            }
        }
        return i - j;
    }

    private static int[] next(char[] p) {
        int[] N = new int[p.length];//next表
        int j = 0;//主串指针
        int t = N[0] = -1;//模式串指针
        while (j < p.length - 1) {
            if (t < 0 || p[j] == p[t]) {
                N[++j] = ++t;
            } else {
                t = N[t];
            }
        }
        return N;
    }

    private static int kmp2(char[] t, char[] p) {
        int i = 0, j = 0;
        int[] next = next2(p);

        System.out.println("kmp2 next : " + Arrays.toString(next));


        while (i < t.length && j < p.length) {
            if (j < 0 || t[i] == p[j]) {
                i++;
                j++;
            } else { //p右移，t不回退
                j = next[j];
            }
        }
        return i - j;
    }

    /**
     * 改进版，改进了 p = 00001 t = 000100001 这种情况
     * 改进的是相同字符连续出现的情况，aba这种情况也被优化了
     * @param p 模式串
     * @return next
     */
    private static int[] next2(char[] p) {
        int[] N = new int[p.length];//next表
        int j = 0;//主串指针
        int t = N[0] = -1;//模式串指针
        while (j < p.length - 1) {
            if (t < 0 || p[j] == p[t]) {
                j++;
                t++;
                N[j] = p[j] != p[t] ? t : N[t];
            } else {
                t = N[t];
            }
        }
        return N;
    }

}
