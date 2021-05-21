# 1220. Count Vowels Permutation
Given an integer n, your task is to count how many strings of length n can be formed under the following rules:

Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
Each vowel 'a' may only be followed by an 'e'.
Each vowel 'e' may only be followed by an 'a' or an 'i'.
Each vowel 'i' may not be followed by another 'i'.
Each vowel 'o' may only be followed by an 'i' or a 'u'.
Each vowel 'u' may only be followed by an 'a'.
Since the answer may be too large, return it modulo 10^9 + 7.


**Example:**
```
Input: n = 2
Output: 10
Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
```

## DP
```c++
class Solution {
public:
	int countVowelPermutation(int n) {
		int mod = 1e9 + 7;
		long long a = 1, e = 1, i = 1, o = 1, u = 1;
		for (int j=2; j<=n; j++) {
			long long aa = (e + i + u) % mod;  // a为第(最后)一个字母的方案
			long long ee = (a + i) % mod;      // 理解成正向或反向都一样
			long long oo = (i) % mod;
			long long uu = (i + o) % mod;
			a = aa;
			e = ee;
			i = ii;
			o = oo;
			u = uu;
		}
		return (a + e + i + o + u) % mod;
	}
};
```