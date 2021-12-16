# 838. Push Dominoes

There are `n` dominoes in a line, and we place each domino vertically upright. In the beginning, we simultaneously push some of the dominoes either to the left or to the right.

After each second, each domino that is falling to the left pushes the adjacent domino on the left. Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.

When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.

For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or already fallen domino.

You are given a string `dominoes` representing the initial state where:

- `dominoes[i] = 'L'`, if the `ith` domino has been pushed to the left,
- `dominoes[i] = 'R'`, if the `ith` domino has been pushed to the right, and
- `dominoes[i] = '.'`, if the `ith` domino has not been pushed.

Return *a string representing the final state*.

 

**Example 1:**

```
Input: dominoes = "RR.L"
Output: "RR.L"
Explanation: The first domino expends no additional force on the second domino.
```

**Example 2:**

```
Input: dominoes = ".L.R...LR..L.."
Output: "LL.RR.LLRRLL.."
```



## Double Points

```java
class Solution {
    public String pushDominoes(String dominoes1) {
        int le = 0, size = dominoes1.length();
        char[] arr = dominoes1.toCharArray();
        boolean findL = false;
        for (int i = 0; i < size; i++) {
            if (arr[i] == 'L') {
                int tmp = i-1;
                while (tmp >= le) {
                    arr[tmp] = 'L';
                    tmp = tmp-1;
                }
                le = i+1;
            } else if (arr[i] == 'R') {
                int start = i+1, end = i+1;
                while (i < size) {
                    if (arr[i] == 'L'){
                        end = i-1;
                        le = end+1;
                        i = end+1;
                        findL = true;
                        break;
                    }
                    if (arr[i] == 'R') {
                        while (start < i) {
                            arr[start] = 'R';
                            start += 1;
                        }
                        start = i+1;
                    }
                    i++;
                }

                if (findL) {
                    while (start < end) {
                        arr[start++] = 'R';
                        arr[end--] = 'L';
                    }
                    findL = false;
                } else {
                    while (start < size) {
                        arr[start++] = 'R';
                    }
                }
            }
        }
        return new String(arr);
    }
}
```



## Simulation

```java
class Solution {
    public String pushDominoes(String S) {
        char[] arr = S.toCharArray();
        int N = arr.length, force = 0;
        int[] forces = new int[N];

        for (int i = 0; i < N; ++i) {
            if (arr[i] == 'R') force = N;
            else if (arr[i] == 'L') force = 0;
            else force = Math.max(force - 1, 0);
            forces[i] += force;
        }

        force = 0;
        for (int i = N-1; i >= 0; --i) {
            if (arr[i] == 'L') force = N;
            else if (arr[i] == 'R') force = 0;
            else force = Math.max(force - 1, 0);
            forces[i] -= force;
        }

        StringBuilder res = new StringBuilder();
        for (int f: forces) res.append(f>0 ? 'R' : f<0?'L':'.');
        return res.toString();
    }
}
```

```python
class Solution:
    def pushDominoes(self, dominoes: str) -> str:
        tmp = ""
        while dominoes != tmp:
            tmp = dominoes
            dominoes = dominoes.replace("R.L", "T")
            dominoes = dominoes.replace(".L", "LL")
            dominoes = dominoes.replace("R.", "RR")
            dominoes = dominoes.replace("T", "R.L")
        return dominoes
```

