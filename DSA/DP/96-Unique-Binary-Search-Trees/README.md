# 96. Unique Binary Search Trees

**Difficulty:** Medium | **Topic:** DP | **Tags:** Math, Dynamic Programming, Tree, Binary Search Tree, Binary Tree

---

## Problem

Given an integer `n`, return the number of structurally unique Binary Search Trees (BSTs) which has exactly `n` nodes of unique values from 1 to `n`.

Constraints:
*   `1 <= n <= 19`

## Examples

Example 1:

```
Input: n = 3
Output: 5
```

Example 2:

```
Input: n = 1
Output: 1
```

## Approach

This problem can be solved using Dynamic Programming. The core idea is to realize that for any given number of nodes `n`, we can choose any number `i` (from 1 to `n`) to be the root of the BST. If `i` is the root, then all numbers less than `i` must form the left subtree, and all numbers greater than `i` must form the right subtree.

Let `G(n)` be the number of structurally unique BSTs that can be formed with `n` nodes.

1.  **Base Cases:**
    *   If `n = 0` (empty tree), there is `1` way to form it (an empty tree itself). So, `G(0) = 1`.
    *   If `n = 1` (a single node tree), there is `1` way to form it. So, `G(1) = 1`.

2.  **Recursive Relation (Intuition):**
    For a given `n`, we iterate through each possible value `i` (from 1 to `n`) to be the root:
    *   If `i` is chosen as the root:
        *   The left subtree must contain `i - 1` nodes (values from 1 to `i - 1`). The number of ways to form this left subtree is `G(i - 1)`.
        *   The right subtree must contain `n - i` nodes (values from `i + 1` to `n`). The number of ways to form this right subtree is `G(n - i)`.
    *   The total number of BSTs with `i` as the root is the product of the number of ways to form its left and right subtrees: `G(i - 1) * G(n - i)`.
    *   Since `i` can be any value from 1 to `n`, we sum up the possibilities for each `i`.

3.  **Dynamic Programming Formulation:**
    We can use a DP array, say `dp`, where `dp[k]` stores `G(k)`, the number of unique BSTs with `k` nodes.
    *   Initialize `dp[0] = 1` and `dp[1] = 1`.
    *   For `k` from 2 to `n`:
        *   Initialize `dp[k] = 0`.
        *   Iterate `i` from 1 to `k` (where `i` is the potential root node):
            *   Add `dp[i - 1] * dp[k - i]` to `dp[k]`.
    *   Finally, `dp[n]` will hold the answer.

This approach builds up the solution for `n` nodes by combining solutions for smaller numbers of nodes, which is the hallmark of dynamic programming. The values `G(n)` are known as Catalan numbers.

## Formula

The recurrence relation derived for the number of unique BSTs with `n` nodes, `G(n)`, is:

```
G(n) = Σ_{i=1 to n} (G(i-1) * G(n-i))
```

with base cases:

```
G(0) = 1
G(1) = 1
```

Where `G(k)` represents the number of unique BSTs with `k` nodes.

## Dry Run

Let's walk through the algorithm for `n = 3`.

We'll use a `dp` array to store the results.
`dp` array of size `n + 1` (i.e., `dp[4]`).

1.  **Initialize base cases:**
    *   `dp[0] = 1`
    *   `dp[1] = 1`
    `dp` is currently: `[1, 1, ?, ?, ?]` (indices 0 to 3 relevant for n=3)

2.  **Calculate `dp[2]` (for `node = 2`):**
    *   We want to find `G(2)`. Iterate `r` (root) from 1 to 2.
    *   **Case `r = 1` (1 is the root):**
        *   Left subtree has `r - 1 = 0` nodes. Ways: `dp[0] = 1`.
        *   Right subtree has `node - r = 2 - 1 = 1` node. Ways: `dp[1] = 1`.
        *   Contribution: `dp[0] * dp[1] = 1 * 1 = 1`.
    *   **Case `r = 2` (2 is the root):**
        *   Left subtree has `r - 1 = 1` node. Ways: `dp[1] = 1`.
        *   Right subtree has `node - r = 2 - 2 = 0` nodes. Ways: `dp[0] = 1`.
        *   Contribution: `dp[1] * dp[0] = 1 * 1 = 1`.
    *   `dp[2]` = Sum of contributions = `1 + 1 = 2`.
    `dp` is now: `[1, 1, 2, ?, ?]`

3.  **Calculate `dp[3]` (for `node = 3`):**
    *   We want to find `G(3)`. Iterate `r` (root) from 1 to 3.
    *   **Case `r = 1` (1 is the root):**
        *   Left subtree has `r - 1 = 0` nodes. Ways: `dp[0] = 1`.
        *   Right subtree has `node - r = 3 - 1 = 2` nodes. Ways: `dp[2] = 2`.
        *   Contribution: `dp[0] * dp[2] = 1 * 2 = 2`.
    *   **Case `r = 2` (2 is the root):**
        *   Left subtree has `r - 1 = 1` node. Ways: `dp[1] = 1`.
        *   Right subtree has `node - r = 3 - 2 = 1` node. Ways: `dp[1] = 1`.
        *   Contribution: `dp[1] * dp[1] = 1 * 1 = 1`.
    *   **Case `r = 3` (3 is the root):**
        *   Left subtree has `r - 1 = 2` nodes. Ways: `dp[2] = 2`.
        *   Right subtree has `node - r = 3 - 3 = 0` nodes. Ways: `dp[0] = 1`.
        *   Contribution: `dp[2] * dp[0] = 2 * 1 = 2`.
    *   `dp[3]` = Sum of contributions = `2 + 1 + 2 = 5`.
    `dp` is now: `[1, 1, 2, 5, ?]`

4.  **Result:**
    The final answer is `dp[3]`, which is 5.

## Time Complexity

The time complexity is `O(n^2)`.
*   We have an outer loop that iterates from `node = 2` to `n` (n-1 iterations).
*   Inside this outer loop, there's an inner loop that iterates from `r = 1` to `node` (`node` iterations).
*   The work inside the inner loop is constant time.
*   Thus, the total operations are proportional to `sum(node for node=2 to n)`, which is `O(n^2)`.

## Space Complexity

The space complexity is `O(n)`.
*   We use a dynamic programming array `dp` of size `n + 1` to store the results for subproblems. This array takes `O(n)` space.

---