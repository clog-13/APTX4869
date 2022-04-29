# 93. Restore IP Addresses

A **valid IP address** consists of exactly four integers separated by single dots. Each integer is between `0` and `255` (**inclusive**) and cannot have leading zeros.

- For example, `"0.1.2.201"` and `"192.168.1.1"` are **valid** IP addresses, but `"0.011.255.245"`, `"192.168.1.312"` and `"192.168@1.1"` are **invalid** IP addresses.

Given a string `s` containing only digits, return *all possible valid IP addresses that can be formed by inserting dots into* `s`. You are **not** allowed to reorder or remove any digits in `s`. You may return the valid IP addresses in **any** order.

 

**Example 1:**

```
Input: s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
```



## Mono

```go
const SEG_COUNT = 4

var (
    res []string
    segments []int
)

func restoreIpAddresses(s string) []string {
    segments = make([]int, SEG_COUNT)
    res = []string{}
    dfs(s, 0, 0)
    return res
}

func dfs(s string, segId, segStart int) {
    // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
    if segId == SEG_COUNT {
        if segStart == len(s) {
            ipAddr := ""
            for i := 0; i < SEG_COUNT; i++ {
                ipAddr += strconv.Itoa(segments[i])
                if i != SEG_COUNT - 1 {
                    ipAddr += "."
                }
            }
            res = append(res, ipAddr)
        }
        return
    }

    if segStart == len(s) { // 还没有找到4段IP地址就遍历完了字符串，提前回溯
        return
    }

    if s[segStart] == '0' { // 不能有前导零，这一段 IP 地址只能为 0
        segments[segId] = 0
        dfs(s, segId + 1, segStart + 1)
    }
    
    addr := 0 // 一般情况，枚举每一种可能性并递归
    for segEnd := segStart; segEnd < len(s); segEnd++ {
        addr = addr * 10 + int(s[segEnd] - '0')
        if addr > 0 && addr <= 0xFF {
            segments[segId] = addr
            dfs(s, segId + 1, segEnd + 1)
        } else {
            break // !!!
        }
    }
}
```

