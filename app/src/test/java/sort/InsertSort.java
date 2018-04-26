package sort;

/**
 * 针对列表会比针对向量(数组)好
 * 方案是:对于未排序数据, 在已排序序列中从后向前扫描, 找到相应位置并插入.
 * 平均情况也是O(n2)
 * 从逆序对的角度看,时间复杂度是和输入有关的
 * 是输入敏感(input sensitive)的
 */
public class InsertSort {
}
