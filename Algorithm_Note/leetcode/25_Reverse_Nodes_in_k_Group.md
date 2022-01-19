# 25. Reverse Nodes in k-Group

Given the `head` of a linked list, reverse the nodes of the list `k` at a time, and return *the modified list*.

`k` is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of `k` then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/reverse_ex1.jpg)

```
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/10/03/reverse_ex2.jpg)

```
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
```



## Simulate

```go
func reverseKGroup(head *ListNode, k int) *ListNode {
    dummy := &ListNode{Next: head}
    pre := dummy

    for head != nil {
        tail := pre
        for i := 0; i < k; i++ {
            tail = tail.Next
            if tail == nil {
                return dummy.Next
            }
        }
        nex := tail.Next
        head, tail = myReverse(head, tail)
        pre.Next = head
        tail.Next = nex
        
        pre = tail
        head = tail.Next
    }
    return dummy.Next
}

func myReverse(head, tail *ListNode) (*ListNode, *ListNode) {
    prev := tail.Next
    cur := head
    for prev != tail {
        nex := cur.Next
        cur.Next = prev
        prev = cur
        cur = nex
    }
    return tail, head
}
```

## Iterate

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode prev = null, cur = head, next = null, check = head;
        int cnt = 0;
        while (cnt < k && check != null) {
            check = check.next;
            cnt++;
        }
        if (cnt == k) {
            cnt = 0;
            while (cnt < k && cur != null) {
                next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
                cnt++;
            }
            if (next != null) {
                head.next = reverseKGroup(next, k);
            }
            return prev;
        } else {
            return head;
        }
    }
}
```

