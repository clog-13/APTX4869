# 1079. Letter Tile Possibilities

You have `n` `tiles`, where each tile has one letter `tiles[i]` printed on it.

Return *the number of possible non-empty sequences of letters* you can make using the letters printed on those `tiles`.

 

**Example 1:**

```
Input: tiles = "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
```

**Example 2:**

```
Input: tiles = "AAABBC"
Output: 188
```



## DFS

```java
class Solution {
    int res = 0;
    public int numTilePossibilities(String tiles) {
        char[] chs = tiles.toCharArray();
        Arrays.sort(chs);
        boolean[] used = new boolean[chs.length];
        dfs(used, chs);
        return res;
    }

    void dfs(boolean[] used, char[] chs) {
        char last = '#';
        for (int i = 0; i < used.length; i++) {
            if (!used[i] && chs[i]!=last) {
                res++;
                used[i] = true;
                dfs(used, chs);
                used[i] = false;
                last = chs[i];
            }
        }
    }
}
```



## BackTracking

```java
class Solution {
    Set<String> set = new HashSet<>();
    
    public int numTilePossibilities(String tiles) {
        helper(new String(""), 0, tiles);
        return set.size();
    }

    void helper(String sub, int vis, String tiles) {
        for (int i = 0; i < tiles.length(); i++) {
            if ((vis&(1<<i)) > 0) continue;
            String tmp = sub + tiles.charAt(i);
            if (set.contains(tmp)) continue;
            set.add(tmp);
            helper(tmp, vis|(1<<i), tiles);
        }
    }
}
```

