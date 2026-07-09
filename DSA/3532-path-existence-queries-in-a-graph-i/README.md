# [Path Existence Queries in a Graph I](https://leetcode.com/problems/path-existence-queries-in-a-graph-i/)

🟡 Medium

**Topics**: Array, Hash Table, Binary Search, Union-Find, Graph Theory

## Problem Summary

Given `n` nodes and a sorted array `nums` where `nums[i]` is the value associated with node `i`, we need to construct an undirected graph. An edge exists between nodes `i` and `j` if `|nums[i] - nums[j]| <= maxDiff`. For a series of queries, each asking for path existence between `ui` and `vi`, we must return a boolean array indicating connectivity.

## Approach

The problem asks for path existence between nodes in an undirected graph, which is a classic application for the Disjoint Set Union (DSU) data structure. The main challenge lies in efficiently constructing the connected components based on the given edge condition (`|nums[i] - nums[j]| <= maxDiff`) without explicitly building a potentially dense graph.

Since `nums` is sorted, for any node `i`, its potential neighbors `j` (where `j > i`) must satisfy `nums[j] - nums[i] <= maxDiff`. This means `nums[j]` must be within the range `[nums[i], nums[i] + maxDiff]`. We can leverage this property and an optimized DSU strategy to group nodes.

1.  **Initialize DSU**: Create a `parent` array where `parent[k] = k` for all nodes `k`, and optionally a `size` array for union-by-size optimization.
2.  **Initialize `next_idx_to_check` Array**: This auxiliary array, `next_idx_to_check`, is key to efficiently processing potential connections. `next_idx_to_check[k]` will store the smallest index `j >= k` that has not yet been "effectively processed" or merged into a component by its preceding nodes. Initialize `next_idx_to_check[k] = k + 1` for `k < n-1`, and `next_idx_to_check[n-1] = n`.
3.  **Build Connected Components**:
    *   Iterate `i` from `0` to `n-1`. This `i` represents the "left" node of a potential edge.
    *   For each `i`, initialize a "right" pointer `j = next_idx_to_check[i]`. This pointer `j` will efficiently scan for potential neighbors to connect to `i`.
    *   While `j < n` (within bounds) and `nums[j] - nums[i] <= maxDiff` (satisfies edge condition):
        *   Perform `union(i, j)` to connect node `i` and node `j`.
        *   **Optimize `next_idx_to_check`**: After `union(i, j)`, `j` (and potentially its entire component) is now part of `i`'s component. To avoid redundant checks, we update `next_idx_to_check[i] = next_idx_to_check[j]`. This effectively "path-compresses" the `next_idx_to_check` array, allowing `i` to jump directly to the next unprocessed index.
        *   Update `j = next_idx_to_check[i]` to continue scanning from the new jumped position.
4.  **Process Queries**: For each query `[ui, vi]` in the `queries` array:
    *   Find the root parents of `ui` and `vi` using `find(ui)` and `find(vi)`.
    *   If `find(ui) == find(vi)`, it means a path exists, so the answer for this query is `true`. Otherwise, it's `false`.
    *   Store these boolean results in the `answer` array.

This optimized DSU construction ensures that each `union` operation is amortized efficient, and the `next_idx_to_check` array prevents excessive iterations in the inner loop, leading to an overall efficient component building phase.

## Algorithm Walkthrough

Let's use Example 2:
`n = 4, nums = [2,5,6,8], maxDiff = 2, queries = [[0,1],[0,2],[1,3],[2,3]]`

