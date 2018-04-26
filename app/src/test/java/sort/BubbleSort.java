package sort;


import org.junit.Test;

import java.util.Arrays;

/**
 * 起泡排序
 * <p>
 * 也是有优化版本的
 * <p>
 * 算法的稳定性(stability):重复元素在输入和输出的次序不变. 比如有7a,7b,7c ,经过排序后,7a,7b,7c的顺序是不变的
 * A B C 版本是稳定的
 */
public class BubbleSort {

    @Test
    public void main() {

        int[] arrA = {2, 3, 2, 1};
        bubbleSortA1(arrA, 0, arrA.length);
        System.out.println(Arrays.toString(arrA));

        int[] arrA2 = {3, 2, 1};
        int hi = arrA2.length;
        while (hi > 0) {
            bubbleSortA2(arrA2, 0, hi--);
        }
        System.out.println(Arrays.toString(arrA2));

        int[] arr = {3, 2, 1};
        hi = arr.length;
        while (!bubbleSortB(arr, 0, hi--)) ;

        System.out.println(Arrays.toString(arr));

        int[] arrC = {3, 2, 1};
        hi = arrC.length;
        int lo = 0;
        while (lo < (hi = bubbleSortC(arr, 0, hi))) ;

        System.out.println(Arrays.toString(arr));

    }

    //每两相邻元素比对
    //时间 三角形 O(n2)
    private static void bubbleSortA1(int[] arr, int lo, int hi) {
        for (int i = hi - 1; i > lo; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    private static void bubbleSortA2(int[] arr, int lo, int hi) {
        while (++lo < hi) {// 自左向右, 逐一检查各对相邻元素
            if (arr[lo - 1] > arr[lo]) {//若逆序
                //swap交换
                int temp = arr[lo - 1];
                arr[lo - 1] = arr[lo];
                arr[lo] = temp;
            }
        }
    }

    //如果后续整体有序,就不再起泡到第一个元素了
    //时间 梯形 O(n1.5)
    private static boolean bubbleSortB(int[] arr, int lo, int hi) {
        boolean sorted = true;//整体有序标志
        while (++lo < hi) { // 自左向右, 逐一检查各对相邻元素
            if (arr[lo - 1] > arr[lo]) {//若逆序
                sorted = false;
                //swap交换
                int temp = arr[lo - 1];
                arr[lo - 1] = arr[lo];
                arr[lo] = temp;
            }
        }
        return sorted;
    }


    //如果后续局部有序,就可以起泡到没有有序的位置
    //时间 O(nr)
    //最坏还是O(n2)
    private static int bubbleSortC(int[] arr, int lo, int hi) {
        int last = lo;//最右侧的逆序对初始化为[lo-1, lo]
        while (++lo < hi) { // 自左向右, 逐一检查各对相邻元素
            if (arr[lo - 1] > arr[lo]) {//若逆序
                last = lo;//更新最右侧的逆序对位置记录
                //swap交换
                int temp = arr[lo - 1];
                arr[lo - 1] = arr[lo];
                arr[lo] = temp;
            }
        }
        return last;//返回最右侧的逆序对位置
    }

}
