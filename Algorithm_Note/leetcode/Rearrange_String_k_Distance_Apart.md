# 358. K 距离间隔重排字符串
给你一个非空的字符串 s 和一个整数 k，你要将这个字符串中的字母进行重新排列，使得重排后的字符串中相同字母的位置间隔距离至少为 k。

所有输入的字符串都由小写字母组成，如果找不到距离至少为 k 的重排结果，请返回一个空字符串 ""。

**示例 1：**
输入: s = "aabbcc", k = 3
输出: "abcabc"
解释: 相同的字母在新的字符串中间隔至少 3 个单位距离。

**示例 2:**
输入: s = "aaabc", k = 3
输出: ""
解释: 没有办法找到可能的重排结果。

### 贪心 + Hash表
```java
class Solution {
    public String rearrangeString(String s, int k) {
        int[] map = new int[26];
        for(char c : s.toCharArray())
            map[c - 'a']++;
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> map[o2] - map[o1]);
        for(int i = 0; i < map.length; i++)
            if(map[i] > 0) heap.add(i);

        StringBuilder res = new StringBuilder();
        Queue<Integer> temp = new LinkedList<>();
        while(!heap.isEmpty()){
            int cur = heap.poll();
            temp.add(cur);
            map[cur]--;
            res.append((char)('a' + cur));
            if(temp.size() >= k){	// 必须是 >=，考虑k为1或0的情况
                int mem = temp.poll();
                if(map[mem] > 0) heap.offer(mem);
            }
        }

        return res.length() == s.length() ? res.toString() : "";
    }
}
```
```java
class Solution {
    public String rearrangeString(String s, int k) {
        if (k <= 1) return s;
        HashMap<Character, Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());   // 降序
        for (Character cha : s.toCharArray())
            map.put(cha, map.getOrDefault(cha, 0) + 1);
        maxHeap.addAll(map.entrySet());

        StringBuilder res = new StringBuilder(s.length());
        Queue<Map.Entry<Character, Integer>> storage = new LinkedList<>();
        while (!maxHeap.isEmpty()) {
            Map.Entry<Character, Integer> cur = maxHeap.poll();
            res.append(cur.getKey());
            cur.setValue(cur.getValue() - 1);
            storage.offer(cur);
            if (storage.size() == k) {
                Map.Entry<Character, Integer> entry = storage.poll();
                if (entry.getValue() > 0){
                    maxHeap.add(entry);
                }
            }
        }
        return res.length() == s.length() ? res.toString() : "";
    }
}
```