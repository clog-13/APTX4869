# 剑指 Offer 51. 数组中的逆序对

在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。

**示例:**
```
输入: [7,5,6,4]
输出: 5
```



## 树状数组+离散化

```java
class Solution {
    public int reversePairs(int[] nums) {
        int N = nums.length;
        int[] arr = new int[N];
        System.arraycopy(nums, 0, arr, 0, N);
        Arrays.sort(arr);
        for (int i = 0; i < N; ++i) {
            nums[i] = Arrays.binarySearch(arr, nums[i]) + 1;
        }

        BIT bit = new BIT(N);
        int res = 0;
        for (int i = N - 1; i >= 0; i--) {
            res += bit.query(nums[i] - 1);
            bit.update(nums[i]);
        }
        return res;
    }
}

class BIT {
    int[] tree;
    int N;

    public BIT(int N) {
        this.N = N;
        this.tree = new int[N + 1];
    }

    public int query(int x) {
        int res = 0;
        while (x != 0) {
            res += tree[x];
            x -= lowbit(x);
        }
        return res;
    }

    public void update(int x) {
        while (x <= N) {
            tree[x]++;
            x += lowbit(x);
        }
    }
    
    public static int lowbit(int x) {
        return x & (-x);
    }
}
```



## 归并排序

```java
class Solution {
    public int reversePairs(int[] nums) {
        return partition(nums, Arrays.copyOf(nums, nums.length), 0, nums.length);
    }

    public int partition(int[] arr, int[] tmp, int le, int ri) {
        if (le+1 >= ri) return 0;
        int mid = (le+ri) >> 1;
        // !!! 最后一次merge的时候由tmp merge到 arr,上一层就应该是arr merge到 tmp 
        return partition(tmp, arr, le, mid) + partition(tmp, arr, mid, ri) + merge(arr, tmp, le, mid, ri);
    }

    public int merge(int[] arr, int[] tmp, int le, int mid, int ri) {
        int i = le, j = mid, k = le, res = 0;
        while (true) {
            if (arr[i] <= arr[j]) {
                res += j - mid;
                tmp[k++] = arr[i++];
                if (i == mid) {
                    System.arraycopy(arr, j, tmp, k, ri - j);
                    return res;
                }
            } else {
                tmp[k++] = arr[j++];
                if (j == ri) {
                    res += (mid - i) * (j - mid);
                    System.arraycopy(arr, i, tmp, k, mid - i);
                    return res;
                }
            }
        }
    }
}
```



```java
class Solution {
    int[] arr;
    int res = 0;
    public int reversePairs(int[] nums) {
        arr = new int[nums.length];
        System.arraycopy(nums, 0, arr, 0, nums.length);
        
        int le = 0, ri = nums.length-1;
        mergeSort(le, ri);
        return res;
    }

    private void mergeSort(int le, int ri) {
        if (le >= ri) return;

        int mid = (le+ri) >> 1;
        mergeSort(le, mid);
        mergeSort(mid+1, ri);

        merge(le, ri);
    }

    private void merge(int le, int ri) {
        int[] temp = new int[ri-le+1];
        int mid = (le + ri) >> 1;
        int i = le, j = mid+1, t = 0;
        while (i <= mid && j <= ri) {
            if (arr[i] <= arr[j]) {
                res += (j-mid-1);

                temp[t] = arr[i];
                i++; t++;
            } else {
                temp[t] = arr[j];
                j++; t++;
            }
        }
        while (i <= mid) {
            res += (ri-mid);
            temp[t] = arr[i];
            i++; t++;
        }
        while (j <= ri) {
            temp[t] = arr[j];
            j++; t++;
        }
        for (i = 0; i < temp.length; i++) {
            arr[le+i] = temp[i];
        }        
    }
}
```



## 快速排序

```java
class Solution {
    int res = 0;
    public int reversePairs(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int n : nums) list.add(n);
        return quickSort(list);
    }

    public int quickSort(List<Integer> list) {
        if (list.size() == 0) return 0;
        int le = 0, ri = list.size()-1, res = 0;
        List<Integer> riList = new ArrayList<>(), leList = new ArrayList<>();
        int idx = new Random().nextInt(list.size());
        for (int i = 0; i < list.size(); i++) {
            if (i == idx) continue;
            else if (list.get(i) <= list.get(idx)) {
                riList.add(list.get(i));
                res += leList.size();	// 之前 "以 idx 为小的逆序对"的大值 以当前i为小的逆序对
                if (list.get(i)<list.get(idx) && i>idx) res++;	// 以 idx 为大的逆序对
            } else {
                leList.add(list.get(i));	
                if (i < idx) res++;	// 以 idx 为小的逆序对
            }
        }
        res += quickSort(leList);
        res += quickSort(riList);
        return res;
    }
}
```





## 树状数组(RE)

错误样例：[2147483647,-2147483647]

```java
class Solution {
    int[] BITree;

    public int reversePairs(int[] nums) {
        if (nums.length == 0) return 0;
        long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        for (int n : nums) {
            if (n < min) min = n;
            if (n > max) max = n;
        }
        for (int i = 0; i < nums.length; i++) nums[i] -= min;
        BITree = new int[max-min+1];
        int res = 0;
        for (int i = nums.length-1; i >= 0; i--) {
            res += query(nums[i]);
            update(nums[i]+1);
        }
        return res;
    }

    private void update(int idx) {
        while (idx < BITree.length) {
            BITree[idx]++;
            idx += (idx & -idx); 
        }
    }

    private int query(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += BITree[idx];
            idx &= (idx-1);
        }
        return sum;
    }
}
```



## 二叉搜索树(TLE)

```java
class Solution {
    int res = 0;
    public int reversePairs(int[] nums) {
        TreeNode root = null;
        for (int i = nums.length-1; i >= 0; i--)
            root = build(root, new TreeNode(nums[i]));
        return res;
    }

    private TreeNode build(TreeNode root, TreeNode node) {
        if (root == null) {
            root = node; return root;
        }

        if (node.val <= root.val) {
            root.cnt++;
            root.le = build(root.le, node);
        } else {
            res += root.cnt+1;
            root.ri = build(root.ri, node); 
        }
        return root;
    }
    private static class TreeNode {
        int val, cnt;
        TreeNode le, ri;

        public TreeNode(int val) {
            this.val = val; this.cnt = 0;
            le = null; ri = null;
        }
    }
}
```

