package sort;

import org.junit.Test;

/**
 * 分治策略,前一序列数值上, 不得超过后一序列任意元素的大小
 * 核心在于子序列的划分
 * 有多种实现
 * <p>
 * 快速排序不是一种稳定的排序算法，也就是说，多个相同的值的相对位置也许会在算法结束时产生变动。
 * 不稳定 5a 5b,可能会变成 5b 5a.
 * //稳定性: 不改变相同数相对位置
 */
public class QuickSort {

    @Test
    public void main() {

        //int[] arr = new int[]{1, 4, 8, 2, 55, 3, 4, 8, 6, 4, 0, 11, 34, 90, 23, 54, 77, 9, 2, 9, 4, 10};
        int[] arr = new int[]{6,2,7,3,8};
        quickSortA(arr, 0, arr.length - 1);
        String out = "";
        for (int digit : arr) {
            out += (digit + ",");
        }
        System.out.println(out);

    }

    /**
     * L U G 的顺序
     * L 恒比pivot小
     * G 恒比pivot大
     * U 剩下未比较的
     * //版本A:基本形式
     */
    public static void quickSortA(int[] arr, int lo, int hi) {
        if (hi - lo < 2)//单元素区间自然有序，否则...
            return;
        int mi = partitionC(arr, lo, hi - 1);//在[lo, hi - 1]内极造轴点
        quickSortA(arr, lo, mi);//对前缀递归排序
        quickSortA(arr, mi + 1, hi);//对后缀递归排序
    }

    //勤于拓展、懒于交换 LUG
    private static int partition(int[] arr, int lo, int hi) {//轴点极造算法:通过调整元素位置构造区间[lo, hi]的轴点，返回轴点位置

        //任选一个元素与首元素交换
        int pivot = arr[lo];//以首元素为候选轴点——经以上交换，等效于随机选取
        while (lo < hi) {//从向量的两端交替地向中间扫描
            //循环开始, 是左边先空了一个位置出来,放到了pivot里面
            while (lo < hi && pivot < arr[hi]) {//在不小于pivot的前提下
                hi--;//向左拓展右端子向量
            }
            arr[lo] = arr[hi];//小于pivot者归入左侧子序列

            //找到右侧比pivot小的,放到左边空的位置,此时右边开始空出位置

            while (lo < hi && arr[lo] <= pivot) {//在不大于pivot的前提下
                lo++;//向右拓展左端子向量
            }
            arr[hi] = arr[lo];//大亍pivot者弻入右侧子序列
        }
        arr[lo] = pivot;//将备份的轴点记录置于前、后子向量之间
        return lo;//返回轴点的位置
    }

    //懒于拓展、勤于交换
    public static int partitionB(int[] arr, int lo, int hi) {//轴点极造算法:通过调整元素位置构造区间[lo, hi]的轴点，返回轴点位置

        //XXX 任选一个元素与首元素交换
        int pivot = arr[lo];//以首元素为候选轴点——经以上交换，等效于随机选取
        while (lo < hi) {//从向量的两端交替地向中间扫描
            //循环开始, 是左边先空了一个位置出来,放到了pivot里面
            while (lo < hi) {//在不小于pivot的前提下
                if (pivot < arr[hi])//在大于pivot的前提下
                    hi--;//向左拓展右端子向量
                else {//直至遇到不大于pivot者
                    arr[lo++] = arr[hi];//将其归入左端子向量
                    break;
                }
            }

            //找到右侧比pivot小的,放到左边空的位置,此时右边开始空出位置

            while (lo < hi) {
                if (arr[lo] < pivot) {//在小于pivot的前提下
                    lo++;//向右拓展左端子向量
                } else {//直至遇到不小于pivot者
                    arr[hi--] = arr[lo];//将其归入右端子向量
                    break;
                }
            }
            arr[lo] = pivot;//将备份的轴点记录置于前、后子向量之间
        }
        return lo;//返回轴点的位置


    }

    //LGU
    private static int partitionC(int[] arr, int lo, int hi) {
        //XXX 任选一个元素与首元素交换
        int pivot = arr[lo];//以首元素为候选轴点——经以上交换，等效于随机选取
        int mi = lo;
        int temp;
        for (int i = lo + 1; i <= hi; i++) {//从左往右扫描
            if (arr[i] < pivot) {//如果arr[i]小于轴点,交换,L向右拓展
                mi++;
                temp = arr[i];
                arr[i] = arr[mi];
                arr[mi] = temp;
            }
        }
        //候选轴点归位(从而名副其实), pivot对应的是mi中的值
        temp = arr[lo];
        arr[lo] = arr[mi];
        arr[mi] = temp;

        return mi;//返回轴点的位置
    }


    //from wikipedia
    public static void quickSort(int[] arr, int head, int tail) {
        if (head >= tail || arr == null || arr.length <= 1) {
            return;
        }
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {
                ++i;
            }
            while (arr[j] > pivot) {
                --j;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                ++i;
                --j;
            } else if (i == j) {
                ++i;
            }
        }
        quickSort(arr, head, j);
        quickSort(arr, i, tail);
    }


}
