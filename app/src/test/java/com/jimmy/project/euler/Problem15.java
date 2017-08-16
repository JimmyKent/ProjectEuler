package com.jimmy.project.euler;

/**
 * Created by jinguochong on 2017/8/14.
 * Lattice paths
 * 这个有点类似杨辉三角，每个节点只能向下或者向右，最左边的只能向右，中间的可以向右和向下，最右边的只能向下，统计里面所有可能的路径
 * 每个节点对应的路径就是左边和上面节点路径之和。
 */

public class Problem15 {

    @org.junit.Test
    public void problem15() {
        int size = 20;
        long[][] lattice = new long[21][21];

        //初始化
        for (int i = 0; i <= size; i++) {
            lattice[0][i] = 1;
            lattice[i][0] = 1;
        }

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j ++){
                lattice[i][j] = lattice[i-1][j] + lattice[i][j-1];
            }
        }
        System.out.println("final : " + lattice[20][20]);
    }
}
