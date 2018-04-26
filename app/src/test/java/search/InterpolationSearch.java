package search;


/**
 * 插值查找
 * 比如在字典, 均匀独立分布, 从lo 到 hi 均匀取值, 比如 有50页a 52页b, ... 60页z
 * 秩的比和数值比是接近的.
 *  mi - lo       e - arr[lo]
 * --------- ~ -------------------
 *  hi - lo     arr[hi] - arr[lo]
 * 可以用在大致范围定lo hi
 * 由上式求mi
 *
 */
public class InterpolationSearch {
}
