# 1488. 避免洪水泛滥

你的国家有无数个湖泊，所有湖泊一开始都是空的。当第 `n` 个湖泊下雨的时候，如果第 `n` 个湖泊是空的，那么它就会装满水，否则这个湖泊会发生洪水。你的目标是避免任意一个湖泊发生洪水。

给你一个整数数组 `rains` ，其中：

- `rains[i] > 0` 表示第 `i` 天时，第 `rains[i]` 个湖泊会下雨。
- `rains[i] == 0` 表示第 `i` 天没有湖泊会下雨，你可以选择 **一个** 湖泊并 **抽干** 这个湖泊的水。

请返回一个数组 `ans` ，满足：

- `ans.length == rains.length`
- 如果 `rains[i] > 0` ，那么`ans[i] == -1` 。
- 如果 `rains[i] == 0` ，`ans[i]` 是你第 `i` 天选择抽干的湖泊。

如果有多种可行解，请返回它们中的 **任意一个** 。如果没办法阻止洪水，请返回一个 **空的数组** 。

请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生（详情请看示例 4）。

 

**示例 1：**

```
输入：rains = [1,2,0,0,2,1]
输出：[-1,-1,2,1,-1,-1]
解释：第一天后，装满水的湖泊包括 [1]
第二天后，装满水的湖泊包括 [1,2]
第三天后，我们抽干湖泊 2 。所以剩下装满水的湖泊包括 [1]
第四天后，我们抽干湖泊 1 。所以暂时没有装满水的湖泊了。
第五天后，装满水的湖泊包括 [2]。
第六天后，装满水的湖泊包括 [1,2]。
可以看出，这个方案下不会有洪水发生。同时， [-1,-1,1,2,-1,-1] 也是另一个可行的没有洪水的方案。
```

**示例 2：**

```
输入：rains = [1,2,0,1,2]
输出：[]
解释：第二天后，装满水的湖泊包括 [1,2]。我们可以在第三天抽干一个湖泊的水。
但第三天后，湖泊 1 和 2 都会再次下雨，所以不管我们第三天抽干哪个湖泊的水，另一个湖泊都会发生洪水。
```

**示例 3：**

```
输入：rains = [69,0,0,0,69]
输出：[-1,69,1,1,-1]
解释：任何形如 [-1,69,x,y,-1], [-1,x,69,y,-1] 或者 [-1,x,y,69,-1] 都是可行的解，其中 1 <= x,y <= 10^9
```



## 贪心

```java
class Solution {
    public int[] avoidFlood(int[] rains) {
        int len = 0, N = rains.length;
        int[] days = new int[N], res = new int[N];
        for (int i = 0; i < N; i++) {
            if (rains[i] == 0) days[len++] = i;
        }
        int idx = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            if (rains[i] > 0) {
                res[i] = -1;
                if (map.containsKey(rains[i])) {
                    int rainIdx = map.get(rains[i]), t = idx;
                    while (t < len) {
                        if (days[t] > rainIdx) break;
                        t++;
                    }
                    if (t>=len || days[t]>i) return new int[0];
                    res[days[t]] = rains[i];
                    days[t] = -1;
                    if (t == idx) {  // 去掉已经用来排水的天, 可以删掉
                        while (idx<len && days[idx]==-1) {
                            idx++;
                        }
                    }
                }
                map.put(rains[i], i);
            }
        }
        for (int i = 0; i < len; i++) {
            if (days[i] != -1) res[days[i]] = 1;
        }
        return res;
    }
}
```



## 并查集

```java
class Solution {
    public int[] avoidFlood(int[] rains) {
        int N = rains.length;
        int[] fa = new int[N], idx = new int[N];  // idx[i]:i下雨天后最近的排水日
        for (int i = 0; i < N; i++) fa[i] = i;
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[N]; Arrays.fill(res, 1);
        for (int i = 0; i < N; i++) {
            if (rains[i] > 0) {
                res[i] = -1;
                if (i>0 && rains[i-1]>0) fa[i] = fa[i-1];  // 与前一个下雨天联通
                else idx[i] = 0x3f3f3f3f;  // 目前第一个雨天(且暂时不知道有没有后续可以排水的天)

                if (map.containsKey(rains[i])) {
                    int lastDay = map.get(rains[i]), lf = find(fa, lastDay);
                    if (idx[lf] >= i) return new int[]{};
                    res[idx[lf]] = rains[i];
                    idx[lf]++;  // idx[lf]这天用来排水, 联通到后一天
                    if (idx[lf]<N && rains[idx[lf]]>0) {  // 如后一天下雨, 就要找后一个排水区间
                        fa[lf] = find(fa, idx[lf]);
                    }
                }
                map.put(rains[i], i);
            } else if (i>0 && rains[i-1]>0) {  // 今天没下雨但前一天下雨了
                idx[find(fa, i-1)] = i;  // 可以处理 前一雨天(联通的父节点)) 的天是 i
            }
        }
        return res;
    }

    int find(int[] fa, int x) {
        if (x != fa[x]) fa[x] = find(fa, fa[x]);
        return fa[x];
    }
}
```

