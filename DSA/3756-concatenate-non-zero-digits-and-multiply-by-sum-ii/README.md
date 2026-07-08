# Concatenate Non-Zero Digits and Multiply by Sum II

## 🟡 Medium

## Topics
Math, String, Prefix Sum

## Problem Summary
Given a digit string `s` and a list of queries `[li, ri]`, for each query, we need to extract the substring `s[li..ri]`. From this substring, we then form an integer `x` by concatenating all its non-zero digits in their original relative order. If no non-zero digits exist, `x` is 0. The sum of digits of `x` is calculated. The final answer for the query is `x * sum`, returned modulo `10^9 + 7`.

## Approach
The problem involves processing multiple range queries on a string to derive a concatenated number and its digit sum. A naive approach of iterating through the substring for each query would be too slow, given the constraints (`s.length` and `queries.length` up to `10^5`). This indicates a need for a precomputation strategy, often involving prefix sums or a similar technique.

The key observation is that only non-zero digits contribute to the number `x`. The relative order of these non-zero digits within the query range determines `x`. We can optimize by first identifying all non-zero digits in the original string `s` along with their original indices.

Let's create an auxiliary list, say `nzEntries`, which stores objects or tuples, each containing a non-zero digit's value and its original index in `s`. For example, `s = "10203004"` would result in `nzEntries = [(1,0), (2,2), (3,4), (4,7)]`.

With `nzEntries`, we can precompute two prefix arrays:
1.  `prefixX[i]`: The value of the number formed by concatenating `nzEntries[0].value` through `nzEntries[i].value`. Each step involves `(currentX * 10 + nextDigit) % MOD`.
2.  `prefixSum[i]`: The sum of digits `nzEntries[0].value` through `nzEntries[i].value`. Each step involves `(currentSum + nextDigit) % MOD`.
We also need a `powersOf10` array to quickly calculate `10^k % MOD`, which will be used in the subtraction step for `prefixX`.

For each query `[li, ri]`:
1.  **Find relevant non-zero digits**: Use binary search (e.g., `lower_bound` and `upper_bound` equivalents) on `nzEntries` (based on `originalIndex`) to find the `start_nz_idx` (first non-zero digit with `originalIndex >= li`) and `end_nz_idx` (last non-zero digit with `originalIndex <= ri`).
2.  **Handle no non-zero digits**: If `start_nz_idx` is invalid (e.g., points beyond the list) or `start_nz_idx > end_nz_idx`, it means there are no non-zero digits in the range `s[li..ri]`. In this case, `x = 0`, `sum = 0`, and the answer is `0`.
3.  **Calculate X for the range**:
    *   The concatenated number `X_range` for `nzEntries[start_nz_idx ... end_nz_idx]` can be derived from `prefixX` values.
    *   `X_range = prefixX[end_nz_idx]`.
    *   If `start_nz_idx > 0`, we need to subtract the `prefixX[start_nz_idx - 1]` part, which was prepended with `10^(count)` where `count` is the number of digits in the current range (`end_nz_idx - start_nz_idx + 1`).
    *   Specifically, `X_range = (prefixX[end_nz_idx] - (prefixX[start_nz_idx - 1] * powersOf10[count]) % MOD + MOD) % MOD`. (The `+ MOD` inside ensures a positive result before the final modulo for negative subtractions).
4.  **Calculate Sum for the range**:
    *   The sum `Sum_range` is simpler: `Sum_range = prefixSum[end_nz_idx]`.
    *   If `start_nz_idx > 0`, `Sum_range = (prefixSum[end_nz_idx] - prefixSum[start_nz_idx - 1] + MOD) % MOD`.
5.  **Final Answer**: The result for the query is `(X_range * Sum_range) % MOD`.

All intermediate calculations involving multiplication should use `long` to prevent overflow before applying the modulo.

## Algorithm Walkthrough

Let's use `s = "10203004"` and `queries = [[0,7],[1,3],[4,6]]`. `MOD = 10^9 + 7`.

**1. Precomputation:**

*   **`nzEntries`**: List of `(value, originalIndex)` for non-zero digits:
    `[(1,0), (2,2), (3,4), (4,7)]`
*   **`prefixX` (concatenated values)**:
    *   `i=0 (1,0)`: `currentX = 1`. `prefixX[0] = 1`.
    *   `i=1 (2,2)`: `currentX = (1 * 10 + 2) % MOD = 12`. `prefixX[1] = 12`.
    *   `i=2 (3,4)`: `currentX = (12 * 10 + 3) % MOD = 123`. `prefixX[2] = 123`.
    *   `i=3 (4,7)`: `currentX = (123 * 10 + 4) % MOD = 1234`. `prefixX[3] = 1234`.
