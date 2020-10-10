# 254. 因子的组合
整数可以被看作是其因子的乘积。

例如：
8 = 2 x 2 x 2 = 2 x 4
请实现一个函数，该函数接收一个整数 n 并返回该整数所有的因子组合。

注意：
- 你可以假定 n 为永远为正数。
- 因子必须大于 1 并且小于 n。

**示例：**
输入: 32
输出:
[
  [2, 16],
  [2, 2, 8],
  [2, 2, 2, 4],
  [2, 2, 2, 2, 2],
  [2, 4, 4],
  [4, 8]
]

### 回溯（DFS）
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> getFactors(int n) {
        backtrack(new LinkedList<>(), n);
        return res;
    }

    private void backtrack(LinkedList<Integer> collector, int n) {
        if (n == 1) return;
        if (collector.size() > 0) {
            ArrayList<Integer> tmp = new ArrayList<>(collector);
            tmp.add(n);
            res.add(tmp);
        }
        for (int i = 2; i*i <= n; i++) {
            if (n % i != 0) continue;
            if (collector.isEmpty() || collector.getLast() <= i) {
                collector.add(i);
                backtrack(collector, n / i);
                collector.remove(collector.size() - 1);
            }
        }
    }
}
```