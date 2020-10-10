# 658. 找到 K 个最接近的元素
给定一个排序好的数组，两个整数 k 和 x，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。如果有两个数与 x 的差值一样，优先选择数值较小的那个数。

**示例:**
输入: [1,2,3,4,5], k=4, x=3
输出: [1,2,3,4]

## 二分
```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int le = 0, ri = arr.length - k;    // 注意 ri = arr.length - k;
        while (le < ri) {
            int mid = (le + ri) >> 1;
            if (x - arr[mid] > arr[mid+k] - x)
                le = mid+1;
            else
                ri = mid;
        }
        List<Integer> res = new ArrayList<>();
        for (int i = le; i < le + k; i++)
            res.add(arr[i]);
        return res;
    }
}
```

## 排除法
```java
public class Solution {

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int size = arr.length;
        int left = 0， right = size - 1;

        int removeNums = size - k;
        while (removeNums > 0) {
            if (x - arr[left] <= arr[right] - x)
                right--;
            else
                left++;
            removeNums--;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }
}
```

## 模拟
```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        Arrays.sort(arr);
        for (int i = 0; i < k; i++)
            res.add(arr[i]);

        for (int i = k; i < arr.length; i++) {
            if(Math.abs(arr[i]-x) < Math.abs(res.get(0)-x)) {
                res.remove(0);
                res.add(arr[i]);
            }
        }
        return res;
    }
}
```