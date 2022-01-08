# 167. Two Sum II - Input Array Is Sorted

Given a **1-indexed** array of integers `numbers` that is already ***sorted in non-decreasing order\***, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return *the indices of the two numbers,* `index1` *and* `index2`*, **added by one** as an integer array* `[index1, index2]` *of length 2.*

The tests are generated such that there is **exactly one solution**. You **may not** use the same element twice.

 

**Example 1:**

```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Example 2:**

```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
```



## 2 Points

```go
func twoSum(numbers []int, target int) []int {
    lo, hi := 0, len(numbers) - 1
    for lo < hi {
        sum := numbers[lo] + numbers[hi]
        if sum == target {
            return []int{lo + 1, hi + 1}
        } else if sum < target {
            lo++
        } else {
            hi--
        }
    }
    return []int{-1, -1}
}
```



## Binary Search

```go
func twoSum(arr []int, tar int) []int {
    for i := 0; i < len(arr); i++ {
        lo, hi := i + 1, len(arr) - 1
        for lo <= hi {
            mid := (hi - lo) / 2 + lo
            if arr[mid] == tar - arr[i] {
                return []int{i + 1, mid + 1}
            } else if arr[mid] > tar - arr[i] {
                hi = mid - 1
            } else {
                lo = mid + 1
            }
        }
    }
    return []int{-1, -1}
}
```

