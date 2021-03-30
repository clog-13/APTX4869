# 面试题 16.21. 交换和

给定两个整数数组，请交换一对数值（每个数组中取一个数值），使得两个数组所有元素的和相等。

返回一个数组，第一个元素是第一个数组中要交换的元素，第二个元素是第二个数组中要交换的元素。若有多个答案，返回任意一个均可。若无满足条件的数值，返回空数组。

**示例:**

```
输入: array1 = [4, 1, 2, 1, 1, 2], array2 = [3, 6, 3, 3]
输出: [1, 3]
```



## 双指针

```java
class Solution {
    public int[] findSwapValues(int[] array1, int[] array2) {
		int sum1=0,sum2=0;
		for(int i:array1) sum1+=i;
		for(int i:array2) sum2+=i;
		int d=sum1-sum2;
		Arrays.sort(array1);
		Arrays.sort(array2);
		int i=0,j=0;
		while(i<array1.length&&j<array2.length) {
			if((array1[i]-array2[j])*2==d) return new int[] {array1[i],array2[j]};
			else if((array1[i]-array2[j])*2<d) {
                i++;
                while (i < array1.length && array1[i] == array1[i-1]) i++;
            } else {
                j++;
                while (j < array2.length && array2[j] == array2[j-1]) j++;
            }
		}
		return new int[] {};
    }
}
```



## Hash

```java
class Solution {
    public int[] findSwapValues(int[] array1, int[] array2) {
        HashMap<Integer, Integer> lookup = new HashMap<>();
        int[] res=new int[0];
        int sum1 = 0, sum2 = 0;

        for(int i=0; i<array1.length; i++) sum1 += array1[i];
        for(int j=0; j<array2.length; j++) sum2 += array2[j];

        int gap = sum1 - sum2;
        if (gap % 2 != 0){
            return res;
        }

        int diff = gap / 2; 

        for(int i=0; i<array1.length; i++) {
            if(!lookup.containsKey(array1[i])) {
                lookup.put(array1[i], i);
            }
        }
        for(int j=0; j<array2.length; j++) {
            int target = array2[j] + diff;
            if(lookup.containsKey(target)){
                res = new int[]{target, array2[j]};
                return res;
            }
        }
        
        return res;

    }
}
```

