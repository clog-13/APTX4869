# 187. Repeated DNA Sequences

The **DNA sequence** is composed of a series of nucleotides abbreviated as `'A'`, `'C'`, `'G'`, and `'T'`.

- For example, `"ACGAATTCCG"` is a **DNA sequence**.

When studying **DNA**, it is useful to identify repeated sequences within the DNA.

Given a string `s` that represents a **DNA sequence**, return all the **`10`-letter-long** sequences (substrings) that occur more than once in a DNA molecule. You may return the answer in **any order**.



**Example 1:**

```
Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
Output: ["AAAAACCCCC","CCCCCAAAAA"]
```



## 枚举+HashSet

```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int L = 10, N = s.length();
        HashSet<String> set = new HashSet(), res = new HashSet();

        for (int start = 0; start < N - L + 1; start++) {
            String cur = s.substring(start, start + L);
            if (set.contains(cur)) res.add(cur);
            set.add(cur);
        }
        return new ArrayList<String>(res);
    }
}
```



## Rabin-Karp 算法(进制转换)

```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int L = 10, N = s.length();
        if (N <= L) return new ArrayList<>();

        int bias = 4, biasL = (int)Math.pow(bias, L);    // rolling hash parameters: base bias
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0); map.put('C', 1);
        map.put('G', 2); map.put('T', 3);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = map.get(s.charAt(i));

        Set<Integer> set = new HashSet<>();
        Set<String> res = new HashSet<>();
        int cur = 0;
        for (int i = 0; i < L; i++) cur = cur*bias + arr[i];
        set.add(cur);
        for (int start = 1; start <= N-L; start++) {
            cur = cur*bias - arr[start - 1]*biasL + arr[start + L - 1];

            if (set.contains(cur)) res.add(s.substring(start, start + L));
            set.add(cur);
        }
        return new ArrayList<>(res);
    }
}
```



## 位操作

```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int L = 10, N = s.length();
        if (N <= L) return new ArrayList<>();

        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0); map.put('C', 1);
        map.put('G', 2); map.put('T', 3);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = map.get(s.charAt(i));

        Set<Integer> set = new HashSet<>();
        Set<String> res = new HashSet<>();
        int bitmask = 0;
        for (int i = 0; i < L; i++) {
            bitmask <<= 2;  // 四进制
            bitmask |= arr[i];
        }
        set.add(bitmask);
        for (int start = 1; start <= N-L; start++) {
            bitmask <<= 2;                  // 为加上右边留出位置
            bitmask |= arr[start + L - 1];  // 加上右边
            bitmask &= ~(3 << (2*L));       // 去掉左边

            if (set.contains(bitmask)) res.add(s.substring(start, start + L));
            set.add(bitmask);
        }
        return new ArrayList<String>(res);
    }
}
```
