# 135. Candy

There are `n` children standing in a line. Each child is assigned a rating value given in the integer array `ratings`.

You are giving candies to these children subjected to the following requirements:

- Each child must have at least one candy.
- Children with a higher rating get more candies than their neighbors.

Return *the minimum number of candies you need to have to distribute the candies to the children*.

 

**Example 1:**

```
Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
```

**Example 2:**

```
Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.
```



## IQ & 2 Loops

```go
func candy(ratings []int) (res int) {
    N := len(ratings)
    le := make([]int, N)
    for i, r := range ratings {
        if i > 0 && r > ratings[i-1] {
            le[i] = le[i-1] + 1
        } else {
            le[i] = 1
        }
    }
    ri := 0
    for i := N - 1; i >= 0; i-- {
        if i < N-1 && ratings[i] > ratings[i+1] {
            ri++
        } else {
            ri = 1
        }
        res += max(le[i], ri)
    }
    return
}

func max(a, b int) int {
    if a > b {
        return a
    }
    return b
}
```



## ST Analyse

```go
/**
如果当前同学比上一个同学评分高，说明我们就在最近的递增序列中，直接分配给该同学pre+1个糖果即可。
否则我们就在一个递减序列中，我们直接分配给当前同学一个糖果，并把该同学所在的递减序列中所有的同学都再多分配一个糖果，以保证糖果数量还是满足条件。
我们无需显式地额外分配糖果，只需要记录当前的递减序列长度，即可知道需要额外分配的糖果数量。
同时注意当当前的递减序列长度和上一个递增序列等长时，需要把最近的递增序列的最后一个同学也并进递减序列中。
*/
func candy(ratings []int) int {
    res, inc, dec, pre := 1, 1, 0, 1
    for i := 1; i < len(ratings); i++ {
        if ratings[i] >= ratings[i-1] {
            dec = 0
            if ratings[i] == ratings[i-1] {
                pre = 1
            } else {
                pre++
            }
            res += pre
            inc = pre
        } else {
            dec++
            if dec == inc {
                dec++
            }
            res += dec
            pre = 1
        }
    }
    return res
}
```

