# KMP

```java
class KMP {
    int maxN = 100010;
    int[] next = new int[maxN];
    
    int kmp(char[] s, char[] p) {
        getNext(p);
        int i = 0, j = 0;
        while (i < s.length && j < p.length) {
            while (j == -1 || s[i] == p[j]) {
                i++; j++;
            } else {
                j = next[j];
            }
        }

        if (j == p.length) return i-j;
        else return -1;    
    }

    void getNext(char[] p) {
        int i = 0, j = -1;
        next[i] = j;
        while (i < p.length) {
            if (j == -1 || p[i] == p[j]) {
                i++; j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }
}
```



