package sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 每次选出最小的放到首位
 */
public class SelectionSort {

    @Test
    public void main() {
        int[] arr = {3, 2, 1, 4};
        int hi = arr.length;
        selectionSort(arr, 0, hi);
        System.out.println(Arrays.toString(arr));
    }


    private static void selectionSort(int[] arr, int lo, int hi) {

        int min, temp;

        for (int i = 0; i < hi; i++) {
            min = i;
            for (int j = i; j < hi; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;

        }

    }
}
