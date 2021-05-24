# 1589. Maximum Sum Obtained of Any Permutation

We have an array of integers, `nums`, and an array of `requests` where `requests[i] = [starti, endi]`. The `ith` request asks for the sum of `nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi]`. Both `starti` and `endi` are *0-indexed*.

Return *the maximum total sum of all requests **among all permutations** of* `nums`.

Since the answer may be too large, return it **modulo** `109 + 7`.

 

**Example:**

```
Input: nums = [1,2,3,4,5], requests = [[1,3],[0,1]]
Output: 19
Explanation: One permutation of nums is [2,1,3,4,5] with the following result: 
requests[0] -> nums[1] + nums[2] + nums[3] = 1 + 3 + 4 = 8
requests[1] -> nums[0] + nums[1] = 2 + 1 = 3
Total sum: 8 + 3 = 11.
A permutation with a higher total sum is [3,5,4,2,1] with the following result:
requests[0] -> nums[1] + nums[2] + nums[3] = 5 + 4 + 2 = 11
requests[1] -> nums[0] + nums[1] = 3 + 5  = 8
Total sum: 11 + 8 = 19, which is the best that you can do.
```

## 差分
```java
class Solution {
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int len = nums.length;
        int[] freq = new int[len];
        for (int[] r : requests) {
            freq[r[0]]++;
            if (r[1]+1 < len) freq[r[1]+1]--;
        }
        
        for (int i = 1; i < len; i++) freq[i] += freq[i-1];
        
        Arrays.sort(nums); Arrays.sort(freq);
        long res = 0;
        for (int i = len-1; i >= 0 && freq[i] > 0; i--) {
            res += (long)nums[i]*freq[i];
        }
        return (int) (res % 1000000007);
    }
}
```



## 线段树

```java
class Solution {
    int N, maxN = 100010;
    Node[] segs = new Node[4*maxN];

    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        N = nums.length;

        build(1, 1, N);
        for (int[] r : requests) lazy_update(1, r[0]+1, r[1]+1, 1);
        int[] cout = new int[N];
        for (int i = 0; i < N; i++) cout[i] = query(1, i+1, i+1);

        Arrays.sort(cout); Arrays.sort(nums);
        long res = 0;
        for (int i = 0; i < N; i++) {
            res += ((long)nums[i]*cout[i]) % 1000000007;
        }
        return (int) (res % 1000000007);
    }

    void build(int root, int le, int ri) {
        if (le == ri) segs[root] = new Node(le, ri);
        else {
            segs[root] = new Node(le, ri);
            int mid = le+ri>>1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);
            push_up(root);
        }
    }

    int query(int root, int start, int end) {
        if (start > segs[root].ri || end < segs[root].le) return 0;
        if (start <= segs[root].le && segs[root].ri <= end) {
            return segs[root].sum;
        }

        push_down(root);
        int res = 0;
        res += query(root<<1, start, end);
        res += query(root<<1|1, start, end);
        return res;
    }

    void update(int root, int idx, int val) {
        if (idx > segs[root].ri || idx < segs[root].le) return;
        if (segs[root].le == segs[root].ri) {
            segs[root].sum += val;  // 目标节点
        } else {
            update(root<<1, idx, val);    // 如果 需要update的节点在左子节点
            update(root<<1|1, idx, val);  // 否则 需要update的节点在右子节点
            push_up(root);
        }
    }

    void lazy_update(int root, int start, int end, int val) {
        if (start > segs[root].ri || end < segs[root].le) return;
        if (start <= segs[root].le && segs[root].ri <= end) {
            segs[root].sum += (long) (segs[root].ri-segs[root].le+1) * val;
            segs[root].tag += val;
        } else {
            push_down(root);

            lazy_update(root<<1, start, end, val);
            lazy_update(root<<1|1, start, end, val);
            push_up(root);
        }
    }

    void push_up(int root) {
        segs[root].sum = segs[root<<1].sum + segs[root<<1|1].sum;
    }

    void push_down(int root) {
        if (segs[root].tag != 0) {
            int mid = segs[root].le+segs[root].ri >> 1;
            segs[root<<1].sum += segs[root].tag * (mid - segs[root].le+1);
            segs[root<<1|1].sum += segs[root].tag * (segs[root].ri - mid);

            segs[root<<1].tag += segs[root].tag;
            segs[root<<1|1].tag += segs[root].tag;
            segs[root].tag = 0;
        }
    }

    static class Node {
        int le, ri, sum, tag;
        public Node(int l, int r) {
            le = l; ri = r;
        }
    }
}
```

