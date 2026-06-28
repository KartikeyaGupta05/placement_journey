# [Valid Subarrays With Matching Sum Digits I](https://leetcode.com/problems/valid-subarrays-with-matching-sum-digits-i/)

🟡 Medium

Topics: Arrays, Iteration

## Problem Summary

This problem asks us to count the number of "valid" subarrays within a given integer array `nums`. A subarray is considered valid if the first digit of its sum is equal to a given integer `x`, and its last digit is also equal to `x`. We need to return the total count of such valid subarrays.

## Approach

The core idea is to systematically iterate through all possible subarrays, calculate their sum, and then check if the sum satisfies the given digit conditions.

1.  **Iterate Subarrays**: We can use a nested loop structure to generate all possible subarrays `nums[l..r]`. The outer loop iterates `l` (left pointer) from `0` to `nums.length - 1`. The inner loop iterates `r` (right pointer) from `l` to `nums.length - 1`.

2.  **Calculate Sum**: For each subarray `nums[l..r]`, we maintain a `currentSum`. Instead of recalculating the sum from scratch for each `(l, r)` pair, we can update `currentSum` incrementally. When `r` advances, we simply add `nums[r]` to the `currentSum`. It's important to use a `long` data type for `currentSum` because the sum of elements can exceed the maximum value of an `int` (`1500 * 10^9 = 1.5 * 10^12`).

3.  **Check Digit Conditions**: For each `currentSum`:
    *   **Last Digit**: The last digit of `currentSum` can be easily obtained using the modulo operator: `currentSum % 10`.
    *   **First Digit**: To find the first digit, we can repeatedly divide `currentSum` by `10` until it becomes a single-digit number (i.e., `currentSum < 10`). This final single-digit number will be the first digit. A temporary variable should be used for this division to preserve the original `currentSum` for the next iteration.
    *   If both the calculated first digit and last digit match the given `x`, we increment our counter for valid subarrays.

This approach ensures that every possible subarray is considered exactly once, and its sum is efficiently computed.

## Algorithm Walkthrough

Let's trace the execution with `nums = [1, 100, 1]` and `x = 1`.

Initialize `count = 0`.

1.  **`l = 0`**:
    *   `currentSum = 0`
    *   **`r = 0`**:
        *   `currentSum += nums[0]` => `currentSum = 1`
        *   `lastDigit = 1 % 10 = 1`
        *   `tempSum = 1`. `tempSum < 10`, so `firstDigit = 1`
        *   `firstDigit == 1` and `lastDigit == 1` (both `== x`). **Valid!** `count = 1`.
    *   **`r = 1`**:
        *   `currentSum += nums[1]` => `currentSum = 1 + 100 = 101`
        *   `lastDigit = 101 % 10 = 1`
        *   `tempSum = 101`. `tempSum = 101 / 10 = 10`. `tempSum = 10 / 10 = 1`. `firstDigit = 1`
        *   `firstDigit == 1` and `lastDigit == 1` (both `== x`). **Valid!** `count = 2`.
    *   **`r = 2`**:
        *   `currentSum += nums[2]` => `currentSum = 101 + 1 = 102`
        *   `lastDigit = 102 % 10 = 2`
        *   `tempSum = 102`. `tempSum = 102 / 10 = 10`. `tempSum = 10 / 10 = 1`. `firstDigit = 1`
        *   `firstDigit == 1` but `lastDigit == 2` (not `== x`). Not valid. `count` remains `2`.

2.  **`l = 1`**:
    *   `currentSum = 0`
    *   **`r = 1`**:
        *   `currentSum += nums[1]` => `currentSum = 100`
        *   `lastDigit = 100 % 10 = 0`
        *   `tempSum = 100`. `tempSum = 100 / 10 = 10`. `tempSum = 10 / 10 = 1`. `firstDigit = 1`
        *   `firstDigit == 1` but `lastDigit == 0` (not `== x`). Not valid. `count` remains `2`.
    *   **`r = 2`**:
        *   `currentSum += nums[2]` => `currentSum = 100 + 1 = 101`
        *   `lastDigit = 101 % 10 = 1`
        *   `tempSum = 101`. `tempSum = 101 / 10 = 10`. `tempSum = 10 / 10 = 1`. `firstDigit = 1`
        *   `firstDigit == 1` and `lastDigit == 1` (both `== x`). **Valid!** `count = 3`.

3.  **`l = 2`**:
    *   `currentSum = 0`
    *   **`r = 2`**:
        *   `currentSum += nums[2]` => `currentSum = 1`
        *   `lastDigit = 1 % 10 = 1`
        *   `tempSum = 1`. `tempSum < 10`, so `firstDigit = 1`
        *   `firstDigit == 1` and `lastDigit == 1` (both `== x`). **Valid!** `count = 4`.

After checking all subarrays, the final `count` is `4`.

## Complexity Analysis

*   **Time Complexity**: `O(N^2)`
    *   The algorithm involves two nested loops. The outer loop runs `N` times (for `l`). The inner loop runs `N-l` times (for `r`).
    *   Inside the inner loop, arithmetic operations (addition, modulo, division) and the `while` loop for finding the first digit are performed. The `while` loop iterates `log10(sum)` times, which is at most `log10(1.5 * 10^12) ≈ 12` times (a constant number of operations).
    *   Therefore, the dominant factor is the nested loops, leading to `O(N * N) = O(N^2)` complexity. Given `N <= 1500`, `N^2` is `2.25 * 10^6`, which is well within typical time limits.

*   **Space Complexity**: `O(1)`
    *   The algorithm uses a few variables to store `count`, `currentSum`, `lastDigit`, `firstDigit`, and `tempSum`. These variables occupy a constant amount of space regardless of the input array size.