**1. DSU Initialization:**
*   `parent = [0, 1, 2, 3]`
*   `size = [1, 1, 1, 1]`
*   `next_idx_to_check = [1, 2, 3, 4]` (node `n-1`'s next is `n`)

**2. Build Connected Components:**

*   **`i = 0` (`nums[0] = 2`):**
    *   `j = next_idx_to_check[0] = 1`.
    *   Check `j=1` (`nums[1]=5`): `nums[1] - nums[0] = 5 - 2 = 3`. `3 > maxDiff=2`. Condition fails.
    *   Loop ends. `next_idx_to_check` remains `[1, 2, 3, 4]`.

*   **`i = 1` (`nums[1] = 5`):**
    *   `j = next_idx_to_check[1] = 2`.
    *   Check `j=2` (`nums[2]=6`): `nums[2] - nums[1] = 6 - 5 = 1`. `1 <= maxDiff=2`. Condition true.
        *   `union(1, 2)`. Let's assume `parent[2]` becomes `1`. (DSU state: `parent = [0,1,1,3]`). `size[1]` becomes `2`.
        *   Update `next_idx_to_check[1] = next_idx_to_check[2] = 3`.
        *   `j` becomes `next_idx_to_check[1] = 3`.
    *   Check `j=3` (`nums[3]=8`): `nums[3] - nums[1] = 8 - 5 = 3`. `3 > maxDiff=2`. Condition fails.
    *   Loop ends. `next_idx_to_check` is now `[1, 3, 3, 4]`.

*   **`i = 2` (`nums[2] = 6`):**
    *   `j = next_idx_to_check[2] = 3`.
    *   Check `j=3` (`nums[3]=8`): `nums[3] - nums[2] = 8 - 6 = 2`. `2 <= maxDiff=2`. Condition true.
        *   `union(2, 3)`. Since `find(2)` is `1`, this effectively `union(1, 3)`. Let `parent[3]` become `1`. (DSU state: `parent = [0,1,1,1]`). `size[1]` becomes `3`.
        *   Update `next_idx_to_check[2] = next_idx_to_check[3] = 4`.
        *   `j` becomes `next_idx_to_check[2] = 4`.
    *   Check `j=4`: `j < n` (4 < 4) is false. Loop ends.
    *   `next_idx_to_check` is now `[1, 3, 4, 4]`.

*   **`i = 3` (`nums[3] = 8`):**
    *   `j = next_idx_to_check[3] = 4`.
    *   Check `j=4`: `j < n` (4 < 4) is false. Loop ends.

**Final DSU state after component building:**
`parent = [0, 1, 1, 1]`
Components: `{0}`, `{1, 2, 3}`. (Root of 0 is 0. Root of 1, 2, 3 is 1).

**3. Process Queries:**

*   **Query `[0,1]`**:
    *   `find(0) = 0`, `find(1) = 1`.
    *   `0 != 1`. Result: `false`.
*   **Query `[0,2]`**:
    *   `find(0) = 0`, `find(2) = 1`.
    *   `0 != 1`. Result: `false`.
*   **Query `[1,3]`**:
    *   `find(1) = 1`, `find(3) = 1`.
    *   `1 == 1`. Result: `true`.
*   **Query `[2,3]`**:
    *   `find(2) = 1`, `find(3) = 1`.
    *   `1 == 1`. Result: `true`.

**Final Answer**: `[false, false, true, true]`. This matches the example output.

## Complexity Analysis

Let `N` be the number of nodes (`nums.length`) and `Q` be the number of queries (`queries.length`).

### Time Complexity

*   **DSU and `next_idx_to_check` Initialization**: `O(N)` to set up the `parent`, `size`, and `next_idx_to_check` arrays.
*   **Building Connected Components**: The outer loop runs `N` times. The inner `while` loop, combined with path compression in `find` and union-by-size in `union`, performs a series of DSU operations. The crucial optimization with `next_idx_to_check[i] = next_idx_to_check[j]` allows `j` to "jump" over already-processed nodes. Each `union` operation effectively reduces the number of disconnected components or merges `next_idx_to_check` pointers. The total time for this phase is amortized `O(N * \alpha(N))`, where `\alpha` is the inverse Ackermann function, which is practically a constant for any realistic `N`.
*   **Processing Queries**: There are `Q` queries. Each query involves two `find` operations, taking `O(\alpha(N))` time. Total `O(Q * \alpha(N))`.
*   **Overall Time Complexity**: `O((N + Q) * \alpha(N))`. Since `\alpha(N)` is extremely small (effectively constant), this can be considered `O(N + Q)`.

### Space Complexity

*   **DSU Arrays**: `parent` and `size` arrays each require `O(N)` space.
*   **`next_idx_to_check` Array**: Requires `O(N)` space.
*   **Answer Array**: Requires `O(Q)` space to store the results of the queries.
*   **Overall Space Complexity**: `O(N + Q)`.