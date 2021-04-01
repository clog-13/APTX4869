# Binary search二分查找

## 查找一个数

```java
int binarySearch(int[] nums, int target) {
    int left = 0; 
    int right = nums.length - 1;

    while (left <= right) {
        int mid = (right + left) >> 1;
        if (nums[mid] == target) {
            return mid; 
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid - 1;
        }
    return -1;
}
```



## 寻找左侧边界

```java
int left_bound(int[] nums, int target) {
    int left = 0;
    int right = nums.length;

    while (left < right) {
        int mid = (left + right) >> 1;
        if (nums[mid] == target) {
            right = mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid;
        }
    }
    return left;
}
```



## 寻找右侧边界

```java
int right_bound(int[] nums, int target) {
    int left = 0;
    int right = nums.length;

    while (left < right) {
        int mid = (left + right) >> 1;
        if (nums[mid] == target) {
            left = mid + 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid;
        }
    }
    return left - 1;
}
```

