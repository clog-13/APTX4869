# 92. Reverse Linked List II

Given the `head` of a singly linked list and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right`, and return *the reversed list*.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev2ex2.jpg)

```
Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]
```

**Example 2:**

```
Input: head = [5], left = 1, right = 1
Output: [5]
```



## Simulation

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        
        ListNode cur = pre.next, nex;
        for (int i = 0; i < right - left; i++) {
            nex = cur.next;
            cur.next = nex.next;
            
            nex.next = pre.next;  // !!!
            pre.next = nex;       // !!!
        }

        return dummy.next;
    }
}
```



```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < left-1; i++) {
            pre = pre.next;
        }
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }
        
        ListNode leftNode = pre.next;
        ListNode tail = rightNode.next;
        pre.next = null;
        rightNode.next = null;

        reverse(leftNode);

        pre.next = rightNode;  // !!!
        leftNode.next = tail;  // !!!
        return dummy.next;
    }

    private void reverse(ListNode head) {
        ListNode pre = null, cur = head;

        while (cur != null) {
            ListNode nex = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nex;
        }
    }
}
```

