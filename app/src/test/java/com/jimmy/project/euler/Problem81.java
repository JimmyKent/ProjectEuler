package com.jimmy.project.euler;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * Created by jinguochong on 24/11/2017.
 */

public class Problem81 {

    private static final String FILE_PATH = "/Users/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p81-82-83_matrix.txt";

    @Test
    public void main() {
        int[][] arrs = new int[80][80];

        readTxtFile(FILE_PATH, arrs);

        System.out.println("last : " + arrs[79][79]);
        //above is correct

        int minimal = getMinimal(arrs);

        System.out.println("minimal : " + minimal);
    }

    private int getMinimal(int[][] arrs) {
        int minimal = 0;

        //从(0, 0) --> (79, 79) 找到每个位置的最短路径

        //arrs[i][j] += Math.min(arrs[i - 1][j], arrs[i][j - 1]);

        //遍历方式: a.按行遍历; b.按列遍历; c.按斜三角遍历

        //a.按行遍历
        for (int i = 1; i < 80; i++) {
            arrs[i][0] += arrs[i - 1][0];
            arrs[0][i] += arrs[0][i - 1];
        }

        for (int i = 1; i < 80; i++) {// row
            for (int j = 1; j < 80; j++) {// column

                arrs[i][j] += Math.min(arrs[i - 1][j], arrs[i][j - 1]);

            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                sb.append(arrs[i][j] + " ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());

        return arrs[79][79];
    }


    private int[] getMax(int[] arr) {
        int[] max = new int[arr.length - 1];
        for (int i = 0; i < max.length; i++) {
            max[i] = Math.max(arr[i], arr[i + 1]);
        }
        return max;
    }

    public void readTxtFile(String filePath, int[][] arrs) {

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
