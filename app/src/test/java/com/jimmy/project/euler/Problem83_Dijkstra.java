package com.jimmy.project.euler;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by jinguochong on 06/12/2017.
 */

public class Problem83_Dijkstra {

    private static final String FILE_PATH = "/home/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p81-82-83_matrix.txt";

    @Test
    public  void main() {
        System.out.println(run());
    }


    private static final int INFINITY = Integer.MAX_VALUE / 2;


    private int[][] distance;

    private String run() {
        int[][] GRID = new int[80][80];
        readTxtFile(FILE_PATH, GRID);

        int h = GRID.length;
        int w = GRID[0].length;
        distance = new int[h][w];
        for (int[] row : distance)
            Arrays.fill(row, INFINITY);

        // Bellman–Ford algorithm
        distance[0][0] = GRID[0][0];
        for (int i = 0; i < w * h; i++) {

            //完整遍历所有位置，一定会找到一个位置，距离进行了修改。
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int temp = INFINITY;
                    temp = Math.min(getDistance(x - 1, y), temp);
                    temp = Math.min(getDistance(x + 1, y), temp);
                    temp = Math.min(getDistance(x, y - 1), temp);
                    temp = Math.min(getDistance(x, y + 1), temp);
                    distance[y][x] = Math.min(GRID[y][x] + temp, distance[y][x]);
                }
            }
        }
        return Integer.toString(distance[h - 1][w - 1]);
    }


    private int getDistance(int x, int y) {
        if (y < 0 || y >= distance.length || x < 0 || x >= distance[y].length)
            return INFINITY;
        else
            return distance[y][x];
    }


    private static void readTxtFile(String filePath, int[][] arrs) {

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
