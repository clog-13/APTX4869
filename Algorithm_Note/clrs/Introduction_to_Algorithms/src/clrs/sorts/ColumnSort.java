package clrs.sorts;

import clrs.tools.Bools;

public class ColumnSort {

    public static void main(String[] args) {
        ColumnSort c = new ColumnSort(5, 50);
    }

    private int[][] nums;

    public ColumnSort(int col, int row) {
        this.nums = new int[col][row];
        for(int i = 0; i < col; i++)
            nums[i] = Bools.randomArray(1, 100, row);

        Bools.showList(ColumnSort(nums));
    }

    public static int[] ColumnSort(int[][] arr) {
        int col = arr.length;
        int row = arr[0].length;

        // 1.对每一列排序
        for(int[] a : arr)
            InsertionSort.InsertionSort(a);
//        showCol_R(arr);

        // 2.转置矩形数组
        int[][] tmp = new int[col][row];
        int base = 0;
        for(int i = 0; i < col; i++) {
            for(int j = 0; j < row; j++) {
                tmp[j % col][base + (j / col)] = arr[i][j];
            }
            base += row / col;
        }
//        showCol_R(tmp);

        // 3.对每一列排序
        for(int[] a : tmp)
            InsertionSort.InsertionSort(a);
//        showCol_R(tmp);

        // 4.对第2步进行逆操作
        base -= row/col;
        for(int i = tmp.length-1; i >= 0; i--) {
            for(int j = tmp[0].length-1; j >= 0; j--) {
                arr[i][j] = tmp[j % col][base + (j / col)];
            }
            base -= row/col;
        }
//        showCol_R(arr);

        // 5.对每一列排序
        for(int[] a : arr)
            InsertionSort.InsertionSort(a);
//        showCol_R(arr);

        // 6. 归并为一个数组
        int[] res = new int[arr.length * arr[0].length];
        int l = arr[0].length;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[0].length; j++) {
                res[i*l+j] = arr[i][j];
            }
        }
//        for(int i : res) System.out.print(i + ", ");
//        System.out.println("\n=====================================");

        // 7.排序
        InsertionSort.InsertionSort(res);

        return res;
    }

    public static void showCol(int[][] arr) {
        for(int[] a : arr) {
            for(int i : a) {
                System.out.print(i+", ");
            }
            System.out.println();
        }
    }

    public static void showCol_R(int[][] arr) {
        for(int j = arr.length-1; j >=0; j--) {
            for(int i : arr[j]) {
                System.out.print(i+", ");
            }
            System.out.println();
        }
        System.out.println("=====================================");
    }
}
