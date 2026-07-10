# [Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/)

🟡 Medium

Topics: Dynamic Programming, Backtracking, Tree, Binary Search Tree, Binary Tree

## Problem Summary

Given an integer `n`, the task is to generate all structurally unique Binary Search Trees (BSTs) that contain nodes with unique values from 1 to `n`. Each generated BST must use all `n` unique values exactly once. The order in which the trees are returned does not matter.

## Approach

The problem asks for *all* possible structural variations of BSTs, which strongly suggests a recursive or backtracking approach. The key property of a BST is that for any node, all values in its left subtree are smaller, and all values in its right subtree are larger.

We can define a recursive function, say `generateTrees(start, end)`, that returns a list of all structurally unique BSTs whose nodes have values in the range `[start, end]`.

1.  **Base Case**: If `start > end`, it means we have an empty range, so an empty subtree. We represent this by returning a list containing `null`.
2.  **Recursive Step**: Iterate through each number `i` from `start` to `end`. For each `i`:
    *   Consider `i` as the root of the current BST.
    *   Recursively generate all possible left subtrees using numbers in the range `[start, i-1]` by calling `generateTrees(start, i-1)`.
    *   Recursively generate all possible right subtrees using numbers in the range `[i+1, end]` by calling `generateTrees(i+1, end)`.
    *   Combine every left subtree from the `leftSubtrees` list with every right subtree from the `rightSubtrees` list. For each combination, create a new `TreeNode` with value `i`, attach the chosen left subtree as its left child, and the chosen right subtree as its right child. Add this newly formed BST to the result list for the current `generateTrees(start, end)` call.

To optimize, we can use memoization (dynamic programming) to store the results of `generateTrees(start, end)`. Since the same subproblems might be encountered multiple times, storing their computed lists of trees in a map (e.g., `Map<Pair<Integer, Integer>, List<TreeNode>>`) prevents redundant calculations.

The main function `generateTrees(n)` will simply call `generateTrees(1, n)` and return its result.

## Algorithm Walkthrough

Let's trace the execution for `n = 3`. We want to find `generateTrees(1, 3)`.

1.  **`generateTrees(1, 3)`**:
    *   **Try `root = 1`**:
        *   `leftSubtrees = generateTrees(1, 0)`: Base case, returns `[null]`.
        *   `rightSubtrees = generateTrees(2, 3)`: (Recursive call)
            *   **`generateTrees(2, 3)`**:
                *   **Try `root = 2`**:
                    *   `left = generateTrees(2, 1)`: Base case, returns `[null]`.
                    *   `right = generateTrees(3, 3)`: (Recursive call)
                        *   **`generateTrees(3, 3)`**:
                            *   **Try `root = 3`**:
                                *   `left = generateTrees(3, 2)`: `[null]`
                                *   `right = generateTrees(4, 3)`: `[null]`
                                *   Combine: `TreeNode(3)` with `null` left, `null` right. Returns `[TreeNode(3)]`.
                        *   End `generateTrees(3, 3)`.
                    *   Combine: `TreeNode(2)` with `null` left, `TreeNode(3)` right. This gives `[2, null, 3]`.
                *   **Try `root = 3`**:
                    *   `left = generateTrees(2, 2)`: (Recursive call)
                        *   **`generateTrees(2, 2)`**:
                            *   **Try `root = 2`**:
                                *   `left = generateTrees(2, 1)`: `[null]`
                                *   `right = generateTrees(3, 2)`: `[null]`
                                *   Combine: `TreeNode(2)` with `null` left, `null` right. Returns `[TreeNode(2)]`.
                        *   End `generateTrees(2, 2)`.
                    *   `right = generateTrees(4, 3)`: `[null]`
                    *   Combine: `TreeNode(3)` with `TreeNode(2)` left, `null` right. This gives `[3, 2, null]`.
                *   `generateTrees(2, 3)` returns `[[2, null, 3], [3, 2, null]]`.
            *   End `generateTrees(2, 3)`.
        *   Combine `root = 1` with `[null]` (left) and `[[2, null, 3], [3, 2, null]]` (right):
            *   `TreeNode(1)` with `null` left, `[2, null, 3]` right -> `[1, null, 2, null, 3]`
            *   `TreeNode(1)` with `null` left, `[3, 2, null]` right -> `[1, null, 3, 2]`
    *   **Try `root = 2`**:
        *   `leftSubtrees = generateTrees(1, 1)`: (Recursive call)
            *   `generateTrees(1, 1)` returns `[TreeNode(1)]`.
        *   `rightSubtrees = generateTrees(3, 3)`: (Already memoized from above) `[TreeNode(3)]`.
        *   Combine `root = 2` with `[TreeNode(1)]` (left) and `[TreeNode(3)]` (right):
            *   `TreeNode(2)` with `[1]` left, `[3]` right -> `[2, 1, 3]`
    *   **Try `root = 3`**:
        *   `leftSubtrees = generateTrees(1, 2)`: (Recursive call)
            *   **`generateTrees(1, 2)`**:
                *   **Try `root = 1`**:
                    *   `left = generateTrees(1, 0)`: `[null]`
                    *   `right = generateTrees(2, 2)`: (Already memoized) `[TreeNode(2)]`
                    *   Combine: `TreeNode(1)` with `null` left, `[2]` right -> `[1, null, 2]`
                *   **Try `root = 2`**:
                    *   `left = generateTrees(1, 1)`: (Already memoized) `[TreeNode(1)]`
                    *   `right = generateTrees(3, 2)`: `[null]`
                    *   Combine: `TreeNode(2)` with `[1]` left, `null` right -> `[2, 1, null]`
                *   `generateTrees(1, 2)` returns `[[1, null, 2], [2, 1, null]]`.
            *   End `generateTrees(1, 2)`.
        *   `rightSubtrees = generateTrees(4, 3)`: Base case, returns `[null]`.
        *   Combine `root = 3` with `[[1, null, 2], [2, 1, null]]` (left) and `[null]` (right):
            *   `TreeNode(3)` with `[1, null, 2]` left, `null` right -> `[3, 1, null, null, 2]`
            *   `TreeNode(3)` with `[2, 1, null]` left, `null` right -> `[3, 2, null, 1]`

The final result for `n=3` is a list containing these 5 unique BSTs:
`[[1,null,2,null,3], [1,null,3,2], [2,1,3], [3,1,null,null,2], [3,2,null,1]]`

## Complexity Analysis

Let `C_n` be the `n`-th Catalan number, which represents the number of structurally unique BSTs with `n` nodes. `C_n` grows exponentially, roughly `(4^n) / (n^(3/2) * sqrt(pi))`.

### Time Complexity

The algorithm computes results for `O(n^2)` distinct subproblems `(start, end)`. For each subproblem, it iterates through `O(n)` possible roots. For each root, it combines lists of left and right subtrees. If `k` is the size of the left subtree range and `n-1-k` is the size of the right subtree range, the number of combinations is `C_k * C_{n-1-k}`. Summing these combinations over all possible `k` is `C_n`. Since we are *constructing* these trees (which involves creating `n` nodes for each tree), the total time complexity is dominated by the number of nodes in all generated trees. This leads to **O(n * C_n)**.

### Space Complexity

The memoization table stores `O(n^2)` lists of trees. Each list contains `C_k` trees for a subproblem of size `k`. The maximum number of trees is `C_n`, each having `n` nodes. Therefore, the total space required to store all generated unique BSTs (both in the memoization table and the final result list) is **O(n * C_n)**. The recursion stack depth is `O(n)`.