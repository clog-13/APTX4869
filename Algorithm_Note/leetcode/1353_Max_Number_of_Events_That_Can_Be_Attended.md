# 1353. Maximum Number of Events That Can Be Attended
Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.

You can attend an event i at any day d where startTimei <= d <= endTimei. **Notice that you can only attend one event at any time d.**

Return the maximum number of events you can attend.

 

**Example:**
```
Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.
```


## 贪心 + 并查集
```java
import java.util.*;

class Solution {
    int[] f = new int[100010];
    public int maxEvents(int[][] events) {  // 结束天，开始天升序
        Arrays.sort(events, (a, b) -> (a[1]==b[1] ? a[0]-b[0] : a[1]-b[1]));
        for (int i = 0; i < f.length; i++) f[i] = i;

        int res = 0;
        for (int[] arr: events) {
            int leisure = find(arr[0]);
            if (leisure <= arr[1]) {
                res++;
                f[leisure] = leisure + 1;
            }
        }
        return res;
    }

    private int find(int x) {
        return f[x] == x ? x : (f[x] = find(f[x]));
    }
}
```