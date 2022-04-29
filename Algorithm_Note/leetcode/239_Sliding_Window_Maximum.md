# 239. Sliding Window Maximum

You are given an array of integers `nums`, there is a sliding window of size `k` which is moving from the very left of the array to the very right. You can only see the `k` numbers in the window. Each time the sliding window moves right by one position.

Return *the max sliding window*.

 

**Example 1:**

```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation: 
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```



## Mono Stack

```go
func maxSlidingWindow(nums []int, k int) []int {
    que := []int{}
    push := func(i int) {
        for len(que) > 0 && nums[i] >= nums[que[len(que)-1]] {
            que = que[:len(que)-1]
        }
        que = append(que, i)
    }

    for i := 0; i < k; i++ { push(i) }
    
    res := make([]int, 1, len(nums)-k+1)
    res[0] = nums[que[0]]
    for i := k; i < len(nums); i++ {
        push(i)
        for que[0] <= i-k { que = que[1:] }
        res = append(res, nums[que[0]])
    }
    return res
}
```



## PriorityQueue

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> deque = new LinkedList<Integer>();
        for (int i = 0; i < k; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int[] res = new int[n - k + 1];
        res[0] = nums[deque.peekFirst()];
        for (int i = k; i < n; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            res[i - k + 1] = nums[deque.peekFirst()];
        }
        return res;
    }
}
```

```go
import (
	"container/heap"
	"sort"
)

var arr []int
type hp struct{ sort.IntSlice }
func (h hp) Less(i, j int) bool  { return arr[h.IntSlice[i]] > arr[h.IntSlice[j]] }
func (h *hp) Push(v interface{}) { h.IntSlice = append(h.IntSlice, v.(int)) }
func (h *hp) Pop() interface{} { 
    arr := h.IntSlice
    res := arr[len(arr)-1]
    h.IntSlice = arr[:len(arr)-1]
    return res
}

func maxSlidingWindow(nums []int, k int) []int {
	arr = nums
	pq := &hp{make([]int, k)}
	for i := 0; i < k; i++ { pq.IntSlice[i] = i }
	heap.Init(pq)

	N := len(nums)
	res := make([]int, 1, N-k+1)
	res[0] = nums[pq.IntSlice[0]]
	for i := k; i < N; i++ {
		heap.Push(pq, i)
		for pq.IntSlice[0] <= i-k { heap.Pop(pq) }
		res = append(res, nums[pq.IntSlice[0]])
	}
	return res
}
```



## Spare Table

```go
func maxSlidingWindow(nums []int, k int) []int {
	N := len(nums)
	preMax, sufMax := make([]int, N), make([]int, N)
	for i, n := range nums {
		if i%k == 0 {
			preMax[i] = n
		} else {
			preMax[i] = max(preMax[i-1], n)
		}
	}
    // ...../...../...
    // .../...../.....
	for i := N - 1; i >= 0; i-- {
		if i == N-1 || (i+1)%k == 0 {  // !!!
			sufMax[i] = nums[i]
		} else {
			sufMax[i] = max(sufMax[i+1], nums[i])
		}
	}

	res := make([]int, N-k+1)
	for i := range res {
		res[i] = max(sufMax[i], preMax[i+k-1])
	}
	return res
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
```

