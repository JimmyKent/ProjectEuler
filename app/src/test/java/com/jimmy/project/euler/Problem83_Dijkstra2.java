package com.jimmy.project.euler;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by jinguochong on 06/12/2017.
 * 425185
 */

public class Problem83_Dijkstra2 {

    private static final String FILE_PATH = "/Users/jinguochong/AndroidStudioProjects/ProjectEuler/app/src/test/java/com/jimmy/project/euler/p81-82-83_matrix.txt";

    private static int MAX = 80;

    private static int[][] distance = new int[MAX][MAX];
    private static int[][] GRID = new int[MAX][MAX];

    @Test
    public void main() {

        readTxtFile(FILE_PATH, GRID);
        cal(GRID);
        System.out.println(distance[distance.length - 1][distance[0].length - 1]);
    }


    private static final int INFINITY = Integer.MAX_VALUE / 2;


    private void cal(int[][] GRID) {

        PriorityQueue<Node> U = new PriorityQueue<>(GRID.length * GRID[0].length, NodeComparator);

        for (int[] row : distance)
            Arrays.fill(row, INFINITY);

        //init
        distance[0][0] = GRID[0][0];
        for (int h = 0; h < GRID.length; h++) {
            for (int w = 0; w < GRID.length; w++) {
                U.add(new Node(h, w, INFINITY));
            }
        }
        U.remove(new Node(0, 0, INFINITY));
        U.remove(new Node(1, 0, INFINITY));
        U.remove(new Node(0, 1, INFINITY));

        U.add(new Node(0, 1, GRID[0][1] + GRID[0][0]));
        distance[0][1] = GRID[0][1] + GRID[0][0];
        U.add(new Node(1, 0, GRID[1][0] + GRID[0][0]));
        distance[1][0] = GRID[1][0] + GRID[0][0];


        Node currentNode;
        while (!U.isEmpty()) {
            currentNode = U.poll();
            int w = currentNode.w;
            int h = currentNode.h;

            distance[h][w] = currentNode.distance;

            System.out.println(currentNode.toString());

            //refresh U's nodes at most 3.

            if (w - 1 > 0 && w - 1 < GRID[h].length) {

                Node left = new Node(h, w - 1, distance[h][w - 1]);

                if (U.contains(left)) {

                    int temp = refresh(left);
                    distance[h][w - 1] = Math.min(GRID[h][w - 1] + temp, distance[h][w - 1]);
                    left.distance = distance[h][w - 1];

                    U.remove(left);
                    U.add(left);
                }
            }

            if (w + 1 > 0 && w + 1 < GRID[h].length) {
                Node right = new Node(h, w + 1, distance[h][w + 1]);

                if (U.contains(right)) {

                    int temp = refresh(right);
                    distance[h][w + 1] = Math.min(GRID[h][w + 1] + temp, distance[h][w + 1]);
                    right.distance = distance[h][w + 1];

                    U.remove(right);
                    U.add(right);
                }
            }

            if (h - 1 > 0 && h - 1 < GRID[h].length) {
                Node top = new Node(h - 1, w, distance[h - 1][w]);

                if (U.contains(top)) {

                    int temp = refresh(top);
                    distance[h - 1][w] = Math.min(GRID[h - 1][w] + temp, distance[h - 1][w]);
                    top.distance = distance[h - 1][w];

                    U.remove(top);
                    U.add(top);
                }
            }

            if (h + 1 > 0 && h + 1 < GRID[h].length) {
                Node bottom = new Node(h + 1, w, distance[h + 1][w]);

                if (U.contains(bottom)) {

                    int temp = refresh(bottom);
                    distance[h + 1][w] = Math.min(GRID[h + 1][w] + temp, distance[h + 1][w]);
                    bottom.distance = distance[h + 1][w];

                    U.remove(bottom);
                    U.add(bottom);
                }
            }
        }

    }

    private int refresh(Node node) {
        int temp = INFINITY;
        temp = Math.min(getDistance(node.h, node.w - 1), temp);
        temp = Math.min(getDistance(node.h, node.w + 1), temp);
        temp = Math.min(getDistance(node.h - 1, node.w), temp);
        temp = Math.min(getDistance(node.h + 1, node.w), temp);
        return temp;
    }

    private int getDistance(int x, int y) {
        if (y < 0 || y >= distance.length || x < 0 || x >= distance[y].length)
            return INFINITY;
        else
            return distance[x][y];
    }

    //correct
    public void testPriority() {
        PriorityQueue<Node> queue = new PriorityQueue<>(4, NodeComparator);
        queue.add(new Node(5, 6, 7));
        queue.add(new Node(5, 6, 8));
        queue.add(new Node(5, 6, 9));
        queue.add(new Node(5, 6, 1));
        Node n = queue.poll();
        while (n != null) {
            System.out.println(n);
            n = queue.poll();
        }
    }

    //correct
    public void testNode() {
        Node n1 = new Node(0, 0, 1);
        Node n2 = new Node(0, 0, 2);
        System.out.println(n1.equals(n2));
    }

    public static Comparator<Node> NodeComparator = new Comparator<Node>() {

        @Override
        public int compare(Node n1, Node n2) {
            return n1.distance - n2.distance;
        }
    };

    class Node {
        int h;//h
        int w;//w
        int distance;


        Node() {
        }

        Node(int h, int w, int distance) {
            this.h = h;
            this.w = w;
            this.distance = distance;
        }


        @Override
        public int hashCode() {
            return h * 100 + w;
        }

        @Override
        public boolean equals(Object obj) {
            Node n = (Node) obj;
            return hashCode() == n.hashCode();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "h=" + h +
                    ", w=" + w +
                    ", distance=" + distance +
                    '}';
        }
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

