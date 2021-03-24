# 1792. 最大平均通过率

一所学校里有一些班级，每个班级里有一些学生，现在每个班都会进行一场期末考试。给你一个二维数组 `classes` ，其中 `classes[i] = [passi, totali]` ，表示你提前知道了第 `i` 个班级总共有 `totali` 个学生，其中只有 `passi` 个学生可以通过考试。

给你一个整数 `extraStudents` ，表示额外有 `extraStudents` 个聪明的学生，他们 **一定** 能通过任何班级的期末考。你需要给这 `extraStudents` 个学生每人都安排一个班级，使得 **所有** 班级的 **平均** 通过率 **最大** 。

一个班级的 **通过率** 等于这个班级通过考试的学生人数除以这个班级的总人数。**平均通过率** 是所有班级的通过率之和除以班级数目。

请你返回在安排这 `extraStudents` 个学生去对应班级后的 **最大** 平均通过率。

 

**示例：**

```
输入：classes = [[1,2],[3,5],[2,2]], extraStudents = 2
输出：0.78333
解释：你可以将额外的两个学生都安排到第一个班级，平均通过率为 (3/4 + 3/5 + 2/2) / 3 = 0.78333 。
```



## 贪心 + 优先队列

```java
class Solution {
    public double maxAverageRatio(int[][] classes, int number) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> // 降序
            Double.compare((b[0]+1D)/(b[1]+1D) - b[0]*1D/b[1], (a[0]+1D)/(a[1]+1D) - a[0]*1D/a[1])
        );
        for (int[] a : classes) queue.add(a);
        
        for (int i = 0; i < number; i++) {
            int[] a = queue.poll();
            queue.add(new int[]{a[0] + 1, a[1] + 1});
        }

        double sum = 0D;
        for (int[] a : queue) sum += a[0]*1D / a[1];
        return sum / classes.length;
    }
}
```

```java
class Solution {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        double res = 0;
        PriorityQueue<Cla> q = new PriorityQueue<>();

        for (int[] t : classes) {
            int a = t[1], b = t[0];
            double d = (double) (a - b) / (a * (a + 1.0));//注意，这里要写成1.0，不然会有精度的缺失
            res += (double) b / a;
            q.add(new Cla(a, b, d));
        }

        while (--extraStudents >= 0) {
            Cla cur = q.poll();
            cur.a += 1; cur.b += 1;
            int a = cur.a, b = cur.b;
            res += cur.d;//说明这个班级要增加d这么多通过率
            cur.d = (double) (a - b) / (a * (a + 1.0));
            q.add(cur);
        }

        return res / classes.length;
    }

    static class Cla implements Comparable<Cla> {
        double d;//增量
        int a, b;//总人数与通过人数
        public Cla(int a, int b, double d) {
            this.a = a;
            this.b = b;
            this.d = d;
        }
        public int compareTo(Cla o1) {
            if (this.d <= o1.d)
                return 1;
            return -1;
        }
    }
}
```

