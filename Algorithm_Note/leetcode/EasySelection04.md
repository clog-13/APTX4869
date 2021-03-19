# EasySelection04



## 面试题 08.06. 汉诺塔问题

在经典汉诺塔问题中，有 3 根柱子及 N 个不同大小的穿孔圆盘，盘子可以滑入任意一根柱子。一开始，所有盘子自上而下按升序依次套在第一根柱子上(即每一个盘子只能放在更大的盘子上面)。移动圆盘时受到以下限制:
(1) 每次只能移动一个盘子;
(2) 盘子只能从柱子顶端滑出移到下一根柱子;
(3) 盘子只能叠在比它大的盘子上。

请编写程序，用栈将所有盘子从第一根柱子移到最后一根柱子。

你需要原地修改栈。

```java
class Solution {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        move(A.size(), A,B,C);
    }

    private void move(int size, List<Integer> a, List<Integer> b, List<Integer> c) {
        if(size == 1){
            c.add(a.remove(a.size()-1));
            return;
        }
        move(size-1,a,c,b);  // 把n-1个盘子从a移到b
        c.add(a.remove(a.size()-1));  // 把第n个盘子 从a移到c
        move(size-1,b,a,c);  // 把n-1个盘子从a移到b
    }
}
```



## 面试题 17.01. 不用加号的加法

设计一个函数把两个数字相加。不得使用 + 或者其他算术运算符。

**示例:**

```
输入: a = 1, b = 1
输出: 2
```

**提示：**

- `a`, `b` 均可能是负数或 0
- 结果不会溢出 32 位整数

```java
class Solution {
    public int add(int a, int b) {
        int sum = 0, carry = 0;
        while (b != 0) {
        	sum = a ^ b;			// 异或计算未进位的部分
        	carry = (a & b) << 1;	// 进位部分
        	a = sum;				// 保存未进位部分，再次计算
        	b = carry;				// 保存进位部分，再次计算
        }
        return a;	// 最后无进位，异或的结果即加法结果
    }
}
```



## 面试题 03.02. 栈的最小值
请设计一个栈，除了常规栈支持的pop与push函数以外，还支持min函数，该函数返回栈元素中的最小值。执行push、pop和min操作的时间复杂度必须为O(1)。

**示例：**

```
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```

```java
class MinStack {
    int min = Integer.MAX_VALUE;
    Stack<Integer> valStack = new Stack<>(), minStack = new Stack<>();

    public MinStack() {}
    
    public void push(int x) {
        valStack.push(x);
        min = Math.min(min, x);
        minStack.push(min);  // ！！！算法重点
    }
    
    public void pop() {
        valStack.pop();
        minStack.pop();
        if (minStack.isEmpty()) {
            min = Integer.MAX_VALUE;
        } else {
            min = minStack.peek();  
        }
    }
    
    public int top() {
        return valStack.peek();
    }
    
    public int getMin() {
        return min;
    }
}
```



## 剑指 Offer 17. 打印从1到最大的n位数

输入数字 `n`，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。

**示例:**

```
输入: n = 1
输出: [1,2,3,4,5,6,7,8,9]
```

```java
class Solution {
    int[] res;
    int nines = 0, idx = 0, le;
    char[] tempNum, loop = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public int[] printNumbers(int n) {
        res = new int[(int)Math.pow(10, n) - 1];
        tempNum = new char[n];
        le = n - 1;
        
        dfs(0, n);
        return res;
    }
    void dfs(int u, int n) {
        if (u == n) {
            String s = String.valueOf(tempNum).substring(le);
            if (!s.equals("0")) res[idx++] = Integer.parseInt(s);
            if (n - le == nines) le--;
            return;
        }
        for (char lo : loop) {
            if (lo == '9') nines++;
            tempNum[u] = lo;
            dfs(u + 1, n);
        }
        nines--;
    }
}
```

