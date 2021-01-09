# 800. 相似 RGB 颜色
RGB 颜色用十六进制来表示的话，每个大写字母都代表了某个从 0 到 f 的 16 进制数。

RGB 颜色 "#AABBCC" 可以简写成 "#ABC" 。例如，"#15c" 其实是 "#1155cc" 的简写。

现在，假如我们分别定义两个颜色 "#ABCDEF" 和 "#UVWXYZ"，则他们的相似度可以通过这个表达式 -(AB - UV)^2 - (CD - WX)^2 - (EF - YZ)^2 来计算。

那么给定颜色 "#ABCDEF"，请你返回一个与 #ABCDEF 最相似的 7 个字符代表的颜色，并且它是可以被简写形式表达的。（比如，可以表示成类似 "#XYZ" 的形式）

**示例：**
输入：color = "#09f166"
输出："#11ee66"
解释：
因为相似度计算得出 -(0x09 - 0x11)^2 -(0xf1 - 0xee)^2 - (0x66 - 0x66)^2 = -64 -9 -0 = -73
这已经是所有可以简写的颜色中最相似的了

```java
class Solution {
    public String similarRGB(String color) {
        return "#" + f(color.substring(1, 3)) + f(color.substring(3, 5)) + f(color.substring(5));
    }

    public String f(String comp) {
        int q = Integer.parseInt(comp, 16);
        q = q / 17 + (q % 17 > 8 ? 1 : 0);
        return String.format("%02x", 17 * q);
    }
}
```



# 面试题 16.11. 跳水板
你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。

返回的长度需要从小到大排列。

**示例 1**

输入：
shorter = 1
longer = 2
k = 3
输出： [3,4,5,6]
解释：
可以使用 3 次 shorter，得到结果 3；使用 2 次 shorter 和 1 次 longer，得到结果 4 。以此类推，得到最终结果。

```java
class Solution {
    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) return new int[0];
        if (shorter == longer) return new int[]{shorter * k};

        int[] res = new int[k + 1];
        res[0] = shorter * k;
        int up = longer - shorter;
        for (int i = 1; i <= k; i++)
            res[i] = res[i - 1] + up;

        return res;
    }
}
```

# 1668. 最大重复子字符串
给你一个字符串 `sequence` ，如果字符串 `word` 连续重复 `k` 次形成的字符串是 `sequence` 的一个子字符串，那么单词 `word` 的 **重复值为 `k`** 。单词 `word` 的 **最****大重复值** 是单词 `word` 在 `sequence` 中最大的重复值。如果 `word` 不是 `sequence` 的子串，那么重复值 `k` 为 `0` 。

给你一个字符串 `sequence` 和 `word` ，请你返回 **最大重复值 `k`** 。

**示例：**

```
输入：sequence = "ababc", word = "ab"
输出：2
解释："abab" 是 "ababc" 的子字符串。
```

```java
int maxRepeating(char * sequence, char * word){
    int res = 0, m = strlen(sequence), n = strlen(word);
    for (int i = 0; i < m; i++) {
        int j = i;  // 暴力匹配
        while (j < m && sequence[j] == word[(j-i)%n]) j++;
        res = fmax(res, (j - i) / n);  // 多少个循环节
    }
    return res;
}
```



# 1700. 无法吃午餐的学生数量

学校的自助午餐提供圆形和方形的三明治，分别用数字 `0` 和 `1` 表示。所有学生站在一个队列里，每个学生要么喜欢圆形的要么喜欢方形的。
餐厅里三明治的数量与学生的数量相同。所有三明治都放在一个 **栈** 里，每一轮：

- 如果队列最前面的学生 **喜欢** 栈顶的三明治，那么会 **拿走它** 并离开队列。
- 否则，这名学生会 **放弃这个三明治** 并回到队列的尾部。

这个过程会一直持续到队列里所有学生都不喜欢栈顶的三明治为止。

给你两个整数数组 `students` 和 `sandwiches` ，其中 `sandwiches[i]` 是栈里面第 `i` 个三明治的类型（`i = 0` 是栈的顶部）， `students[j]` 是初始队列里第 `j` 名学生对三明治的喜好（`j = 0` 是队列的最开始位置）。请你返回无法吃午餐的学生数量。



**示例：**

```
输入：students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
输出：3
```

```java
int next(int n, int* arr, int size) {
	if (n == size) n = 0;
	while (arr[n] == 2) {
		n++;
		if (n == size) n = 0;
	}
	return n;
}

int countStudents(int* students, int studentsSize, int* sandwiches, int sandwichesSize) {
	int front = 0, top = 0;
	while (top < sandwichesSize) {
		if (students[front] == sandwiches[top]) {
			top++;
			if (top == sandwichesSize) break;
			students[front] = 2;
			front = next(front+1, students, studentsSize);
		} else {
			int temp = front;
			while (students[front] != sandwiches[top]) {
				front = next(front+1, students, studentsSize);
				if (front == temp) break;
			}
			if (front == temp) break;
		}
	}

	return sandwichesSize - top;
}
```



# 面试题 01.02. 判定是否互为字符重排

给定两个字符串 `s1` 和 `s2`，请编写一个程序，确定其中一个字符串的字符重新排列后，能否变成另一个字符串。

**示例：**

```
输入: s1 = "abc", s2 = "bca"
输出: true 
```

```java
bool CheckPermutation(char* s1, char* s2){
    char bucket_s1[256] = {0};
    char bucket_s2[256] = {0};

    while (*s1) bucket_s1[*s1++]++;
    while (*s2) bucket_s2[*s2++]++;

    for (int i = 0; i < 256; i++) {
        if (bucket_s1[i] != bucket_s2[i]) return false;
    }

    return true;
}
```



# 面试题 02.06. 回文链表

编写一个函数，检查输入的链表是否是回文的。

**示例：**

```
输入： 1->2->2->1
输出： true 
```

```java
struct ListNode* reverseList(struct ListNode* head) {
    struct ListNode *pre = NULL, *cur = head;
    while (cur != NULL) {
        struct ListNode* nex = cur->next;
        cur->next = pre;
        pre = cur;
        cur = nex;
    }
    return pre;
}

struct ListNode* endOfFirstHalf(struct ListNode* head) {
    struct ListNode *fast = head, *slow = head;
    while (fast->next != NULL && fast->next->next != NULL) {
        fast = fast->next->next;
        slow = slow->next;
    }
    return slow;
}

bool isPalindrome(struct ListNode* head) {
    if (head == NULL) return true;

    // 找到前半部分链表的尾节点, 并反转后半部分链表
    struct ListNode* firstHalfEnd = endOfFirstHalf(head);
    struct ListNode* secondHalfStart = reverseList(firstHalfEnd->next);

    // 判断
    struct ListNode *p1 = head, *p2 = secondHalfStart;
    bool res = true;
    while (res && p2 != NULL) {
        if (p1->val != p2->val) res = false;
        p1 = p1->next;
        p2 = p2->next;
    }

    // 还原链表
    firstHalfEnd->next = reverseList(secondHalfStart);
    return res;
}
```

