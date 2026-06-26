# [Combination Sum](https://leetcode.com/problems/combination-sum/)

🟡 Medium

Topics: Array, Backtracking

## Problem Summary

Given an array of distinct candidate integers and a target sum, the objective is to find all unique combinations of these candidates that sum up to the target. The same number can be chosen from the candidates an unlimited number of times.

## Approach

This problem is a classic application of **backtracking**. We need to explore all possible combinations of numbers from the `candidates` array that sum up to the `target`.

The core idea behind the backtracking approach is as follows:

1.  **Recursive Function Signature**: We define a recursive helper function, say `backtrack(candidates, target, currentCombination, startIndex)`.
    *   `candidates`: The array of numbers to choose from.
    *   `target`: The remaining sum we need to achieve.
    *   `currentCombination`: A list representing the combination being built in the current recursive path.
    *   `startIndex`: An index to ensure unique combinations and avoid redundant explorations. By starting the iteration from `startIndex`, we prevent using elements that appeared before the current `startIndex`, which helps in building combinations in non-decreasing order and thus prevents duplicates like `[2,3]` and `[3,2]`. Importantly, for this problem, since numbers can be reused, when we pick a number, the next recursive call will still start from the *same* `startIndex` (or `i` in the loop), allowing the number to be chosen again.

2.  **Base Cases**:
    *   If `target == 0`: We have found a valid combination. Add a copy of `currentCombination` to our list of results. Then, return.
    *   If `target < 0`: The current path has exceeded the target, so it's invalid. Prune this branch and return.

