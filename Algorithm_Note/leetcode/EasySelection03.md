# 面试题 02.07. 链表相交

给定两个（单向）链表，判定它们是否相交并返回交点。请注意相交的定义基于节点的引用，而不是基于节点的值。换句话说，如果一个链表的第k个节点与另一个链表的第j个节点是同一节点（引用完全相同），则这两个链表相交。

**示例：**

```
输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
输出：Reference of the node with value = 8
输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
```

```c
// 借鉴 判断链表是否有环
struct ListNode *getIntersectionNode(struct ListNode *headA, struct ListNode *headB) {
   struct ListNode *p=headA, *q=headB;
   while (p != q) {
       p = p==NULL ? headB : p->next;
       q = q==NULL ? headA : q->next;
   }
   return q;
}
```

# 剑指 Offer 03. 数组中重复的数字
在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

**示例：**

```
输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3 
```

```c++
class SolutionBinary {
public:
    int findRepeatNumber(vector<int> &nums) {
        if (nums.empty()) return -1;
        int dow = 0, top = nums.size() - 1;
        while (top >= dow) {
            int mid = (top+dow) >> 1;
            int count = countRange(nums, dow, mid);
            if (top == dow) {
                if (count >= 2) return dow;
                else break;
            }
            if (count > (mid - dow + 1)) {
                top = mid;
            } else {
                dow = mid + 1;
            }
        }
        return -1;
    }

    int countRange(vector<int> &nums, int dow, int top) {
        if (nums.empty()) return -1;
        int count = 0;
        for (int n : nums) {
            if (n >= dow && n <= top) count++;
        }
        return count;
    }
};
```

```java
如果没有重复数字，那么正常排序后，数字i应该在下标为i的位置，所以思路是重头扫描数组，遇到下标为i的数字如果不是i的话，（假设为m),那么我们就拿与下标m的数字交换。在交换过程中，如果有重复的数字发生，那么终止返回ture。
class Solution {
    public int findRepeatNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    return nums[i];
                }
                int tmp = nums[i];
                nums[i] = nums[tmp];
                nums[tmp] = tmp;
            }
        }
        return -1;
    }
}
```

# 面试题 05.03. 翻转数位

给定一个32位整数 `num`，你可以将一个数位从0变为1。请编写一个程序，找出你能够获得的最长的一串1的长度。

**示例 1：**

```
输入: num = 1775(110111011112)
输出: 8
```

**示例 2：**

```
输入: num = -1
输出: 32
```

```c
int reverseBits(int num){
    int cur = 0, pre = 0, res = 0;
    for (int i = 0; i < 32; i++) {
        if (!(num&1)) {
            cur -= pre;
            pre = cur + 1;
        }
        cur++;
        res = fmax(res, cur);
        num >>= 1;
    }
    return res;
}
```

```c
int reverseBits(int num){
    int le = 0, ri = 0, res = 0;
    for (int i = 0; i < 32; i++) {
        if (num&1) ri++; 
        else le = ri+1, ri = 0;
        res = fmax(le+ri, res); 
        num >>= 1;
    }
    return res;
}
```

```java
class Solution {
    public int reverseBits(int num) {
        int cnt = 0, res = 0;
        int[] le = new int[32], ri = new int[32];

        for (int i = 0; i < 32; i++) {
            int cur = num >>> i;
            if ((cur & 1) == 1) cnt ++;
            else {
                ri[i] = cnt;
                cnt = 0;
            }
            res = Math.max(res, cnt);
        }

        cnt = 0;
        for (int i = 31; i >= 0; i--) {
            int cur = num >>> i;
            
            if ((cur&1) == 1) cnt ++;
            else {
                le[i] = cnt;
                cnt = 0;
            }
            res = Math.max(res, cnt);
        }

        for (int i = 0; i < 32; i++) {
            res = Math.max(res, le[i] + ri[i] + 1);
        }
        return res;
    }
}
```

