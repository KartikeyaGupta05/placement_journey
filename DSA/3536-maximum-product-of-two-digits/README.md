# [Maximum Product of Two Digits](https://leetcode.com/problems/maximum-product-of-two-digits/)

🟢 Easy

**Topics**: Math, Sorting

## Problem Summary

Given a positive integer `n`, the task is to find the maximum product that can be formed by multiplying any two digits present in `n`. The problem statement clarifies that if a digit appears multiple times, it can be used more than once to form the product. For instance, if `n=22`, the digits are `[2, 2]`, and the maximum product is `2 * 2 = 4`.

## Approach

To achieve the maximum product of two digits, we intuitively need to find the two largest digits available within the given number `n`. We can iterate through the digits of `n` and keep track of the two largest digits encountered so far.

The strategy involves:
1.  Initializing two variables, `max1` and `max2`, to `0`. `max1` will store the largest digit found, and `max2` will store the second largest.
2.  Extracting digits from `n` one by one using the modulo operator (`% 10`) and integer division (`/ 10`).
3.  For each extracted digit:
    *   If the digit is greater than `max1`, it means we found a new largest digit. The current `max1` then becomes the new `max2`, and the new digit becomes `max1`.
    *   If the digit is not greater than `max1` but is greater than `max2`, it means we found a new second-largest digit. In this case, the digit replaces `max2`.
4.  Once all digits of `n` have been processed, the product `max1 * max2` will be the maximum product of any two digits.

This approach efficiently finds the two largest digits without needing to store all digits in a collection or sort them, leading to optimal space complexity.

## Algorithm Walkthrough (n = 124)

Let's trace the algorithm with `n = 124`.

1.  **Initialization**:
    *   `max1 = 0` (largest digit found so far)
    *   `max2 = 0` (second largest digit found so far)

2.  **Process `n = 124`**:
    *   **Extract `digit = 4`**:
        *   `4 > max1 (0)` is true.
        *   Update: `max2 = max1` (so `max2 = 0`), then `max1 = 4`.
        *   Current state: `max1 = 4`, `max2 = 0`.
        *   Update `n`: `n = 124 / 10 = 12`.
    *   **Extract `digit = 2`**:
        *   `2 > max1 (4)` is false.
        *   `2 > max2 (0)` is true.
        *   Update: `max2 = 2`.
        *   Current state: `max1 = 4`, `max2 = 2`.
        *   Update `n`: `n = 12 / 10 = 1`.
    *   **Extract `digit = 1`**:
        *   `1 > max1 (4)` is false.
        *   `1 > max2 (2)` is false.
        *   Current state: `max1 = 4`, `max2 = 2`. (No change)
        *   Update `n`: `n = 1 / 10 = 0`.

3.  **Loop Termination**: `n` is now `0`, so the loop finishes.

4.  **Result**: The maximum product is `max1 * max2 = 4 * 2 = 8`.

## Complexity Analysis

Let `k` be the number of digits in `n`. For `n <= 10^9`, `k` is at most 10.

*   **Time Complexity**:
    The algorithm iterates through each digit of `n` exactly once. The number of iterations is equal to the number of digits `k`. Since `k` is a very small constant (maximum 10 for `n = 10^9`), the time complexity is effectively constant.
    *   O(k) or O(log10 n), which simplifies to **O(1)**.

*   **Space Complexity**:
    The algorithm only uses a few constant-space variables (`max1`, `max2`, and a temporary variable for the current digit). It does not use any data structures whose size depends on the input `n`.
    *   **O(1)**.