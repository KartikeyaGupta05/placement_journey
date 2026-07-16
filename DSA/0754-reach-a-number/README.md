# [Reach a Number](https://leetcode.com/problems/reach-a-number/)

🟡 Medium

**Topics:** Math, Binary Search

## Problem Summary

The problem asks us to find the minimum number of moves required to reach a specific target position on an infinite number line. Starting from 0, the *i*-th move involves taking exactly `i` steps either to the left or right. We need to determine the smallest `numMoves` to land exactly on the `target` position.

## Approach

The core idea is to realize that the problem is symmetric: reaching `target` is equivalent to reaching `-target`. Therefore, we can simplify the problem by working with `abs(target)`.

1.  **Iterative Summation:** We start at position 0. On the 1st move, we take 1 step; on the 2nd, 2 steps, and so on. We are essentially accumulating a sum `1 + 2 + ... + k`. We need to find the smallest `k` (number of moves) such that our accumulated sum, `current_sum = k * (k + 1) / 2`, is greater than or equal to `abs(target)`. We can find this `k` by iteratively adding `i` to `current_sum` and incrementing `k` until `current_sum >= abs(target)`.

2.  **Parity Check for Adjustment:** Once we have `k` and `current_sum`, there are three cases:
    *   **Case 1: `current_sum == abs(target)`**
        If the accumulated sum exactly matches `abs(target)`, then `k` is our answer. We can reach `target` in `k` moves by taking all steps in the direction of `target`.
    *   **Case 2: `current_sum > abs(target)`**
        We have overshot the target. The difference `diff = current_sum - abs(target)` must be an even number.
        Why? To reduce our total sum from `current_sum` to `abs(target)`, we need to "flip" the direction of some steps. If we change a `+j` step to a `-j` step, the total sum decreases by `2j`. This means any adjustment to the final position by flipping signs will always result in a change that is an even number.
        Therefore, if `diff` is even, we can achieve `abs(target)` in `k` moves by changing the direction of some specific steps that sum up to `diff / 2`.
    *   **Case 3: `current_sum > abs(target)` but `diff` is odd**
        If `diff = current_sum - abs(target)` is odd, we cannot reach `abs(target)` in `k` moves because we can only adjust by even amounts. In this scenario, we need to make more moves.
        *   Consider `k+1` moves: The new sum would be `current_sum + (k+1)`. The new difference would be `(current_sum + k + 1) - abs(target) = diff + k + 1`.
            *   If `k` is even, then `k+1` is odd. `diff + k + 1` would be `odd + odd = even`. So, `k+1` moves would be sufficient.
            *   If `k` is odd, then `k+1` is even. `diff + k + 1` would be `odd + even = odd`. In this case, `k+1` moves are still not enough.
        *   Consider `k+2` moves (only if `k+1` moves failed, i.e., `k` was odd): The new sum would be `current_sum + (k+1) + (k+2)`. The new difference would be `(current_sum + k + 1 + k + 2) - abs(target) = diff + k + 1 + k + 2`.
            *   If `k` is odd, then `k+1` is even and `k+2` is odd. `diff + k + 1 + k + 2` would be `odd + even + odd = even`. So, `k+2` moves would be sufficient.

    In summary, after finding the minimum `k` such that `current_sum >= abs(target)`:
    *   If `(current_sum - abs(target))` is even, the answer is `k`.
    *   Else if `k` is even, the answer is `k+1`.
    *   Else (`k` is odd), the answer is `k+2`.

## Algorithm Walkthrough

Let's use `target = 2` as an example.

1.  **Absolute Target**: `t = abs(2) = 2`.
2.  **Iterative Summation**:
    *   Initialize `k = 0`, `current_sum = 0`.
    *   **Move 1**: `k` becomes 1. `current_sum = 0 + 1 = 1`.
        `current_sum (1)` is less than `t (2)`. Continue.
    *   **Move 2**: `k` becomes 2. `current_sum = 1 + 2 = 3`.
        `current_sum (3)` is greater than or equal to `t (2)`. Stop.
3.  **Check Parity**:
    *   We have `k = 2` and `current_sum = 3`.
    *   Calculate `diff = current_sum - t = 3 - 2 = 1`.
    *   `diff` (1) is odd.
    *   Since `diff` is odd, we check `k`'s parity. `k` (2) is even.
    *   According to the logic, if `diff` is odd and `k` is even, the answer is `k+1`.
    *   Result: `2 + 1 = 3`.

This matches Example 1: `target = 2` yields `Output: 3`.

---

Let's use `target = 3` as another example.

1.  **Absolute Target**: `t = abs(3) = 3`.
2.  **Iterative Summation**:
    *   Initialize `k = 0`, `current_sum = 0`.
    *   **Move 1**: `k` becomes 1. `current_sum = 0 + 1 = 1`.
        `current_sum (1)` is less than `t (3)`. Continue.
    *   **Move 2**: `k` becomes 2. `current_sum = 1 + 2 = 3`.
        `current_sum (3)` is greater than or equal to `t (3)`. Stop.
3.  **Check Parity**:
    *   We have `k = 2` and `current_sum = 3`.
    *   Calculate `diff = current_sum - t = 3 - 3 = 0`.
    *   `diff` (0) is even.
    *   According to the logic, if `diff` is even, the answer is `k`.
    *   Result: `2`.

This matches Example 2: `target = 3` yields `Output: 2`.

## Complexity Analysis

### Time Complexity
The loop iterates until `k * (k + 1) / 2 >= abs(target)`. This means `k^2` is approximately `2 * abs(target)`, so `k` is approximately `sqrt(2 * abs(target))`. For `target` up to `10^9`, `k` will be around `sqrt(2 * 10^9) = sqrt(20 * 10^8) \approx 4.47 * 10^4`. The number of iterations is proportional to `sqrt(target)`.
Therefore, the time complexity is **O(sqrt(target))**.

### Space Complexity
We only use a few integer variables (`target`, `k`, `current_sum`, `diff`).
Therefore, the space complexity is **O(1)**.