package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jinguochong on 2017/9/21.
 * 数独
 * ┌───────┬───────┬───────┐
 * │ 3 6 2 │ 5 8 1 │ 4 7 9 │
 * │ 9 1 4 │ 2 3 7 │ 8 5 6 │
 * │ 7 8 5 │ 6 9 4 │ 2 3 1 │
 * ├───────┼───────┼───────┤
 * │ 1 7 9 │ 4 6 2 │ 5 8 3 │
 * │ 8 2 3 │ 7 5 9 │ 6 1 4 │
 * │ 5 4 6 │ 8 1 3 │ 9 2 7 │
 * ├───────┼───────┼───────┤
 * │ 4 3 1 │ 9 2 5 │ 7 6 8 │
 * │ 6 5 7 │ 1 4 8 │ 3 9 2 │
 * │ 2 9 8 │ 3 7 6 │ 1 4 5 │
 * └───────┴───────┴───────┘
 */

public class SudokuTest3 {

    private static String test =
            "" +
                    "360000000" +
                    "004230800" +
                    "000004200" +
                    "070460003" +
                    "820000014" +
                    "500013020" +
                    "001900000" +
                    "007048300" +
                    "000000045";
    private static final int NUM_MAX = 9;

    private static List<Integer> TOTAL = new ArrayList<>();

    private static List<Integer> empty = new ArrayList<>();


    //@Test
    public static void main(String[] args) {
        char[] chars = test.toCharArray();
        //System.out.println(new String(chars));
        initConstants();

        int[][] arr = init(chars);

        print(arr);


        //检查不出无解的情况!
        backTrack(arr, 0, false);


        print(arr);

    }

    private static void initConstants() {
        for (int i = 1; i < 10; i++) {
            TOTAL.add(i);
        }
    }

    private static int[][] init(char[] chars) {

        int[][] arr = new int[9][9];
        int i = 0, j = 0;

        int zeroSum = 0;
        int value;

        for (char c : chars) {
            if (j < 9) {
                value = c - '0';
                arr[i][j] = value;
            } else {
                j = 0;
                i++;
                value = c - '0';
                arr[i][j] = value;
            }
            j++;
            if (value == 0) {
                zeroSum++;
            }
        }

        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                if (arr[a][b] == 0) {
                    empty.add(a * 9 + b);
                }
            }
        }

        System.out.println("Zero sum : " + zeroSum + "  " + Collections.singletonList(empty));

        return arr;
    }

    /**
     * 回溯法求解:
     * 认为数独位置有序;
     * guess 起点有两种:
     * a.从0一直往后遍历
     * b.从possible最少的开始遍历
     * <p>
     * 选b:
     * 维护List<Cell>
     * cell.possible 只由value改变才进行改变, 不随guess改变而变,
     * Cell添加guess list, 如果产生回溯, 删除guess list 的最后一个元素
     * 维护的东西太多了, 不好回溯, 需要牺牲太多内存在存各种状态的List
     * <p>
     * 选a:
     * 暴力求解的略微改进;
     * guess从小到大进行
     * 思路:从0-80 顺序guess, 不需要维护guess表, 产生guess之后, 立即计算下一个位置的可能.
     * 如果产生回溯, 只需把当前guess的值++,
     * <p>
     * 这里是a解法
     *
     *
     * 对于任意空位:
     * 1.关注上一步是前进还是回溯
     * 2.关注当前步是前进还是回溯
     * @param input 原始数独
     */
    private static void backTrack(int[][] input, int position, boolean isBacktrack) {
        if (position > 81) {
            return;
        }
        System.out.println("backTrack : " + position + " " + isBacktrack);
        // recursion
        if (isBacktrack) {
            while (!empty.contains(position)) {
                --position;
                if (position < 0) {
                    return;
                }
            }
            int guessNext = guessNext(input, position);

            if (guessNext == 0) { //回溯
                input[position / 9][position % 9] = 0;
                --position;
                while (!empty.contains(position)) {
                    --position;
                    if (position < 0) {
                        return;
                    }
                }
                isBacktrack = true;
            } else { //前进

                input[position / 9][position % 9] = guessNext;

                ++position;
                while (!empty.contains(position)) {
                    ++position;
                    if (position > 80) {
                        return;
                    }
                }
                isBacktrack = false;
            }
        } else {
            while (!empty.contains(position)) {
                ++position;
                if (position > 80) {
                    return;
                }
            }
            // forward
            int guessNext = guessNext(input, position);
            int value = input[position / 9][position % 9];
            if (value == 0) {
                if (guessNext == 0) { //产生回溯
                    --position;
                    //getNextPosition
                    while (!empty.contains(position)) {
                        --position;
                        if (position < 0) {
                            return;
                        }
                    }
                    isBacktrack = true;
                } else {
                    input[position / 9][position % 9] = guessNext;
                    ++position;
                    //getNextPosition
                    while (!empty.contains(position)) {
                        ++position;
                        if (position > 80) {
                            return;
                        }
                    }
                    isBacktrack = false;
                }
            } else {
                //跳过非空的位置, 最开始题目就给出的位置
                ++position;
                //getNextPosition
                while (!empty.contains(position)) {
                    ++position;
                    if (position > 80) {
                        return;
                    }
                }
                isBacktrack = false;
            }
        }
        backTrack(input, position, isBacktrack);
    }

    /**
     * @return 返回下一个猜测的点
     */
    private static int guessNext(int[][] input, int position) {
        int next = 0;

        Set<Integer> impossible = new HashSet<>();

        int x = position % 9;//行
        int y = position / 9;//列

        int value = input[y][x];

        if (value == NUM_MAX) {
            return next;
        }

        for (int i = 0; i < 9; i++) {
            //找出行已经有的数
            if (input[y][i] != 0) {
                impossible.add(input[y][i]);
            }
            //找出列已经有的数
            if (input[i][x] != 0) {
                impossible.add(input[i][x]);
            }
        }

        //找出块已经有的数

        //找到块的左上角的点, 有9个(0,0) 9个(3,0) ...
        int blockX = x / 3 * 3;
        int blockY = y / 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (input[blockY + i][blockX + j] != 0) {
                    impossible.add(input[blockY + i][blockX + j]);
                }
            }
        }


        List<Integer> possible = new ArrayList<>();
        possible.addAll(TOTAL);

        //System.out.println(Collections.singletonList(possible));

        possible.removeAll(impossible);

        Collections.sort(possible);
        for (int i : possible) {
            if (i > value) {
                return i;
            }
        }

        return next;
    }

    private static void print(int[][] arr) {
        System.out.println("┌───────┬───────┬───────┐");
        for (int i = 0; i < 9; i++) {
            System.out.print("│ ");
            for (int j = 0; j < 9; j++) {
                System.out.print(arr[i][j] + " ");
                if (j == 2 | j == 5)
                    System.out.print("│ ");
            }
            System.out.print("│");
            System.out.print("\n");
            if (i == 2 | i == 5)
                System.out.println("├───────┼───────┼───────┤");
        }
        System.out.println("└───────┴───────┴───────┘");
    }

}