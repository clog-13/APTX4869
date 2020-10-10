# 186. 翻转字符串里的单词 II
给定一个字符串，逐个翻转字符串中的每个单词。

**示例：**
输入: ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
输出: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]

注意：
单词的定义是不包含空格的一系列字符
输入字符串中不会包含前置或尾随的空格
单词与单词之间永远是以单个空格隔开的

进阶：使用 O(1) 额外空间复杂度的原地解法。

## 思维
先翻转每个单词， 再翻转整句话，这样每个单词翻转两次，单词个体顺序不变但单词间的顺序发生了变换。
也可以先翻转整句话，再翻转每个单词。
```cpp
void reverseWord(char* s, int le, int ri) {
    char tmp;
    while (le < ri) {
        tmp = s[le];
        s[le++] = s[ri];
        s[ri--] = tmp;
    }
}

void reverseWords(char* s, int sSize) {
    int start = 0, end, cur;
    if (sSize == 0) return;
    while (cur < sSize) {
        if (s[cur] == ' ') {
            reverseWord(s, start, cur - 1);
            start = cur + 1;
        }
        cur++;
    }
    reverseWord(s, start, sSize - 1);   // 翻转最后一个单词
    
    reverseWord(s, 0, sSize - 1);
}
```