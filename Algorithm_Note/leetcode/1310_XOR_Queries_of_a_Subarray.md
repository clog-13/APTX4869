# 1310. XOR Queries of a Subarray
Given the array arr of positive integers and the array queries where queries[i] = [Li, Ri], for each query i compute the XOR of elements from Li to Ri (that is, arr[Li] xor arr[Li+1] xor ... xor arr[Ri] ). Return an array containing the result for the given queries.


**Example:**
```
Input: arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
Output: [2,7,14,8] 
Explanation: 
The binary representation of the elements in the array are:
1 = 0001 
3 = 0011 
4 = 0100 
8 = 1000 
The XOR values for queries are:
[0,1] = 1 xor 3 = 2 
[1,2] = 3 xor 4 = 7 
[0,3] = 1 xor 3 xor 4 xor 8 = 14 
[3,3] = 8
```


## 前缀"异或"

[a1,a2,a3,a4,a5,a6,a7] 比如求a4\^a5\^a6

preXor[6] = a1\^a2\^a3\^a4\^a5\^a6

preXor[3] = a1\^a2\^a3

res = preXor[6] \^ a1\^a2\^a3

res = preXor[6] ^ preXor[3]

```java
class Solution {
    public int[] xorQueries(int[] arr, int[][] queries) {
        int N = arr.length, idx = 0;
        int[] preXor = new int[N+1], res = new int[queries.length];
        for (int i = 1; i <= N; i++) {
            preXor[i] = preXor[i-1] ^ arr[i-1];
        }

        for (int[] q : queries) {
            int t = preXor[Math.max(q[0], 0)] ^ preXor[q[1]+1];
            res[idx++] = t;
        }
        return res;
    }
}
```

