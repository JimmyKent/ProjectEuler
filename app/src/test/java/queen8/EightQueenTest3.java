package queen8;

import org.junit.Test;

/**
 * Created by jinguochong on 17-11-23.
 * 深度优先算法
 */

public class EightQueenTest3 {

    private static final int N = 8;

    private int[] existPos = new int[N];//
    private static int count;

    @Test
    public void main() {
        for (int i = 0; i < existPos.length; i++) {
            existPos[i] = -1;
        }
        cal(0);
    }

    /**
     * 每层递归设一个指针，从左往右，如果找到合法的就赋值，往下进行
     * 不会产生回溯过程
     */
    private void cal(int pos) {
        int x = pos % N;
        final int Y = pos / N;
        if (Y == N) {
            print(existPos);
            return;
        }
        while (x < N) {
            if (isValid(pos)) {
                existPos[Y] = pos;
                int next = N * (Y + 1);
                cal(next);//向下
                //深度优先的精髓,需要清空当前行的赋值
                existPos[Y] = -1;
            }
            //向右
            x++;
            pos = x + Y * N;
        }
    }

    private boolean isValid(int pos) {
        boolean valid = true;
        for (int i = 0; i < existPos.length; i++) {
            int queen = existPos[i];
            if (queen != -1) {
                valid = posValid(pos, queen);
                if (!valid) {
                    return false;
                }
            }
        }


        return valid;
    }

    private boolean posValid(int pos, int queen) {
        int x = queen % N;
        int y = queen / N;

        int posX = pos % N;
        int posY = pos / N;
        if (posX == x) {
            return false;
        }
        if (posY == y) {
            return false;
        }

        int leftDiagonal = x - (posY - y) + N * posY;
        if (leftDiagonal == pos) {
            return false;
        }

        int rightDiagonal = x + (posY - y) + N * posY;
        if (rightDiagonal == pos) {
            return false;
        }

        return true;
    }

    private void print(int[] arr) {
        count++;
        System.out.println(count);
        int[][] towArr = new int[8][8];
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            i = i < 0 ? 0 : i;
            sb.append(i).append(" ");
            towArr[i / 8][i % 8] = 1;
        }
        System.out.println(sb);
        print(towArr);
    }

    private static void print(int[][] arr) {
        System.out.println("┌─────────────────┐");
        for (int i = 0; i < 8; i++) {
            System.out.print("│ ");
            for (int j = 0; j < 8; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("│");
            System.out.println("");
        }
        System.out.println("└─────────────────┘");
    }

}
