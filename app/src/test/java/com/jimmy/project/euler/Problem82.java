package com.jimmy.project.euler;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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

        这个思路是从其他位置到当前位置的算法;
        应该也有从当前位置触发,判断是去哪个位置的
        http://www.javashuo.com/content/p-4338566.html
        */
public class Problem82 {

    private static final String FILE_PATH = "/Users/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p81-82-83_matrix.txt";

    @Test
    public void main() {
        int[][] arrs = new int[80][80];

        readTxtFile(FILE_PATH, arrs);


        cal(arrs);

        int a = 260324;

    }

    private void cal(int[][] arr) {

        //第二列和最后一列需要单独计算,不存在比较

        for (int i = 0; i < arr.length; i++) {
            arr[i][1] += arr[i][0];
        }

        //一般性
        for (int j = 2; j < arr.length - 1; j++) { //按列循环

            for (int i = 0; i < arr[0].length; i++) { //列中的每个位置,对应是行

                //int minimal = arr[i - 1][j];//先取arr[i][j]左边的值


                //要达到图中的效果,此处有图

                int[] sum = new int[80];
                for (int k = i - 1; k > -1; k--) {
                    sum[k] = arr[k][j] + arr[k + 1][j];
                }

                sum[i] = arr[i][j];

                for (int k = i + 1; k < 80; k++) {
                    sum[k] = arr[k][j] + arr[k - 1][j];
                }

                int columnMinimal = sum[i] + arr[i][j - 1];//取第一个

                //加回前面一列的值
                for (int k = 0; k < 80; k++) {
                    sum[k] += arr[k][j - 1];
                    if (sum[k] < columnMinimal) {
                        columnMinimal = sum[k];
                    }
                }

                arr[i][j] += columnMinimal;

            }

        }


        for (int i = 0; i < arr.length; i++) {
            arr[i][arr.length - 1] += arr[i][arr.length - 2];
        }

        //这里就不和上一个for合并了,方便理解
        int minimal = arr[0][arr[0].length - 1];
        for (int i = 0; i < 80; i++) {
            if (arr[i][arr[0].length - 1] < minimal) {
                minimal = arr[i][arr[0].length - 1];
            }
        }

        System.out.println(minimal);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++){
            for (int j = 0 ; j <80 ; j++){
                sb.append(arr[i][j]).append("  ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }


    private void readTxtFile(String filePath, int[][] arrs) {

        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                int i = 0;
                int j = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //System.out.println(lineTxt);
                    String[] lineArr = lineTxt.split(",");
                    for (String num : lineArr) {
                        arrs[i][j] = Integer.valueOf(num);
                        j++;
                    }
                    i++;
                    j = 0;
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
}
