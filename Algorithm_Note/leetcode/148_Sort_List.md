#### [148. Sort List](https://leetcode-cn.com/problems/sort-list/)

难度中等1458

Given the `head` of a linked list, return *the list after sorting it in **ascending order***.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/09/14/sort_list_1.jpg)

```
Input: head = [4,2,1,3]
Output: [1,2,3,4]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/09/14/sort_list_2.jpg)

```
Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]
```



## 自顶向下归并排序

```go
func sortList(head *ListNode) *ListNode {
    return sort(head, nil)
}

func sort(head, tail *ListNode) *ListNode {
    if head == nil {
        return head
    }

    if head.Next == tail {
        head.Next = nil
        return head
    }

    slow, fast := head, head  // 快慢指针
    for fast != tail {
        slow = slow.Next
        fast = fast.Next
        if fast != tail {
            fast = fast.Next
        }
    }

    mid := slow
    return merge(sort(head, mid), sort(mid, tail))
}

func merge(head1, head2 *ListNode) *ListNode {
    dummy := &ListNode{}
    tmp, t1, t2 := dummy, head1, head2
    for t1 != nil && t2 != nil {
        if t1.Val <= t2.Val {
            tmp.Next = t1
            t1 = t1.Next
        } else {
            tmp.Next = t2
            t2 = t2.Next
        }
        tmp = tmp.Next
    }
    if t1 != nil {
        tmp.Next = t1
    } else if t2 != nil {
        tmp.Next = t2
    }
    return dummy.Next
}
```



## 自底向上归并排序

```go
func sortList(head *ListNode) *ListNode {
    if head == nil {
        return head
    }

    len := 0
    for node := head; node != nil; node = node.Next {
        len++
    }

    dummy := &ListNode{Next: head}
    for subLength := 1; subLength < len; subLength <<= 1 {
        prev, cur := dummy, dummy.Next
        for cur != nil {
            head1 := cur
            
            for i := 1; i < subLength && cur.Next != nil; i++ {
                cur = cur.Next
            }
            head2 := cur.Next
            cur.Next = nil
            cur = head2
            for i := 1; i < subLength && cur != nil && cur.Next != nil; i++ {
                cur = cur.Next
            }
            var nex *ListNode
            if cur != nil {
                nex = cur.Next
                cur.Next = nil
            }

            prev.Next = merge(head1, head2)

            for prev.Next != nil {
                prev = prev.Next
            }
            cur = nex
        }
    }
    return dummy.Next
}

func merge(head1, head2 *ListNode) *ListNode {
    dummy := &ListNode{}
    tmp, t1, t2 := dummy, head1, head2
    for t1 != nil && t2 != nil {
        if t1.Val <= t2.Val {
            tmp.Next = t1
            t1 = t1.Next
        } else {
            tmp.Next = t2
            t2 = t2.Next
        }
        tmp = tmp.Next
    }
    if t1 != nil {
        tmp.Next = t1
    } else if t2 != nil {
        tmp.Next = t2
    }
    return dummy.Next
}
```

