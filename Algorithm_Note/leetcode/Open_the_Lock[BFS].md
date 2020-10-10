#752. 打开转盘锁
你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。

锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。

列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。

字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。

**示例:**
输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
输出：6
解释：
可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
因为当拨动到 "0102" 时这个锁就会被锁定。

## BFS
为了求步数，在 while 里加了一层 for
```java
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        if(dead.contains(target) || dead.contains("0000")) return -1;

        String start="0000";
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        int step = 0;

        while(!queue.isEmpty()) {
            int N = queue.size();
            for(int i = 0; i < N; i++) {
                String cur = queue.poll();
                if(target.equals(cur))
                    return step;

                List<String> nexts = getNexts(cur);
                for(String s : nexts) if(!dead.contains(s) && !visited.contains(s)) {
                        visited.add(s);
                        queue.offer(s);
                }
            }
            step++;
        }
        return -1;
    }
    /**
    *获取邻接的所有节点
    */
    public List<String> getNexts(String cur){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            StringBuilder curSb= new StringBuilder(cur);
            curSb.setCharAt(i, cur.charAt(i)=='0'?'9':(char)(cur.charAt(i)-1));	// 减一
            list.add(curSb.toString());
            curSb.setCharAt(i, cur.charAt(i)=='9'?'0':(char)(cur.charAt(i)+1));	// 加一
            list.add(curSb.toString());
        }
        return list;
    }
}
```
**复杂度分析**

- 时间复杂度：O(N^2 × A^N + D)我们用 A 表示数字的个数，N 表示状态的位数，D 表示数组 deadends 的大小。在最坏情况下，我们需要搜索完所有状态，状态的总数为 O(A^N)。对于每个状态，我们要枚举修改的位置，需要 O(N) 的时间，枚举后得到新的状态同样需要 O(N) 的时间。

- 空间复杂度：O(A^N + D)，用来存储队列以及 deadends 的集合。

##数学（逆向思维）
变成 求从target到0000的状态需要多少步。
理解代码可行性是关键，注意是先求了一次可变换的8种形态（而不是直接从target开始求）
```java
class Solution {
    public int openLock(String[] deadends, String target) {
        List<String> deals = Arrays.asList(deadends);
        if (deals.contains("0000")) return -1;

        final List<String> options = new ArrayList<>(); // 可达目标数的 8 个状态
        for (int i = 0; i < 4; i++) {
            char[] cs = target.toCharArray();
            char c = cs[i];
            cs[i] = (char) ((c - 48 + 1) % 10 + 48);
            options.add(new String(cs));
            cs[i] = (char) ((c - 48 + 9) % 10 + 48);
            options.add(new String(cs));
        }

        options.removeAll(deals); // 可达数字中移除死亡数字
        if (options.isEmpty()) return -1; // 可达目标数的 8 个状态都在死亡数字中直接返回

        int step = Integer.MAX_VALUE;
        for (String option : options) { // 判断可达状态中最短转动次数
            int curStep = 1;
            char[] cs = option.toCharArray();
            for (int i = 0; i < 4; i++) {
                int num = cs[i] - 48;
                if (num > 5) curStep += 10 - num; // 判断正转，倒转
                else curStep += num;
            }
            step = Math.min(curStep, step); // 记录最小旋转次数
        }
        return step;
    }
}
```
**复杂度分析**

- 时间复杂度：O(12)

- 空间复杂度：O(8)