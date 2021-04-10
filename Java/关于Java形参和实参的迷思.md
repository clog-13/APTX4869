```java
class Solution {
    public static void main(String[] args) {
//        int[] a = {1,1};
//        h(a);
//        System.out.println(a[0]);

//        int[][] aa = {{1,1}, {1,1}};
//        hh(aa);
//        System.out.println(aa[0][0]);

//        int[][] bb = {{3,3}, {3,3}};
//        uu(bb);
//        System.out.println(bb[0][0]);

//        Outer o = new Outer(1);
//        oo(o);
//        System.out.println(o.x);

//        Inner i = new Inner(3);
//        ii(i);
//        System.out.println(i.x);

        Outer o2 = new Outer(1);
        or(o2);
        System.out.println(o2.arr[0]);
        System.out.println(o2.dd[0][0]);
    }

    public static void h(int[] nums) {
        System.out.println(nums[0]);
        nums[0] = 2; nums[1] = 2;
//        int[] b = {2,2};
//        nums = b;
        System.out.println(nums[0]);
    }

    public static void hh(int[][] nums) {
        System.out.println(nums[0][0]);
        int[] b = {2,2,2};
        nums[0] = b; nums[1] = b;
        System.out.println(nums[0][0]);
    }

    public static void uu(int[][] nums) {
        System.out.println(nums[0][0]);
        int[][] b = {{4,4},{4,4}};
        nums = b;
        System.out.println(nums[0][0]);
    }

    public static void oo(Outer o) {
        System.out.println(o.x);
        o.x = 2;
//        Outer tmp = new Outer(2);
//        o = tmp;
        System.out.println(o.x);
    }

    public static void ii(Inner i) {
        System.out.println(i.x);
        Inner tmp = new Inner(4);
        i = tmp;
        System.out.println(i.x);
    }

    public static void or(Outer o) {
        System.out.println(o.arr[0]);
        System.out.println(o.dd[0][0]);
        int[] brr = new int[]{2,2};
        o.arr = brr;
        int[][] ddd = new int[][]{{2,2},{2,2}};
        o.dd = ddd;
        System.out.println(o.arr[0]);
        System.out.println(o.dd[0][0]);
    }

    static class Inner {
        int x;
        int[] arr;
        Inner(int xx) {
            x = xx;
            arr = new int[]{3,3};
        }
    }
}

class Outer {
    int x;
    int[] arr;
    int[][] dd;
    Outer(int xx) {
        x = xx;
        arr = new int[]{1,1};
        dd = new int[][]{{1,1},{1,1}};
    }
}

```

