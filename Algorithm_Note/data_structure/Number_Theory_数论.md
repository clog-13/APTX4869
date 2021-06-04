# 数论



## GCD(最小公约数)

```java
private static int gcd(int a, int b) {
	return b == 0 ? a : gcd(b, a%b);
}
```



## 素数筛（线性）

```java
void get_primes(int N) {
    for (int n = 2; n <= N; n ++ ) {
        if (!st[n]) primes[idx++] = n;
		// 遍历一些已经确定的素数
        for (int i = 0; primes[i]*n <= maxN; i++) {
            st[primes[i] * n] = true;
            if (n % primes[i] == 0) break;
        }
    }
}
```

```c++
// 朴素筛法-O(nlogn)
void get_primes(int N) {
    for (int n = 2; n <= N; n++) {
        if (!st[n]) prime[cnt++] = n;
        for (int i = n+n; i <= N; i += n) {
            st[i] = true;
        }
    }
}

// 埃式筛法-O(nloglogn)
void get_primes(int N) {
    for (int n = 2; n <= N; n++) {
        if (!st[n]) {
            primes[cnt++] = n;
            for (int i = n; i <= N; i += n) {
                st[i] = true;
            }
        }
    }
}
```

