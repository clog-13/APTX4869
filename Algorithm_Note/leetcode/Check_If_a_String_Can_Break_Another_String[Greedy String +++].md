#1433. 检查一个字符串是否可以打破另一个字符串
给你两个字符串 s1 和 s2 ，它们长度相等，请你检查是否存在一个 s1  的排列可以打破 s2 的一个排列，或者是否存在一个 s2 的排列可以打破 s1 的一个排列。

字符串 x 可以打破字符串 y （两者长度都为 n ）需满足对于所有 i（在 0 到 n - 1 之间）都有 x[i] >= y[i]（字典序意义下的顺序）。

**示例：**
输入：s1 = "abe", s2 = "acd"
输出：false
解释：s1="abe" 的所有排列包括："abe"，"aeb"，"bae"，"bea"，"eab" 和 "eba" ，s2="acd" 的所有排列包括："acd"，"adc"，"cad"，"cda"，"dac" 和 "dca"。然而没有任何 s1 的排列可以打破 s2 的排列。也没有 s2 的排列能打破 s1 的排列。

##排序
```java
class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {
        int N = s1.length();
        int[] list_a = new int[N];
        int[] list_b = new int[N];
        for(int i = 0; i < N; i++) {
            list_a[i] = s1.charAt(i) - 'a';
            list_b[i] = s2.charAt(i) - 'a';
        }

        Arrays.sort(list_a);
        Arrays.sort(list_b);

        boolean ba = true, bb = true;
        for(int i = 0; i < N; i++) {
            if(list_a[i] > list_b[i]) ba = false;
            if(list_a[i] < list_b[i]) bb = false;
        }
        return ba||bb;
    }
}
```
时：O()
空：O()
##计数排序
```java
class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {
        int[] c1 = count(s1);
        int[] c2 = count(s2);

        return canBreak(c1, c2) || canBreak(c2, c1);
    }

    private int[] count(String s) {
        int[] c = new int[26];
        for (char ch : s.toCharArray())
            c[ch - 'a']++;
        return c;
    }

    private boolean canBreak(int[] c1, int[] c2) {
        c1 = Arrays.copyOf(c1, c1.length);
        c2 = Arrays.copyOf(c2, c2.length);

        for (int i = 0; i < 26; i++) {
            int consume = c1[i];
            if (consume == 0) continue;

            for (int j = i; j < 26; j++) {
                if (c2[j] >= consume) {
                    c2[j] -= consume;
                    consume = 0;
                    break;
                } else {
                    c2[j] = 0;
                    consume -= c2[j];
                }
            }

            if (consume > 0) return false;	// c2消耗不了
        }
        return true;
    }
}
```
```java
class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {
        int[] res1 = new int[26];
        int[] res2 = new int[26];
        char[] chars = s1.toCharArray();
        for (char c : chars)
            res1[c-'a']++;

        chars = s2.toCharArray();
        for (char c : chars)
            res2[c-'a']++;

        for (int i = 1; i < 26; i++) {		// 前缀和
            res1[i]+=res1[i-1];
            res2[i]+=res2[i-1];
        }

        return f(res1,res2) || f(res2,res1);
    }

    boolean f(int[] a,int[] b) {
        for (int i = 0; i < 26; i++) {
            if (a[i] < b[i]) return false;
        }
        return true;
    }
}
```
_ _ _
> B
