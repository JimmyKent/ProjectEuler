package com.jimmy.project.euler;

import org.junit.Test;

/**
 * Created by jinguochong on 2017/8/20.
 * 观察：
 * 0123456987
 * 0123457689
 * 1:从后往前找到第一个顺序的两个数：6和9，
 * 2.2.找到987中比6大的最小的数，
 * 3.交换最小的和6，
 * 4.然后把后面的顺排，
 */
public class Problem24 {
    @Test
    public void test() {

        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        //从1开始，是因为0就是numbers最开始的样子
        func(numbers);

    }

    private void func(int[] numbers) {
        String answer = "";
        for (int i = 1; i < 1000000; i++) {//1000000
            next(numbers);
        }

        for (int j = 0; j < numbers.length; j++) {
            answer += "" + numbers[j];
        }
        System.out.println("Answer2: " + answer);

    }

    public static void next(int[] array) {
        //1:从后往前找到第一个顺序的两个数
        int index1 = array.length - 2;
        while (index1 >= 0 && array[index1] > array[index1 + 1]) {
            index1--;
        }
        if (index1 == -1) {
            return;
        }
        //2.找到987中比6大的最小的数，这里从后往前找，因为最后的最小。
        int index2 = array.length - 1;//用于和index1交换
        while (array[index2] < array[index1]) {
            index2--;
        }

        //3.交换最小的和index1
        int min = array[index1];
        array[index1] = array[index2];
        array[index2] = min;

        //4.然后把index2后面的顺排，此时是倒序的
        int temp;
        for (int i = index1 + 1, j = array.length - 1; i < j; i++, j--) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

}
