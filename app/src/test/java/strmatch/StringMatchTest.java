package strmatch;


import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by jinguochong on 2017/8/11.
 */

public class StringMatchTest {

    @org.junit.Test
    public void StringMath() {

        String[][] test = {
                {"abcd", "aa", "3"},//3
                {"abcdefgabcd", "bcd", "1"},//1
                {"000100001", "00001", "4"},//1
        };

        for (String[] s : test) {
            char[] t = s[0].toCharArray();
            char[] p = s[1].toCharArray();
            int position = Integer.parseInt(s[2]);

            int bf1 = bf1(t, p);
            System.out.println("bf1 : " + bf1);
            assertEquals(bf1, position);

            int bf2 = bf2(t, p);
            System.out.println("bf2 : " + bf2);
            assertEquals(bf2, position);

            int kmp = kmp(t, p);
            System.out.println("kmp : " + kmp);
            //assertEquals(kmp, position);

            int kmp2 = kmp2(t, p);
            System.out.println("kmp2 : " + kmp2);
            //assertEquals(kmp2, position);
        }

        int[] nextTest = next("12123".toCharArray());
        int[] nextTest2 = next2("121212123".toCharArray());

        System.out.println("nextTest : " + Arrays.toString(nextTest));
        System.out.println("nextTest : " + Arrays.toString(nextTest2));

        //nextTest : [-1, 0]
        //nextTest : [-1, 0, 1]
        //nextTest : [-1, 0]

    }


    /**
     * Brute force algorithm 蛮力算法
     *
     * @param t text 文本串
     * @param p pattern 模式串
     * @return position 位置
     * 改进条件: i < n - m + 1 如果t剩下的字符串长度小于p的长度,就退出
     */
    private static int bf1(char[] t, char[] p) {
        int i = 0, j = 0; // i - 文本串当前位置, j 模式串当前位置
        int n = t.length, m = p.length;
        //while (i < n && j < m) { // 如果因为j退出, 匹配,  i退出, 没有匹配的
        while (i < n - m + 1 && j < m) { // 边界改进
            if (t[i] == (p[j])) {
                i++;
                j++;
            } else {
                i = i - j + 1;//i往后移一个单位
                j = 0;
            }
        }
        return i - j;//如果匹配, i - j 就是对的位置的第一个字母, 没有匹配的情况, i - j == n - m + 1
    }


    //
    private static int bf2(char[] t, char[] p) {
        int i, j; // i + j - 文本串当前位置, j 模式串当前位置
        int n = t.length, m = p.length;
        //t[i+j]与p[j]对齐
        for (i = 0; i < n - m + 1; i++) {//i < n - m + 1 如果t剩下的字符串长度小于p的长度,就退出
            //每次当前字符比对成功, 看起来是只是j前进, i没有变化, 但实质比对的还是i+j位置
            for (j = 0; j < m; j++) {
                if (t[i + j] != p[j])
                    break;
            }
            if (m <= j)
                break;//找到匹配字符
        }
        return i; //如果匹配, i就是对的位置的第一个字母, 失配的时候 i == n - m + 1
    }


    /**
     * @param t text 文本串
     * @param p pattern 模式串
     * @return position 位置
     *
     * j<0:假想模式:哨兵模式,简化代码,统一理解
     * j<0 的过程: 当j==0时, 对应p的首字符不匹配的情况.
     * t[i] != p[j], 进入else语句,p要向前进,此时p模式串指向的位置j已经是0了,
     * 所以通过next表找到了下一个位置,就是-1, 进入while循环,此时j == -1 < 0, 对于p而言,-1位置是通配符,
     * 和所有字符匹配,可以进行i++; j++; 这个设计很巧妙.
     * 巧妙的在p首字符不匹配的情况下,进行 i 前进,到下一个字符.
     */
    private static int kmp(char[] t, char[] p) {//对应bf1
        int i = 0, j = 0;
        int[] next = next(p);
        System.out.println("kmp next : " + Arrays.toString(next));
        while (i < t.length - p.length + 1 && j < p.length) {
            if (j < 0 || t[i] == p[j]) {//多了j<0, next表的第一项是-1, next表的第一项认为是通配符,和所有字符匹配
                i++;
                j++;
            } else { //p右移，t不回退
                j = next[j];//j不重新赋值0
            }
        }
        return i - j;
    }

    //在j位置,在j的前缀中,存在next[j]的真前缀和真后缀完全匹配,且是最长匹配
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
        while (i < t.length - p.length + 1 && j < p.length) {
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
     * 改进的是相同字符多次连续或者不连续出现的情况，aba这种情况也被优化了
     *
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
