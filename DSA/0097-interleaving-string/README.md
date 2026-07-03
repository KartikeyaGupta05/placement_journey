# [Interleaving String](https://leetcode.com/problems/interleaving-string/)

🟡 Medium

**Topics**: String, Dynamic Programming

---

## Problem Summary

Given three strings `s1`, `s2`, and `s3`, the problem asks us to determine if `s3` can be formed by an "interleaving" of `s1` and `s2`. An interleaving means that `s3` is created by concatenating characters from `s1` and `s2` such that the relative order of characters from `s1` is preserved, the relative order of characters from `s2` is preserved, and each character from `s1` and `s2` is used exactly once.

---

## Approach

This problem exhibits optimal substructure and overlapping subproblems, making it a classic candidate for Dynamic Programming.

We define a 2D boolean array `dp` where `dp[i][j]` represents whether the prefix of `s3` of length `i + j` can be formed by interleaving the prefix of `s1` of length `i` and the prefix of `s2` of length `j`.

Let `m = s1.length()` and `n = s2.length()`. The `dp` table will have dimensions `(m+1) x (n+1)`.

1.  **Initial Checks**:
    *   If `s1.length() + s2.length()` is not equal to `s3.length()`, it's impossible for `s3` to be an interleaving, so we can immediately return `false`.

2.  **Base Case**:
    *   `dp[0][0]` is `true`, as empty prefixes of `s1` and `s2` can interleave to form an empty prefix of `s3`.

3.  **Filling the First Row (s1 is empty)**:
    *   For `j` from 1 to `n`: `dp[0][j]` is `true` if and only if `dp[0][j-1]` is `true` AND `s2.charAt(j-1)` matches `s3.charAt(j-1)`. This means if `s1` contributes nothing, `s3`'s prefix must match `s2`'s prefix exactly.

4.  **Filling the First Column (s2 is empty)**:
    *   For `i` from 1 to `m`: `dp[i][0]` is `true` if and only if `dp[i-1][0]` is `true` AND `s1.charAt(i-1)` matches `s3.charAt(i-1)`. Similarly, if `s2` contributes nothing, `s3`'s prefix must match `s1`'s prefix.

5.  **Filling the General Cells**:
    *   For `i` from 1 to `m` and `j` from 1 to `n`:
        *   `dp[i][j]` can be `true` in two ways:
            *   **Using `s1`'s character**: If `s1.charAt(i-1)` matches `s3.charAt(i+j-1)` AND `dp[i-1][j]` is `true` (meaning the previous parts successfully interleaved and we just appended `s1`'s `i`-th character).
            *   **Using `s2`'s character**: If `s2.charAt(j-1)` matches `s3.charAt(i+j-1)` AND `dp[i][j-1]` is `true` (meaning the previous parts successfully interleaved and we just appended `s2`'s `j`-th character).
        *   Therefore, `dp[i][j] = (s1.charAt(i-1) == s3.charAt(i+j-1) && dp[i-1][j]) || (s2.charAt(j-1) == s3.charAt(i+j-1) && dp[i][j-1])`.

6.  **Result**:
    *   The final answer is `dp[m][n]`, which tells us if `s3` of length `m+n` can be formed by interleaving `s1` of length `m` and `s2` of length `n`.

---

## Algorithm Walkthrough

Let's use Example 1: `s1 = "aabcc"`, `s2 = "dbbca"`, `s3 = "aadbbcbcac"`

1.  **Initial Check**:
    *   `s1.length() = 5`, `s2.length() = 5`, `s3.length() = 10`.
    *   `5 + 5 = 10`. Lengths match, so we proceed.
    *   `m = 5`, `n = 5`. We create a `dp[6][6]` boolean table initialized to `false`.

2.  **Base Case**:
    *   `dp[0][0] = true` (empty prefixes can form an empty string).

3.  **Fill First Row (s1 prefix is "")**:
    *   `s3` characters indices are `j-1`.
    *   `j=1`: `s2[0] = 'd'`, `s3[0] = 'a'`. Mismatch. `dp[0][1] = false`.
    *   Since `dp[0][1]` is `false`, all subsequent `dp[0][j]` will remain `false`.

