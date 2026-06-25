# Count Subarrays With Majority Element I

🔗 [LeetCode Problem Link](https://leetcode.com/problems/count-subarrays-with-majority-element-i/)

## 🟡 Medium

**Topics:** Array, Counting

## Problem Summary

This problem asks us to count the number of subarrays within a given integer array `nums` where a specific `target` integer is the majority element. A majority element is defined as an element that appears strictly more than half the times in that particular subarray.

## Approach

The core idea is to transform the problem into counting subarrays with a positive sum. For any element `num` in a subarray, we assign `+1` if `num` is equal to `target`, and `-1` if `num` is not equal to `target`. If `target` is the majority element in a subarray, it means the count of `target` elements is strictly greater than the count of non-`target` elements. In our transformed representation, this translates to the sum of elements in that subarray being strictly positive.

We can iterate through all possible subarrays using nested loops. For each subarray `nums[i..j]`, we maintain a `balance` variable. This `balance` variable is incremented by `1` if `nums[k]` (for `k` from `i` to `j`) is `target`, and decremented by `1` otherwise. If, at any point, the `balance` becomes strictly greater than `0`, it means `target` is currently the majority element in the subarray `nums[i..k]`, and we increment our total count.

## Algorithm Walkthrough

Let's use `nums = [1,2,2,3]` and `target = 2`.
Initialize `totalCount = 0`.

1.  **Outer loop (start `i`)**:
    *   **`i = 0` (subarray starts at index 0)**:
        *   Initialize `balance = 0`.
        *   **Inner loop (end `j`)**:
            *   **`j = 0`**: `nums[0] = 1`. `1 != target`. `balance = -1`. `balance > 0` is false.
            *   **`j = 1`**: `nums[1] = 2`. `2 == target`. `balance = -1 + 1 = 0`. `balance > 0` is false.
            *   **`j = 2`**: `nums[2] = 2`. `2 == target`. `balance = 0 + 1 = 1`. `balance > 0` is true. `totalCount = 1`. (Subarray: `[1,2,2]`)
            *   **`j = 3`**: `nums[3] = 3`. `3 != target`. `balance = 1 - 1 = 0`. `balance > 0` is false.
    *   **`i = 1` (subarray starts at index 1)**:
        *   Initialize `balance = 0`.
        *   **Inner loop (end `j`)**:
            *   **`j = 1`**: `nums[1] = 2`. `2 == target`. `balance = 1`. `balance > 0` is true. `totalCount = 2`. (Subarray: `[2]`)
            *   **`j = 2`**: `nums[2] = 2`. `2 == target`. `balance = 1 + 1 = 2`. `balance > 0` is true. `totalCount = 3`. (Subarray: `[2,2]`)
            *   **`j = 3`**: `nums[3] = 3`. `3 != target`. `balance = 2 - 1 = 1`. `balance > 0` is true. `totalCount = 4`. (Subarray: `[2,2,3]`)
    *   **`i = 2` (subarray starts at index 2)**:
        *   Initialize `balance = 0`.
        *   **Inner loop (end `j`)**:
            *   **`j = 2`**: `nums[2] = 2`. `2 == target`. `balance = 1`. `balance > 0` is true. `totalCount = 5`. (Subarray: `[2]`)
            *   **`j = 3`**: `nums[3] = 3`. `3 != target`. `balance = 1 - 1 = 0`. `balance > 0` is false.
    *   **`i = 3` (subarray starts at index 3)**:
        *   Initialize `balance = 0`.
        *   **Inner loop (end `j`)**:
            *   **`j = 3`**: `nums[3] = 3`. `3 != target`. `balance = -1`. `balance > 0` is false.

2.  After checking all subarrays, the final `totalCount` is `5`. This matches Example 1.

## Complexity Analysis

*   **Time Complexity**: `O(N^2)`
    The solution involves two nested loops. The outer loop iterates `N` times (for `i` from `0` to `N-1`), and the inner loop iterates up to `N` times (for `j` from `i` to `N-1`). Inside the inner loop, operations are constant time. Therefore, the total time complexity is `O(N*N) = O(N^2)`. Given `N <= 1000`, `N^2` operations (approx `10^6`) are well within typical time limits.

*   **Space Complexity**: `O(1)`
    The algorithm uses only a few auxiliary variables (`totalCount`, `balance`, `i`, `j`), which occupy a constant amount of space regardless of the input array size.