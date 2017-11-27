package com.jimmy.project.euler;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by jinguochong on 2017/8/15.
 * Dynamic Programming
 * 动态方程的体现。
 * 思路：如果使用Brute Force算法，需要把每条路径遍历一遍
 * 如果使用dp思路，如下图：
 * <p>
 *     1
 *   2   3
 * 4   5   6
 * <p>
 * 最后是要从2或者3出发，选取2+4，2+5，3+5，3+6中最大的
 * 可简化为
 * <p>
 * 1
 * 7   9 // max(2+4，2+5)   max(3+5，3+6)
 * <p>
 * 从后往前递推，得解。
 * <p>
 * 分析BF和DP的时间复杂度
 * BF : 有 {2 pow(h -1) //h 树高} 这么多条路径，乘以（h-1）次的加法
 * DP : 第n层，需要计算2(n-1)加法，(n-1)次比较，总：3(n-1)+3(n-2)+...+3*1 n是树宽，正好等于树高
 * DP把BF算法中的pow降低到n平方
 * {@link Problem67}
 * Problem 81 和这个是一模一样的
 */

public class Problem18 {

    @Test
    public void correct() {
        int[][] arrs = new int[][]{};
        //readTxtFile("/Users/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p18_triangle.txt", arrs);
        int big = readTxtFromBottom("/Users/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p18_triangle.txt");
        System.out.println("big : " + big);
    }

    public void readTxtFile(String filePath, int[][] arrs) {

        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);


                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    private int readTxtFromBottom(String filePath) {
        int maxSum = 0;
        try {
            // 下面定位文件末行, 一行一行向上读取
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            long len = raf.length(); // 获得文件的长度,以便定位末尾
            long pos = len - 1; // 定位文件尾
            int[] max = null;
            while (pos > 0) { // 判断文件是否到达头
                --pos; // 一个字符一个字符的向前移动指针
                raf.seek(pos); // 定位文件指针所指的位置
                if (raf.readByte() == '\n') { // 如果是换行符,就可以读取该行了
                    String line = raf.readLine();
                    System.out.println(line);
                    String[] lineStrs = line.split(" ");


                    int[] original = new int[lineStrs.length];

                    /* 开发者不友好
                    if (max == null) {
                        for (int i = 0; i < lineStrs.length; i++) {
                            original[i] = Integer.valueOf(lineStrs[i]);
                        }
                    } else {
                        for (int i = 0; i < lineStrs.length; i++) {
                            original[i] = Integer.valueOf(lineStrs[i]) + max[i];
                        }
                    }*/
                    for (int i = 0; i < lineStrs.length; i++) {
                        original[i] = Integer.valueOf(lineStrs[i]);
                        if (max != null) {
                            original[i] += max[i];
                        }
                    }

                    max = getMax(original);
                }
            }

            raf.seek(pos); // 最后还需要读取第一行
            String firstLine = raf.readLine();
            System.out.println(firstLine);
            maxSum = max[0] + Integer.valueOf(firstLine);
            raf.close(); // 关闭

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxSum;
    }

    private int[] getMax(int[] arr) {
        int[] max = new int[arr.length - 1];
        for (int i = 0; i < max.length; i++) {
            max[i] = Math.max(arr[i], arr[i + 1]);
        }
        return max;
    }

}
