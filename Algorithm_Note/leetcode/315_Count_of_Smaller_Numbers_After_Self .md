# 315. 计算右侧小于当前元素的个数

给定一个整数数组 *nums*，按要求返回一个新数组 *counts*。数组 *counts* 有该性质： `counts[i]` 的值是 `nums[i]` 右侧小于 `nums[i]` 的元素的数量。 

**示例：**

```
输入：nums = [5,2,6,1]
输出：[2,1,1,0] 
解释：
5 的右侧有 2 个更小的元素 (2 和 1)
2 的右侧仅有 1 个更小的元素 (1)
6 的右侧有 1 个更小的元素 (1)
1 的右侧有 0 个更小的元素
```



## 树状数组

```java
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        if (nums.length == 0) return Collections.emptyList();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int val : nums) {
            if (val < min) min = val;
            if (val > max) max = val;
        }

        for (int i = 0; i < nums.length; i++) nums[i] -= min;

        int[] tr = new int[max - min + 1];
        Integer[] res = new Integer[nums.length];
        for (int i = nums.length-1; i >= 0; i--) {
            res[i] = getSum(nums[i], tr);
            update(nums[i] + 1, tr);    // 比当前数大的 数之后如果被查询，右侧小于自己的数量应该 加一
        }
        return Arrays.asList(res);
    }

    int getSum(int val, int[] tr) {
        int res = 0;
        for ( ; val > 0; val-=val&-val) res += tr[val];
        return res;
    }

    void update(int val, int[] tr) {
        for ( ; val < tr.length; val+=val&-val) tr[val] += 1;
    }
}
```

树状数组+离散化

```java
class Solution {
    int[] tr, arr;

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        discretization(nums);
        tr = new int[nums.length];
        for (int i = nums.length-1; i >= 0; i--) {
            int id = Arrays.binarySearch(arr, nums[i]) + 1;
            res.add(query(id-1));
            update(id);
        }

        Collections.reverse(res);
        return res;
    }

    private void discretization(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);
        arr = new int[set.size()];
        int idx = 0;
        for (int n : set) arr[idx++] = n;
        Arrays.sort(arr);
    }

    private void update(int idx) {
        for ( ; idx < tr.length; idx += lowBit(idx)) tr[idx] += 1;
    }

    private int query(int idx) {
        int res = 0;
        for ( ; idx > 0; idx -= lowBit(idx)) res += tr[idx];
        return res;
    }

    private int lowBit(int x) {
        return x & (-x);
    }
}
```





## 归并排序

同样可以使用 快速排序，但快速排序最坏时间复杂度位O(n^2)

```java
class Solution {
    int[] idx, tmp, tmpIdx, res;

    public List<Integer> countSmaller(int[] nums) {
        idx = new int[nums.length];
        res = new int[nums.length];

        for (int i = 0; i < nums.length; ++i) idx[i] = i;
        int le = 0, ri = nums.length - 1;
        mergeSort(nums, le, ri);

        List<Integer> list = new ArrayList<>();
        for (int num : res) list.add(num);
        return list;
    }

    void mergeSort(int[] arr, int le, int ri) {
        if (le >= ri) return;

        int mid = (le + ri) >> 1;
        mergeSort(arr, le, mid);
        mergeSort(arr, mid+1, ri);

        merge(arr, le, mid, ri);
    }

    void merge(int[] arr, int le, int mid, int ri) {
        int i = le, j = mid+1, p = le;
        while (i <= mid && j <= ri) {
            if (arr[i] <= arr[j]) {
                tmp[p] = arr[i];
                tmpIdx[p] = idx[i];
                res[idx[i]] += (j - mid - 1);  // 计算右侧小于当前元素的个数
                i++;
            } else {
                tmp[p] = arr[j];
                tmpIdx[p] = idx[j];
                j++;
            }
            p++;
        }
        while (i <= mid)  {
            tmp[p] = arr[i];
            tmpIdx[p] = idx[i];
            res[idx[i]] += (ri+1 - mid - 1);  // 计算右侧小于当前元素的个数
            i++; p++;
        }
        while (j <= ri) {
            tmp[p] = arr[j];
            tmpIdx[p] = idx[j];
            j++; p++;
        }

        for (int k = le; k <= ri; ++k) {
            arr[k] = tmp[k];
            idx[k] = tmpIdx[k];
        }
    }
}
```



## 线段树