3.  **Recursive Step**:
    *   Iterate through the `candidates` array starting from `startIndex`. For each candidate `candidates[i]`:
        *   Add `candidates[i]` to `currentCombination`.
        *   Make a recursive call: `backtrack(candidates, target - candidates[i], currentCombination, i)`. Notice that `i` is passed as the new `startIndex`. This is crucial for allowing the *same number* `candidates[i]` to be picked again in the next level of recursion.
        *   After the recursive call returns (i.e., we've explored all paths with `candidates[i]` at the current position), remove `candidates[i]` from `currentCombination`. This "backtrack" step undoes the choice, allowing the algorithm to explore other possibilities.

This strategy systematically explores all potential paths, builds valid combinations, prunes invalid ones, and ensures uniqueness through the `startIndex` parameter.

## Algorithm Walkthrough

Let's trace the execution with `candidates = [2,3,6,7]` and `target = 7`.

Initial call: `backtrack(candidates, target=7, currentCombination=[], startIndex=0)`

1.  **`backtrack(..., target=7, currentCombination=[], startIndex=0)`**
    *   Loop `i` from `0` to `3`:
        *   `i = 0`, `candidates[0] = 2`:
            *   Add `2` to `currentCombination`: `[2]`.
            *   Call `backtrack(..., target=5, currentCombination=[2], startIndex=0)`
            *   **`backtrack(..., target=5, currentCombination=[2], startIndex=0)`**
                *   Loop `i` from `0` to `3`:
                    *   `i = 0`, `candidates[0] = 2`:
                        *   Add `2`: `[2,2]`.
                        *   Call `backtrack(..., target=3, currentCombination=[2,2], startIndex=0)`
                        *   **`backtrack(..., target=3, currentCombination=[2,2], startIndex=0)`**
                            *   Loop `i` from `0` to `3`:
                                *   `i = 0`, `candidates[0] = 2`:
                                    *   Add `2`: `[2,2,2]`.
                                    *   Call `backtrack(..., target=1, currentCombination=[2,2,2], startIndex=0)`
                                    *   **`backtrack(..., target=1, currentCombination=[2,2,2], startIndex=0)`**
                                        *   `i = 0`, `candidates[0] = 2`: Add `2`: `[2,2,2,2]`. Call `backtrack(..., target=-1, ...)`. `target < 0`, **return**. Remove `2`.
                                        *   `i = 1`, `candidates[1] = 3`: Add `3`: `[2,2,2,3]`. Call `backtrack(..., target=-2, ...)`. `target < 0`, **return**. Remove `3`.
                                        *   ... (other candidates would also result in `target < 0`)
                                    *   **Return** from `backtrack(..., target=1, ...)`.
                                *   Remove `2` (from `[2,2,2]` -> `[2,2]`).
                                *   `i = 1`, `candidates[1] = 3`:
                                    *   Add `3`: `[2,2,3]`.
                                    *   Call `backtrack(..., target=0, currentCombination=[2,2,3], startIndex=1)`
                                    *   **`backtrack(..., target=0, currentCombination=[2,2,3], startIndex=1)`**
                                        *   `target == 0`. **Add `[2,2,3]` to results.**
                                        *   **Return**.
                                    *   Remove `3` (from `[2,2,3]` -> `[2,2]`).
                                *   ... (other candidates for `target=3` would result in `target < 0`)
                            *   **Return** from `backtrack(..., target=3, ...)`.
                        *   Remove `2` (from `[2,2]` -> `[2]`).
                    *   `i = 1`, `candidates[1] = 3`:
                        *   Add `3`: `[2,3]`.
                        *   Call `backtrack(..., target=2, currentCombination=[2,3], startIndex=1)`
                        *   **`backtrack(..., target=2, currentCombination=[2,3], startIndex=1)`**
                            *   Loop `i` from `1` to `3`:
                                *   `i = 1`, `candidates[1] = 3`: Add `3`: `[2,3,3]`. Call `backtrack(..., target=-1, ...)`. `target < 0`, **return**. Remove `3`.
                                *   ... (other candidates would also result in `target < 0`)
                            *   **Return**.
                        *   Remove `3` (from `[2,3]` -> `[2]`).
                    *   ... (other candidates `6`, `7` would result in `target < 0`)
                *   **Return** from `backtrack(..., target=5, ...)`.
            *   Remove `2` (from `[2]` -> `[]`).
        *   `i = 1`, `candidates[1] = 3`: (Similar branch, not leading to a result of 7 on its own or already covered by starting with 2)
        *   `i = 2`, `candidates[2] = 6`: (Similar branch)
        *   `i = 3`, `candidates[3] = 7`:
            *   Add `7`: `[7]`.
            *   Call `backtrack(..., target=0, currentCombination=[7], startIndex=3)`
            *   **`backtrack(..., target=0, currentCombination=[7], startIndex=3)`**
                *   `target == 0`. **Add `[7]` to results.**
                *   **Return**.
            *   Remove `7` (from `[7]` -> `[]`).
    *   **Return** from initial call.

Final `results`: `[[2,2,3], [7]]`

## Complexity Analysis

Let `N` be the number of `candidates`, and `T` be the `target` value. Let `M` be the minimum value among `candidates`.

*   **Time Complexity**:
    The time complexity for combination sum problems with unlimited repetitions can be quite high due to the nature of exploring all possible combinations.
    In the worst case, the recursion tree can have a depth of `T / M` (e.g., if `candidates = [1]`, and `target = T`). At each level of the recursion, we iterate through `N` candidates. This suggests a complexity that is exponential in `T` and `N`.
    A common theoretical upper bound for the number of nodes in the recursion tree for this problem is roughly `O(N^(T/M))`. Additionally, when a valid combination is found, copying it to the results list takes `O(T/M)` time, as the maximum length of a combination is `T/M`.
    Thus, the overall time complexity can be approximated as `O(N^(T/M) * T/M)`.
    However, the problem statement provides a constraint: "the number of unique combinations that sum up to target is less than 150". This implies that the practical runtime on LeetCode is often much better than the theoretical worst-case, as the number of valid paths (and subsequent copies) is heavily limited. If `S` is the number of valid combinations, and `K = T/M` is the maximum length, then the time spent for processing and storing found combinations is `O(S * K)`.

*   **Space Complexity**:
    The space complexity is determined by the maximum depth of the recursion stack and the space required to store the `results`.
    *   **Recursion Stack**: In the worst case, the recursion depth can go up to `T / M` (e.g., if `target = 40` and `min_candidate = 2`, depth = 20). So, `O(T/M)`.
    *   **Results List**: If `S` is the number of unique combinations found, and `K = T/M` is the maximum length of a combination, then storing all combinations requires `O(S * K)` space.
    Therefore, the total space complexity is `O(T/M + S * T/M)`, which simplifies to `O(S * T/M)` as `S` can be significant.