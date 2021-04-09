# Binary search二分查找

## 查找一个数

```java
int binarySearch(int[] nums, int tar) {
    int le = 0, ri = nums.length - 1;  // ##

    while (le <= ri) {
        int mid = (ri + le) >> 1;
        if (nums[mid] == tar) {
            return mid;
        } else if (nums[mid] < tar) {
            le = mid + 1;
        } else if (nums[mid] > tar) {
            ri = mid - 1;  // ##
        }
    }
    return -1;
}
```



## 寻找左侧边界

```java
int left_bound(int[] nums, int tar) {
    int le = 0, ri = nums.length;  // ##

    while (le < ri) {
        int mid = (le + ri) >> 1;
        if (nums[mid] == tar) {
            ri = mid;  // ##
        } else if (nums[mid] < tar) {
            le = mid + 1;
        } else if (nums[mid] > tar) {
            ri = mid;
        }
    }
    return le;
}
```



## 寻找右侧边界

```java
int right_bound(int[] nums, int tar) {
    int le = 0, ri = nums.length;  // ##

    while (le < ri) {
        int mid = (le + ri) >> 1;
        if (nums[mid] == tar) {
            le = mid + 1;  // ##
        } else if (nums[mid] < tar) {
            le = mid + 1;
        } else if (nums[mid] > tar) {
            ri = mid;
        }
    }
    return le - 1;  // ##
}
```
