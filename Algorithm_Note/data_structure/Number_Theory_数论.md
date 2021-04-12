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
	for (int i = 2; i <= N; i ++ ) {
		if (!st[i]) primes[idx++] = i;
	    
        for (int j = 0; primes[j]*i <= maxN; j++) {
            st[primes[j] * i] = true;
			if (i % primes[j] == 0) break;
        }
    }
}
```

```c++
// 朴素筛法-O(nlogn)
void get_primes(int n) {
    for (int i = 2; i <= n; i++) {
        if (!st[i]) prime[cnt++] = i;
        for (int j = i+i; j <= n; j += i) {
            st[j] = true;
        }
    }
}

// 埃式筛法-O(nloglogn)
void get_primes(int n) {
    for (int i = 2; i <= n; i++) {
        if (!st[i]) { 
            prime[cnt++] = i;
            for (int j = i; j <= n; j += i) {
                st[j] = true;
            }
        }
    } 
}
```

