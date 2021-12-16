# 201. Bitwise AND of Numbers Range

Given two integers `left` and `right` that represent the range `[left, right]`, return *the bitwise AND(&) of all numbers in this range, inclusive*.

 

**Example 1:**

```
Input: left = 5, right = 7
Output: 4
```

**Example 2:**

```
Input: left = 1, right = 2147483647
Output: 0
```



## CS + BIT OP

```c++
class Solution {
public:  
    int rangeBitwiseAnd(int m, int n) {
        // 对于一系列&位，只要有一个零值的位，那么这一系列位的按位与运算结果都将为零。
        // 不管取值如何，必然要经过 ..x0abcd. 和 ..x10000.. 使得x 右边的后缀必然都为 0
        int shift = 0;  
        while (m != n) {  // 找到公共前缀
            m >>= 1;
            n >>= 1;
            shift++;
        }
        return m << shift;
    }
};
```

```c++
class Solution {
public:
    int rangeBitwiseAnd(int m, int n) {
        while (m < n) {
            n -= n&-n;
            // n = n&(n-1);
        }
        return n;
    }
};
```

