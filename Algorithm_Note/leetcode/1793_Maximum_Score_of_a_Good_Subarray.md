# 1793. 好子数组的最大分数

给你一个整数数组 `nums` **（下标从 0 开始）**和一个整数 `k` 。

一个子数组 `(i, j)` 的 **分数** 定义为 `min(nums[i], nums[i+1], ..., nums[j]) * (j - i + 1)` 。一个 **好** 子数组的两个端点下标需要满足 `i <= k <= j` 。

请你返回 **好** 子数组的最大可能 **分数** 。

 

**示例 1：**

```
输入：nums = [1,4,3,7,4,5], k = 3
输出：15
解释：最优子数组的左右端点下标是 (1, 5) ，分数为 min(4,3,7,4,5) * (5-1+1) = 3 * 5 = 15 。
```

**示例 2：**

```
输入：nums = [5,5,4,5,4,1,1,1], k = 0
输出：20
解释：最优子数组的左右端点下标是 (0, 4) ，分数为 min(5,5,4,5,4) * (4-0+1) = 4 * 5 = 20 。
```



## 双指针

```java
class Solution {
    public int maximumScore(int[] nums, int k) {
        int le = k, ri = k, N = nums.length;
        int res = 0, cur = nums[k];
        while (le>=0 || ri < N) {
            while (le >= 0 && cur<=nums[le]) le--;
            while (ri < N  && cur<=nums[ri]) ri++;

            res = Math.max(res, cur * (ri-le-1));

            if (le >= 0 && ri < N) cur = Math.max(nums[le], nums[ri]);
            else if (le >= 0) cur = nums[le];
            else if (ri < N) cur = nums[ri];
        }
        return res;
    }
}
```



```java
class Solution {
    public int maximumScore(int[] nums, int k) {
        int le = k, ri = k, res = 0;
        for (int val = nums[k]; val > 0; val--) {
            while (le-1 >= 0 && val <= nums[le-1]) le--;
            while (ri+1 < nums.length && val <= nums[ri + 1]) ri++;
            res = Math.max(res, val * (ri-le+1));
        }
        return res;
    }
}
```



## 线段树

```java
class Solution {
    public int maximumScore(int[] nums, int k) {
        Segment_tree st = new Segment_tree(nums);  // min tree
        int res = 0, le = 0, ri = nums.length-1;
        while (le <= k && ri >= k) {
            int mi = st.query(1, le+1, ri+1);
            res = Math.max(res, mi * (ri-le+1));
            int lmi = st.query(1, le+1, k+1);
            if (lmi == mi) {
                if (le == k) return res;
                while (le < k && nums[le] != mi) le++;
                if (le < k) le++;
            } else {
                if (ri == k) return res;
                while (ri > k && nums[ri] != mi) ri--;
                if (ri > k) ri--;
            }
        }
        return res;
    }

}
class Segment_tree {
    int maxN = 100010;
    int[] arr = new int[maxN];
    Node[] segs = new Node[4*maxN];

    public Segment_tree(int[] nums) {
        System.arraycopy(nums, 0, arr, 1, nums.length);
        build(1, 1, nums.length+1);
    }

    void build(int root, int le, int ri) {
        if (le == ri) {
            segs[root] = new Node(le, ri, arr[le]);
        } else {
            segs[root] = new Node(le, ri, 0);

            int mid = le+ri >> 1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);

            segs[root].min = Math.min(segs[root<<1].min, segs[root<<1|1].min);
        }
    }

    int query(int root, int start, int end) {
        if (start <= segs[root].le && segs[root].ri <= end) return segs[root].min;

        int res = Integer.MAX_VALUE;
        int mid = (segs[root].le + segs[root].ri) >> 1;
        if (start <= mid) res = Math.min(res, query(root<<1, start, end));
        if (end > mid) res = Math.min(res, query((root<<1)|1, start, end));
        return res;
    }

    static class Node {
        int le, ri, min;
        public Node(int l, int r, int s) {
            le = l; ri = r; min = s;
        }
    }
}
```