*   **`prefixSum` (sum of digits)**:
    *   `i=0 (1,0)`: `currentSum = 1`. `prefixSum[0] = 1`.
    *   `i=1 (2,2)`: `currentSum = (1 + 2) % MOD = 3`. `prefixSum[1] = 3`.
    *   `i=2 (3,4)`: `currentSum = (3 + 3) % MOD = 6`. `prefixSum[2] = 6`.
    *   `i=3 (4,7)`: `currentSum = (6 + 4) % MOD = 10`. `prefixSum[3] = 10`.
*   **`powersOf10`**: `[1, 10, 100, 1000, 10000, ...]` up to `s.length`.

**2. Process Queries:**

*   **Query 1: `[0,7]` (s[0..7] = "10203004")**
    *   `li=0`, `ri=7`.
    *   Binary search for `li=0` finds `(1,0)` at `start_nz_idx = 0`.
    *   Binary search for `ri=7` finds `(4,7)` at `end_nz_idx = 3`.
    *   `start_nz_idx <= end_nz_idx`, so valid range.
    *   `count = end_nz_idx - start_nz_idx + 1 = 3 - 0 + 1 = 4`.
    *   `X_range`: `prefixX[3] = 1234`. Since `start_nz_idx = 0`, no prefix subtraction is needed. So `X_range = 1234`.
    *   `Sum_range`: `prefixSum[3] = 10`. Since `start_nz_idx = 0`, no prefix subtraction is needed. So `Sum_range = 10`.
    *   Answer: `(1234 * 10) % MOD = 12340`.

*   **Query 2: `[1,3]` (s[1..3] = "020")**
    *   `li=1`, `ri=3`.
    *   Binary search for `li=1` finds `(2,2)` at `start_nz_idx = 1`.
    *   Binary search for `ri=3` finds `(2,2)` at `end_nz_idx = 1`.
    *   `start_nz_idx <= end_nz_idx`, so valid range.
    *   `count = end_nz_idx - start_nz_idx + 1 = 1 - 1 + 1 = 1`.
    *   `X_range`:
        *   `prefixX[1] = 12`.
        *   `start_nz_idx = 1 (>0)`. `prefixX[start_nz_idx - 1] = prefixX[0] = 1`.
        *   `X_range = (12 - (1L * powersOf10[1]) % MOD + MOD) % MOD = (12 - 10 + MOD) % MOD = 2`.
    *   `Sum_range`:
        *   `prefixSum[1] = 3`.
        *   `start_nz_idx = 1 (>0)`. `prefixSum[start_nz_idx - 1] = prefixSum[0] = 1`.
        *   `Sum_range = (3 - 1 + MOD) % MOD = 2`.
    *   Answer: `(2 * 2) % MOD = 4`.

*   **Query 3: `[4,6]` (s[4..6] = "300")**
    *   `li=4`, `ri=6`.
    *   Binary search for `li=4` finds `(3,4)` at `start_nz_idx = 2`.
    *   Binary search for `ri=6` finds `(3,4)` at `end_nz_idx = 2`.
    *   `start_nz_idx <= end_nz_idx`, so valid range.
    *   `count = end_nz_idx - start_nz_idx + 1 = 2 - 2 + 1 = 1`.
    *   `X_range`:
        *   `prefixX[2] = 123`.
        *   `start_nz_idx = 2 (>0)`. `prefixX[start_nz_idx - 1] = prefixX[1] = 12`.
        *   `X_range = (123 - (12L * powersOf10[1]) % MOD + MOD) % MOD = (123 - 120 + MOD) % MOD = 3`.
    *   `Sum_range`:
        *   `prefixSum[2] = 6`.
        *   `start_nz_idx = 2 (>0)`. `prefixSum[start_nz_idx - 1] = prefixSum[1] = 3`.
        *   `Sum_range = (6 - 3 + MOD) % MOD = 3`.
    *   Answer: `(3 * 3) % MOD = 9`.

The final output is `[12340, 4, 9]`, matching the example.

## Complexity Analysis

*   **Time Complexity**:
    *   **Precomputation**:
        *   Iterating through string `s` to build `nzEntries`: `O(m)`, where `m` is the length of `s`.
        *   Building `prefixX` and `prefixSum` arrays: `O(N_nz)`, where `N_nz` is the number of non-zero digits in `s` (`N_nz <= m`).
        *   Building `powersOf10` array: `O(m)`.
    *   **Per Query**:
        *   Two binary searches on `nzEntries` to find `start_nz_idx` and `end_nz_idx`: `O(log N_nz)`.
        *   Constant time arithmetic operations for calculating `X_range`, `Sum_range`, and the final answer.
    *   **Total**: With `Q` queries, the overall time complexity is `O(m + Q * log N_nz)`. Since `N_nz <= m`, this simplifies to **`O(m + Q log m)`**.

*   **Space Complexity**:
    *   `nzEntries` list: `O(N_nz)`.
    *   `prefixX` and `prefixSum` arrays: `O(N_nz)`.
    *   `powersOf10` array: `O(m)`.
    *   **Total**: The overall space complexity is **`O(m)`**.