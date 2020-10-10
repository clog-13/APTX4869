# 365. 水壶问题
有两个容量分别为 x升 和 y升 的水壶以及无限多的水。请判断能否通过使用这两个水壶，从而可以得到恰好 z升 的水？

如果可以，最后请用以上水壶中的一或两个来盛放取得的 z升 水。

**你允许：**
装满任意一个水壶
清空任意一个水壶
从一个水壶向另外一个水壶倒水，直到装满或者倒空

**示例:**
输入: x = 3, y = 5, z = 4
输出: True

## 搜索
```java
TLE
class Solution {
    public boolean canMeasureWater(int x, int y, int z) {
        if (x + y < z) return false;
        HashSet<Node> haveCount = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(0, 0));

        while(!stack.isEmpty()){
            Node top = stack.pop();
            if(top.x==z || top.y == z||top.x + top.y == z) return true;
            if(haveCount.contains(top)) continue;
            haveCount.add(top);

            stack.push(new Node(x,  top.y));    // 把X灌满
            stack.push(new Node(top.x,  y));    // 把Y灌满
            stack.push(new Node(0,  top.y));    // 把X清空
            stack.push(new Node(top.x,  0));    // 把Y清空
            // 把X的水灌入Y，直至灌满或X空
            stack.push(new Node(top.x - Math.min(top.x, y-top.y), top.y + Math.min(top.x, y - top.y)));
            // 把Y的水灌入X，直至灌满或Y空
            stack.push(new Node(top.x + Math.min(top.y, x - top.x), top.y - Math.min(top.y, x - top.x)));
        }
        return false;
    }
}

class Node{
    int x, y;
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int hashCode(){
        String top = String.valueOf(x) + "|" + String.valueOf(y);
        return top.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) 
            return false;
        Node obj1 = (Node) obj;
        if (obj1.x == this.x && obj1.y == this.y)
            return true;
        return false;
    }
}
```
```java
class Solution {
    public boolean canMeasureWater(int x, int y, int z) {
        if(z < 0 || x+y < z) return false;

        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int n = queue.poll();
            if (n+x <= x+y && set.add(n+x))
                queue.offer(n+x);
            if (n+y <= x+y && set.add(n+y))
                queue.offer(n+y);
            if(n-x >= 0 && set.add(n-x))
                queue.offer(n-x);
            if(n-y >= 0 && set.add(n-y))
                queue.offer(n-y);

            if(set.contains(z))
                return true;
        }
        return false;
    }
}
```

## 数学
贝祖定理：ax+by=z 有解当且仅当 z 是 x, y 的最大公约数的倍数。因此我们只需要找到 x, y 的最大公约数并判断 z 是否是它的倍数即可。

我们认为，每次操作只会让桶里的水总量增加 x，增加 y，减少 x，或者减少 y。

你可能认为这有问题：如果往一个不满的桶里放水，或者把它排空呢？那变化量不就不是 x 或者 y 了吗？接下来我们来解释这一点：

首先要清楚，在题目所给的操作下，两个桶不可能同时有水且不满。因为观察所有题目中的操作，操作的结果都至少有一个桶是空的或者满的；

其次，对一个不满的桶加水是没有意义的。因为如果另一个桶是空的，那么这个操作的结果等价于直接从初始状态给这个桶加满水；而如果另一个桶是满的，那么这个操作的结果等价于从初始状态分别给两个桶加满；

再次，把一个不满的桶里面的水倒掉是没有意义的。因为如果另一个桶是空的，那么这个操作的结果等价于回到初始状态；而如果另一个桶是满的，那么这个操作的结果等价于从初始状态直接给另一个桶倒满。

因此，我们可以认为每次操作只会给水的总量带来 x 或者 y 的变化量。因此我们的目标可以改写成：找到一对整数 a, b 使得

ax+by=z

而只要满足 z ≤ x+y，且这样的 a, b 存在，那么我们的目标就是可以达成的。这是因为：

若 a ≥ 0, b ≥ 0，那么显然可以达成目标。

若 a < 0，那么可以进行以下操作：

> 往 y 壶倒水；

>把 y 壶的水倒入 x 壶；

>如果 y 壶不为空，那么 x 壶肯定是满的，把 x 壶倒空，然后再把 y 壶的水倒入 x 壶。

重复以上操作直至某一步时 x 壶进行了 aa 次倒空操作，y 壶进行了 bb 次倒水操作。

若 b < 0，方法同上，x 与 y 互换。

而贝祖定理告诉我们，ax+by=z 有解当且仅当 z 是 x, y 的最大公约数的倍数。因此我们只需要找到 x, y 的最大公约数并判断 z 是否是它的倍数即可。

```cpp
bool canMeasureWater(int x, int y, int z){
    if(x + y < z) return false;
    if(x > y) {
        int temp = x;
        x = y;
        y = temp;
    }
    int j = 1;
    for(int i = x;i > 1;i--) {
        if(x % i == 0 && y % i == 0) {
            j = i;
            break;
        }
    }

    if(x == 0 || y == 0)
        return z == 0 || x + y == z;
    else
        return z % j == 0;
}
```
```java
class Solution {
	public boolean canMeasureWater(int x, int y, int z) {
        return z == 0 || (x + y >= z && z % gcd(x, y) == 0);
    }

    int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
```