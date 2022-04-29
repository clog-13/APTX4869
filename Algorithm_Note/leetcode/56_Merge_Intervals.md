# 56. Merge Intervals
Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

**Example 1:**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
```

## preSum
```java
class Solution {
    public int[][] merge(int[][] intervals) {
        int max = 0;
        for (int[] n : intervals) max = Math.max(max, n[1]);
        
        int[] axis = new int[max + 1];  // 数轴axis，起始点的位置为正，终止点的位置为负
        boolean[] flag = new boolean[max + 1];  // 标准flag，记录形如[a,a]的点区间
        for (int[] n : intervals) {  // 初始化axis和flag
            axis[n[0]]++;
            axis[n[1]]--;
            if (n[0] == n[1]) flag[n[0]] = true;
        }

        /* 共有4种情况：
         *  k从零到正：这种情况表示是一个区间的开始
         *  k从正到正：这种情况表示有区间的嵌套
         *  k从正到零：这种情况表示是一个区间的结束
         *  k从零到零：这种情况表示没有区间或有一个点区间。
         */
        List<int[]> list = new ArrayList();
        int s = 0, k = 0;
        for (int i = 0; i <= max; i++) {
            if (k == 0) {
                if (axis[i] > 0) {  // 从零到正，记录区间的起点
                    s = i;
                    k += axis[i];
                } else if(flag[i]) {  // 从零到零，检验是否有点区间
                    list.add(new int[]{i, i});
                }
            } else {
                k += axis[i];  // 计算k的变化
                if (k == 0) {  // 如果是从正到零，说明是区间的结束，记录结束
                    list.add(new int[]{s, i});
                }
            }
        }

        int[][] res = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
```

## Mono
```java
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] n1 , int[] n2){
                return n1[0] - n2[0];
            }
        });

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < intervals.length; ) {
            int[] tmp = new int[2];
            tmp[0] = intervals[i][0];
            int j = i, max = intervals[i][1];
            while (j < intervals.length && intervals[j][0] <= max) {
                max = Math.max(max, intervals[j][1]);
                j++;
            }
            j--;
            if (i != j) {
                tmp[1] = Math.max(intervals[j][1], max);
            } else {
                tmp[1] = intervals[i][1];
            }
            list.add(tmp);
            i = j+1;
        }
        int[][] res = new int[list.size()][2];
        int i = 0;
        for (int[] n : list) {
            res[i][0] = n[0];
            res[i++][1] = n[1];
        }
        return res;
    }
}
```