4.  **Fill First Column (s2 prefix is "")**:
    *   `s3` characters indices are `i-1`.
    *   `i=1`: `s1[0] = 'a'`, `s3[0] = 'a'`. Match. `dp[1][0] = dp[0][0] && true = true`.
    *   `i=2`: `s1[1] = 'a'`, `s3[1] = 'a'`. Match. `dp[2][0] = dp[1][0] && true = true`.
    *   `i=3`: `s1[2] = 'b'`, `s3[2] = 'd'`. Mismatch. `dp[3][0] = dp[2][0] && false = false`.
    *   `i=4`: `s1[3] = 'c'`, `s3[3] = 'b'`. Mismatch. `dp[4][0] = false`.
    *   `i=5`: `s1[4] = 'c'`, `s3[4] = 'b'`. Mismatch. `dp[5][0] = false`.

    Current `dp` table (relevant parts):
    ```
           ""   d    b    b    c    a (s2)
         ----------------------------------
    ""   | T    F    F    F    F    F
    a    | T    F    F    F    F    F
    a    | T    F    F    F    F    F
    b    | F    F    F    F    F    F
    c    | F    F    F    F    F    F
    c    | F    F    F    F    F    F
    (s1)
    ```

5.  **Fill General Cells (`i` from 1 to `m`, `j` from 1 to `n`)**:
    *   `dp[i][j] = (s1.charAt(i-1) == s3.charAt(i+j-1) && dp[i-1][j]) || (s2.charAt(j-1) == s3.charAt(i+j-1) && dp[i][j-1])`

    Let's trace a few key cells:
    *   **`dp[1][1]` (`s1="a", s2="d", s3="ad"`)**: `s3[1] = 'a'`
        *   Option 1 (`s1`): `s1[0] = 'a'`. `s1[0] == s3[1]`? 'a' == 'a' (True). `dp[0][1]`? `false`. (`True && False` is `False`).
        *   Option 2 (`s2`): `s2[0] = 'd'`. `s2[0] == s3[1]`? 'd' == 'a' (False). (`False && dp[1][0]`) is `False`.
        *   `dp[1][1] = false`.

    *   **`dp[2][1]` (`s1="aa", s2="d", s3="aad"`)**: `s3[2] = 'd'`
        *   Option 1 (`s1`): `s1[1] = 'a'`. `s1[1] == s3[2]`? 'a' == 'd' (False). (`False && dp[1][1]`) is `False`.
        *   Option 2 (`s2`): `s2[0] = 'd'`. `s2[0] == s3[2]`? 'd' == 'd' (True). `dp[2][0]`? `true`. (`True && True` is `True`).
        *   `dp[2][1] = true`. (This means `s3` prefix "aad" can be formed by `s1` prefix "aa" and `s2` prefix "d" by picking 'a' from `s1`, 'a' from `s1`, then 'd' from `s2`).

    *   **`dp[2][2]` (`s1="aa", s2="db", s3="aad" + "b" = "aadbb"`)**: `s3[3] = 'b'`
        *   Option 1 (`s1`): `s1[1] = 'a'`. `s1[1] == s3[3]`? 'a' == 'b' (False). (`False && dp[1][2]`) is `False`.
        *   Option 2 (`s2`): `s2[1] = 'b'`. `s2[1] == s3[3]`? 'b' == 'b' (True). `dp[2][1]`? `true`. (`True && True` is `True`).
        *   `dp[2][2] = true`.

    The process continues, filling the table. Eventually, we will reach `dp[5][5]`.
    For this example, the final `dp[5][5]` will be `true`.

---

## Complexity Analysis

*   **Time Complexity**:
    *   O(m \* n), where `m` is the length of `s1` and `n` is the length of `s2`. We iterate through each cell of the `(m+1) x (n+1)` DP table exactly once, and each cell computation takes constant time.

*   **Space Complexity**:
    *   O(m \* n) for the 2D `dp` table.
    *   **Follow-up Optimization**: It's possible to optimize the space to O(min(m, n)) or O(n) (assuming `n <= m`) by noticing that `dp[i][j]` only depends on `dp[i-1][j]` (previous row) and `dp[i][j-1]` (current row, previous column). This allows reducing the space to two rows (or one row if optimized carefully) of the DP table.