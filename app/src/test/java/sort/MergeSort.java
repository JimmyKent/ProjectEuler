package sort;

import org.junit.Test;

import java.util.Arrays;


/**
 * 先划分为二        O(1)
 * 子序列递归排序    2 * T(n/2)
 * 合并有序子序列    O(n)
 * <p>
 * 递推式
 * T(n) = 2 * T(n/2) +O(n)  ==> O(n log n)
 */
public class MergeSort {

    @Test
    public void main() {
        int[] arr = {3, 2, 1, 4};
        int hi = arr.length;
        mergeSort(arr, 0, hi);
        System.out.println(Arrays.toString(arr));
    }


    /**
     *
     */
    private static void mergeSort(int[] arr, int lo, int hi) {
        if (hi - lo < 2)
            return;
        int mi = (hi + lo) >> 1;
        mergeSort(arr, lo, mi);
        mergeSort(arr, mi, hi);
        merge2(arr, lo, mi, hi);

    }

    private static void merge(int[] arr, int lo, int mi, int hi) {
        int lb = mi - lo;//前缀的长度
        int[] arrB = new int[lb];
        //不友好
        // for (int i = 0; i < lb; arrB[i] = arr[i++]) ;//复制前子序列arrB
        for (int i = 0; i < lb; i++) {//复制前子序列arrB
            arrB[i] = arr[lo + i];
        }
        int lc = hi - mi;
        int[] arrC = new int[lc];
        for (int i = 0; i < lc; i++) {//复制前子序列arrC
            arrC[i] = arr[mi + i];
        }
        //i - arr; j - arrB; k - arrC
        for (int i = 0, j = 0, k = 0; (j < lb || k < lc); ) {//arrB[j]和arrC[k]中小的转至arrA的末尾
            if (j < lb && (lc <= k || arrB[j] <= arrC[k])) //j < lb arrB未越界, lc <= k  arrC已经越界, 哨兵节点模式,认为arrC尾部有个正无穷(+∞)的哨兵
                arr[lo + i++] = arrB[j++];
            if (k < lc && (lb <= j || arrC[k] < arrB[j]))
                arr[lo + i++] = arrC[k++];
        }
    }

    //arrC 不需要复制,让它留在arr里面
    private static void merge2(int[] arr, int lo, int mi, int hi) {
        int lb = mi - lo;//前缀的长度
        int[] arrB = new int[lb];
        //不友好
        // for (int i = 0; i < lb; arrB[i] = arr[i++]) ;//复制前子序列arrB
        for (int i = 0; i < lb; i++) {//复制前子序列arrB
            arrB[i] = arr[lo + i];
        }

        int lc = hi - mi;

        //i - arr; j - arrB; k - arrC
        for (int i = 0, j = 0, k = 0; j < lb; ) {//arrB[j]和arrC[k]中小的转至arrA的末尾
            if (k < lc && arr[mi + k] < arrB[j])//如果不交换两个if,j会越界
                arr[lo + i++] = arr[mi + k++];
            if (lc <= k || arrB[j] <= arr[mi + k]) //lc <= k arrC 放完了
                arr[lo + i++] = arrB[j++];

        }
    }

}