```java
import java.util.*;

class Solution {
    Node[] tr = new Node[4*20010];

    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> res = new LinkedList<>();
        int N = nums.length; if (N == 0) return res;

        int st = nums[0], ed = nums[0]; // 获取区间范围
        for (int n : nums) {
            if (n < st) st = n;
            if (n > ed) ed = n;
        }
        st += 10000; ed += 10000;
        build(1, st, ed);
        for (int i = N-1; i >= 0; i--) {
            res.addFirst(query(1, st, nums[i]-1+10000));
            update(1, nums[i]+10000, 1);
        }
        return res;
    }

    void build(int u, int le, int ri) {
        if (le == ri) tr[u] = new Node(le, ri);
        else {
            tr[u] = new Node(le, ri);
            int mid = le+ri>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);
        }
    }

    int query(int u, int st, int ed) {
        if (st > tr[u].ri || ed < tr[u].le) return 0;
        if (st <= tr[u].le && tr[u].ri <= ed) return tr[u].sum;

        push_down(u);
        int res = 0;
        res += query(u<<1, st, ed);
        res += query(u<<1|1, st, ed);
        return res;
    }

    void update(int u, int idx, int val) {
        if (idx > tr[u].ri || idx < tr[u].le) return;
        if (tr[u].le == tr[u].ri) tr[u].sum += val;
        else {
            update(u<<1, idx, val);
            update(u<<1|1, idx, val);
            push_up(u);
        }
    }

    void update_lazy(int u, int st, int ed, int val) {
        if (st > tr[u].ri || ed < tr[u].le) return;
        if (st <= tr[u].le && tr[u].ri <= ed) {
            tr[u].sum += (tr[u].ri-tr[u].le+1) * val;
            tr[u].tag += val;
        } else {
            push_down(u);
            update_lazy(u<<1, st, ed, 1);
            update_lazy(u<<1|1, st, ed, 1);
            push_up(u);
        }
    }

    void push_up(int u) {
        tr[u].sum = tr[u<<1].sum + tr[u<<1|1].sum;
    }

    void push_down(int u) {
        if (tr[u].tag == 0) return;
        int mid = tr[u].le + tr[u].ri >> 1;
        tr[u>>1].sum += tr[u].tag * (mid - tr[u].le + 1);
        tr[u>>1|1].sum += tr[u].tag * (tr[u].ri - mid);

        tr[u>>1].tag += tr[u].tag;
        tr[u>>1|1].tag += tr[u].tag;
        tr[u].tag = 0;
    }

    static class Node {
        int le, ri, sum, tag;
        public Node(int l, int r) {
            le = l; ri = r;
        }
    }
}
```



## 平衡树

```java
import java.util.*;

class Solution {
    int maxN = 20010, INF = 0x3f3f3f3f;
    Node root = null;

    public List<Integer> countSmaller(int[] nums) {
        LinkedList<Integer> res = new LinkedList<>();

        build();
        for (int i = nums.length-1; i >= 0; i--) {
            res.addFirst(getRangByKey(root, nums[i]-1)-1);
            root = insert(root, nums[i]);
        }
        return res;
    }

    // 插入数值 x
    Node insert(Node u, int x) {
        if (u == null) return new Node(x, getRand());
        if (u.val == x) {
            u.cnt++;
        } else if (x < u.val) {
            u.le = insert(u.le, x);
            if (u.le.rand > u.rand) u = riRotate(u);  // 左大右旋
        } else if (x > u.val) {
            u.ri = insert(u.ri, x);
            if (u.ri.rand > u.rand) u = leRotate(u);  // 右大左旋
        }
        update(u);
        return u;
    }

    // 删除数值 x(若有多个相同的数，只删除一个)
    Node remove(Node u, int x) {
        if (u == null) return null;
        if (x == u.val) {
            if (u.cnt > 1) u.cnt--;
            else {
                if (u.le == null && u.ri == null) {  // 唯一删除代码
                    return null;
                } else if (u.ri == null) {  // 右无右旋
                    u = riRotate(u);  // 可能还会进行多次旋转
                    u.ri = remove(u.ri, x);
                } else {
                    u = leRotate(u);  // 可能还会进行多次旋转
                    u.le = remove(u.le, x);
                }
            }
        } else if (x < u.val) {
            u.le = remove(u.le, x);
        } else if (x > u.val) {
            u.ri = remove(u.ri, x);
        }

        update(u);
        return u;
    }

    // 查询数值 x 的排名(若有多个相同的数，输出最小的排名)
    int getRangByKey(Node u, int x) {
        if (u == null) return 0;

        int lsize = u.le==null ? 0 : u.le.size;
        if (x < u.val) return getRangByKey(u.le, x);
        if (x > u.val) return lsize + u.cnt + getRangByKey(u.ri, x);
        return lsize;
    }

    // 查询排名为 rank 的数值
    int getKeyByRang(Node u, int rank) {
        if (u == null) return INF;
        int lsize = u.le==null ? 0 : u.le.size;

        if (lsize >= rank) return getKeyByRang(u.le, rank);
        if (lsize+u.cnt >= rank) return u.val;  // l<r && r<=l+c
        return getKeyByRang(u.ri, rank - lsize - u.cnt);
    }

    // 求数值 x 的前驱数值(前驱定义为小于 x 的最大值)
    int getPre(Node u, int x) {
        if (u == null) return -INF;
        if (u.val >= x) return getPre(u.le, x);
        else return Math.max(u.val, getPre(u.ri, x));
    }

    // 求数值 x 的后继数值(后继定义为大于 x 的最小值)
    int getNext(Node u, int x) {
        if (u == null) return INF;
        if (u.val <= x) return getNext(u.ri, x);
        else return Math.min(u.val, getNext(u.le, x));
    }

    Node riRotate(Node node) {
        Node tmp = node.le;
        node.le = tmp.ri;
        tmp.ri = node;

        update(tmp.ri);  // == pushup(node)
        update(tmp);
        return tmp;
    }

    Node leRotate(Node node) {
        Node tmp = node.ri;
        node.ri = tmp.le;
        tmp.le = node;

        update(tmp.le);  // == pushup(node)
        update(tmp);
        return tmp;
    }

    void update(Node u) {
        u.size = u.cnt;
        if (u.le != null) u.size += u.le.size;
        if (u.ri != null) u.size += u.ri.size;
    }

    void build() {
        root = new Node(-INF, getRand());
        root.ri = new Node(INF, getRand());
        update(root);
    }

    int getRand() {
        return new Random().nextInt(2*maxN);
    }

    static class Node {
        int val, rand, cnt, size;
        Node le, ri;

        public Node(int v, int r) {
            val = v; rand = r;
            cnt = size = 1;
        }
    }
}
```

