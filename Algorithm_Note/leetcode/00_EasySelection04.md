# 剑指 Offer 58 - I. 翻转单词顺序

输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。

**示例：**

```
输入: "the sky is blue"
输出: "blue is sky the"
```

```c
char* reverseWords(char* s){
    int rear = -1, idx = 0, N = strlen(s);
    if (!N) return "";

    char *res = calloc(N+1, sizeof(char));
    for (int i = N-1; i >= 0; i--) {
        if (rear==-1 && s[i]!=' ' && ) {
            rear = i;
        }

        if (rear!=-1 && (s[i-1]==' ' || i==0)) {	// rear移动到空格处（或头）时
            int sz = rear - i + 1;
            strncpy(res+idx, s+i, sz);
            idx += sz;
            res[idx++] = ' ';
            rear = -1;
        }
    }
    if (idx != 0) res[idx-1] = '\0';

    return res;
}
```

# 剑指 Offer 65. 不用加减乘除做加法

写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。

```java
class Solution {
    public int add(int a, int b) {
        while (b != 0) { // 当进位为 0 时跳出
            int c = (a & b) << 1;  // 进位
            a ^= b;     // 非进位和
            b = c;      // 进位
        }
        return a;
    }
}
```

# 剑指 Offer 61. 扑克牌中的顺子

从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。

**示例:**

```
输入: [0,0,1,2,5]
输出: True
```
```java
class Solution {
    public boolean isStraight(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int max = 0, min = 14;
        for (int num : nums) {
            if (num == 0) continue;
            max = Math.max(max, num); // 最大牌
            min = Math.min(min, num); // 最小牌
            if (set.contains(num)) return false; // 若有重复，返回 false
            set.add(num);
        }
        return max-min < 5; // !!!
    }
}
```
```c
int sort(int *a, int *b) {
    return *a-*b;
}

bool isStraight(int* nums, int numsSize){
    int map[20] = {0}, zero_cnt = 0, min = 0;

    qsort(nums, numsSize, sizeof(int), sort);
    
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] == 0) zero_cnt++;
        else if (min == 0) min = nums[i];
        map[nums[i]]++;
    }

    for (int i = 0; i < 5; i++) {
        if (map[min+i] == 0) zero_cnt--;
    }
    
    return !zero_cnt;
}
```

.