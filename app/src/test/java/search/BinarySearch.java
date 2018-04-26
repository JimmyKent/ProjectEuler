package search;

import org.junit.Test;


/**
 * 基于有序向量
 * 可能存在多个符合条件的
 * insert基于search, insert(search(e) + 1, e), 即便查找失败,也应该给出新元素插入的恰当位置.
 * 若允许元素重复, 每一组也应该按插入次序排列. 也就是说查找返回的是不大于当前元素e的最后一个元素.
 *
 * O(log n)
 */
public class BinarySearch {

    @Test
    public void main() {

        //int[] arr = {1, 1, 1, 2, 4, 4, 4, 4, 4, 4, 5, 5, 6, 7};
        int[] arr = {1, 2, 3, 5};
        int e = 5;
        int result;
        result = binarySearchA(arr, e, 0, arr.length  );
        System.out.println(result);

        result = binarySearchB(arr, e, 0, arr.length );
        System.out.println(result);

        result = binarySearchC(arr, e, 0, arr.length  );
        System.out.println(result);
    }

    //左闭右开 [lo, hi) 所以传入的需要是length 而不是 length - 1
    private static int binarySearchA(int[] arr, int e, int lo, int hi) {
        while (lo < hi) {
            int mid = (hi + lo) >> 1;
            if (arr[mid] < e) {
                lo = mid + 1;
            } else if (arr[mid] > e) {
                hi = mid;
            } else {
                return mid;

            }
        }
        return -1;
    }


    //e需要严格小于轴点x
    //性能更加稳定, 最好更差, 最差更好
    private static int binarySearchB(int[] arr, int e, int lo, int hi) {
        while (hi - lo > 1) {
            int mid = (hi + lo) >> 1;
            if (e < arr[mid])
                hi = mid; // [lo, mi)
            else
                lo = mid; // [mi, hi)
        }
        return e == arr[lo] ? lo : -1;
    }

    //最后会压缩为lo == hi, 左侧是全部不大于e的数值,右侧是全部大于e的数值.
    private static int binarySearchC(int[] arr, int e, int lo, int hi) {
        while (lo < hi) {
            int mid = (hi + lo) >> 1;
            if (e < arr[mid])
                hi = mid; // [lo, mi)
            else
                lo = mid + 1; // (mi, hi)
        }
        return --lo;
    }
}
