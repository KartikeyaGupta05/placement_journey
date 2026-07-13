# [Sequential Digits](https://leetcode.com/problems/sequential-digits/)

🟡 Medium

Topics: Enumeration

## Problem Summary

The problem asks us to find all integers within a specified range `[low, high]` that exhibit "sequential digits." An integer has sequential digits if each digit is exactly one more than the preceding digit (e.g., 123, 4567). The final list of these numbers must be returned in sorted order.

## Approach

The core idea is to systematically generate all possible sequential digit numbers and then filter those that fall within the given `[low, high]` range. Since the maximum value for `high` is `10^9`, the largest sequential digit number we need to consider is `123456789`, which has 9 digits. This implies a fixed, small number of possible sequential digit numbers.

We can achieve this by iterating through all possible starting digits (1 through 9). For each starting digit, we build subsequent digits by incrementing the previous digit. For example, if the starting digit is 1, we first form 12, then 123, then 1234, and so on, until the next digit would exceed 9.

During the generation process, we continuously check if the `current_num` formed is within the `[low, high]` bounds. If it is, we add it to a temporary list. An important optimization is to `break` early: if `current_num` exceeds `high`, we can stop extending it and move to the next starting digit, as any further sequential numbers built from it will also be greater than `high`.

Finally, after generating all candidates, the collected sequential numbers are sorted before being returned.

## Algorithm Walkthrough

Let's use `low = 100`, `high = 300` as an example.

1.  Initialize an empty list `result`.
2.  **Outer loop: Iterate `start_digit` from 1 to 9.**

    *   **`start_digit = 1`**:
        *   `current_num = 1`.
        *   **Inner loop: Iterate `next_digit` from `start_digit + 1` (i.e., 2) to 9.**
            *   `next_digit = 2`: `current_num = 1 * 10 + 2 = 12`.
                *   `12` is not in `[100, 300]`.
            *   `next_digit = 3`: `current_num = 12 * 10 + 3 = 123`.
                *   `123` is in `[100, 300]`. Add `123` to `result`. `result = [123]`.
            *   `next_digit = 4`: `current_num = 123 * 10 + 4 = 1234`.
                *   `1234` is not in `[100, 300]`.
                *   `1234 > high (300)`, so we `break` from the inner loop for `start_digit = 1`.
    *   **`start_digit = 2`**:
        *   `current_num = 2`.
        *   **Inner loop: Iterate `next_digit` from `start_digit + 1` (i.e., 3) to 9.**
            *   `next_digit = 3`: `current_num = 2 * 10 + 3 = 23`.
                *   `23` is not in `[100, 300]`.
            *   `next_digit = 4`: `current_num = 23 * 10 + 4 = 234`.
                *   `234` is in `[100, 300]`. Add `234` to `result`. `result = [123, 234]`.
            *   `next_digit = 5`: `current_num = 234 * 10 + 5 = 2345`.
                *   `2345` is not in `[100, 300]`.
                *   `2345 > high (300)`, so we `break` from the inner loop for `start_digit = 2`.
    *   **`start_digit = 3`**:
        *   `current_num = 3`.
        *   **Inner loop:**
            *   `next_digit = 4`: `current_num = 34`. Not in range.
            *   `next_digit = 5`: `current_num = 345`. Not in range. `345 > high (300)`, `break`.
    *   ... (This process continues for `start_digit` up to 9. For `start_digit = 9`, the inner loop won't run as `next_digit` would start at 10.)

3.  After all iterations, `result = [123, 234]`.
4.  Sort `result`. In this case, it is already sorted.
5.  Return `[123, 234]`.

## Complexity Analysis

*   **Time Complexity**: `O(1)`
    The number of possible sequential digit numbers is very small and constant. The longest sequential digit number is 123456789 (9 digits). There are only 36 such numbers (12, 23, ..., 89; 123, ..., 789; ...; 123456789). Generating each number involves a loop proportional to its number of digits (at most 9 operations). Therefore, the total time to generate all possible numbers is a constant amount of work. Sorting a list of at most 36 numbers is also constant time, `O(36 log 36)`. Thus, the overall time complexity is constant.

*   **Space Complexity**: `O(1)`
    We store a list of at most 36 integers. This number is fixed and does not depend on the input `low` or `high` beyond which specific numbers are included. Therefore, the space complexity is constant.