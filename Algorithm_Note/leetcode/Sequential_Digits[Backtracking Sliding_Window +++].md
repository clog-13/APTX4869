#1291. 顺次数
我们定义「顺次数」为：每一位上的数字都比前一位上的数字大 1 的整数。

请你返回由 [low, high] 范围内所有顺次数组成的 有序 列表（从小到大排序）。

**示例：**
输出：low = 1000, high = 13000
输出：[1234,2345,3456,4567,5678,6789,12345]

##枚举
```java
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 9; ++i) {
            int num = i;
            for (int j = i + 1; j <= 9; ++j) {
                num = num * 10 + j;
                if (num >= low && num <= high) {
                    res.add(num);
                }
            }
        }
        Collections.sort(res);
        return res;
    }
}
```
##滑动窗口
```java
class Solution {
    String A = "123456789";
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        String lo = low +"";
        String hi = high+"";
        for(int len = lo.length(); len <= hi.length(); len++) {
            for(int i = 0; i <= 9-len; i++) {
                String tmp = A.substring(i, i+len);
                int cmp = Integer.valueOf(tmp);
                if(cmp >= low && cmp <= high) res.add(cmp);
            }
        }
        return res;
    }
}
```
##回溯
```java
class Solution {
    int lo, hi;
    char[] num = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public List<Integer> sequentialDigits(int low, int high) {
        lo = low; hi = high;
        List<Integer> res = new LinkedList<>();
        StringBuilder temp = new StringBuilder("0");
        backtrack(0, num.length, res, temp);
        Collections.sort(res);
        return res;
    }
    public void backtrack(int start, int end, List<Integer> res, StringBuilder temp){
        int n = Integer.parseInt(new String(temp));
        if(n>=lo && n<=hi)
            res.add(n);

        for(int i=start; i<end; i++){
            // 注意向后剪枝时，只需要考虑最后一个元素的下标不要越界即可
            if(i == num.length)
                break;

            temp.append(num[i]);
            backtrack(i+1, i+2, res, temp);
            temp.deleteCharAt(temp.length()-1);
        }
    }
}
```