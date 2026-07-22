# [3Sum](https://leetcode.com/problems/3sum/)

## 🟡 Medium

**Topics**: Array, Two Pointers, Sorting

## Problem Summary

Given an array of integers `nums`, the task is to find all unique triplets `[nums[i], nums[j], nums[k]]` such that `i`, `j`, and `k` are distinct indices, and their sum `nums[i] + nums[j] + nums[k]` equals zero. The solution set must not contain duplicate triplets.

## Approach

The most efficient approach to solve the 3Sum problem involves sorting the input array and then using a two-pointer technique.

1.  **Sort the array**: Sorting `nums` allows us to easily handle duplicates and efficiently move pointers.
2.  **Iterate with a fixed pointer (`i`)**: We iterate through the sorted array with a primary pointer `i` from the beginning up to `n-3` (where `n` is the array length). For each `nums[i]`, we treat it as the first element of a potential triplet.
    *   **Skip duplicates for `i`**: To avoid duplicate triplets, if `i > 0` and `nums[i]` is the same as `nums[i-1]`, we skip this iteration and move to the next `i`.
3.  **Two-pointer technique for remaining sum**: For each `nums[i]`, we need to find two numbers `nums[left]` and `nums[right]` such that `nums[left] + nums[right] == -nums[i]`. We initialize `left` to `i + 1` and `right` to `n - 1`.
    *   **Calculate sum**: Compute `currentSum = nums[i] + nums[left] + nums[right]`.
    *   **Adjust pointers**:
        *   If `currentSum == 0`: A valid triplet is found! Add `[nums[i], nums[left], nums[right]]` to the result list. Then, increment `left` and decrement `right`. Crucially, **skip duplicates for `left` and `right`** by continuing to increment `left` as long as `left < right` and `nums[left] == nums[left-1]`, and similarly for `right` (decrement `right` as long as `left < right` and `nums[right] == nums[right+1]`). This ensures unique pairs for the fixed `nums[i]`.
        *   If `currentSum < 0`: The sum is too small, so we need a larger value. Increment `left` to consider a larger number.
        *   If `currentSum > 0`: The sum is too large, so we need a smaller value. Decrement `right` to consider a smaller number.
    *   The two-pointer loop continues as long as `left < right`.

This approach ensures that all possible combinations are considered, duplicates are avoided, and the search for the remaining two numbers is efficient due to the sorted array and two-pointer movement.

## Algorithm Walkthrough

Let's trace the algorithm with `nums = [-1,0,1,2,-1,-4]`.

1.  **Sort `nums`**: `nums` becomes `[-4, -1, -1, 0, 1, 2]`. Let `n = 6`.
    `results = []`

2.  **`i = 0`, `nums[i] = -4`**:
    *   Initialize `left = 1`, `right = 5`.
    *   Target for `nums[left] + nums[right]` is `0 - (-4) = 4`.
    *   **Loop (`left < right`)**:
        *   `left = 1 (nums[1] = -1)`, `right = 5 (nums[5] = 2)`. `currentSum = -4 + (-1) + 2 = -3`.
        *   `-3 < 0`: Need a larger sum. `left++` -> `left = 2`.
        *   `left = 2 (nums[2] = -1)`, `right = 5 (nums[5] = 2)`. `currentSum = -4 + (-1) + 2 = -3`.
        *   `-3 < 0`: Need a larger sum. `left++` -> `left = 3`.
        *   `left = 3 (nums[3] = 0)`, `right = 5 (nums[5] = 2)`. `currentSum = -4 + 0 + 2 = -2`.
        *   `-2 < 0`: Need a larger sum. `left++` -> `left = 4`.
        *   `left = 4 (nums[4] = 1)`, `right = 5 (nums[5] = 2)`. `currentSum = -4 + 1 + 2 = -1`.
        *   `-1 < 0`: Need a larger sum. `left++` -> `left = 5`.
        *   Now `left = right`. The inner loop terminates.

3.  **`i = 1`, `nums[i] = -1`**:
    *   `i > 0` but `nums[1] (-1)` is not equal to `nums[0] (-4)`. Proceed.
    *   Initialize `left = 2`, `right = 5`.
    *   Target for `nums[left] + nums[right]` is `0 - (-1) = 1`.
    *   **Loop (`left < right`)**:
        *   `left = 2 (nums[2] = -1)`, `right = 5 (nums[5] = 2)`. `currentSum = -1 + (-1) + 2 = 0`.
        *   `0 == 0`: Found a triplet! Add `[-1, -1, 2]` to `results`. `results = [[-1, -1, 2]]`.
            *   Increment `left` to `3`.
            *   Decrement `right` to `4`.
            *   Skip duplicates: `nums[left-1]` (`nums[2] = -1`) is not equal to `nums[left]` (`nums[3] = 0`).
            *   Skip duplicates: `nums[right+1]` (`nums[5] = 2`) is not equal to `nums[right]` (`nums[4] = 1`).
        *   `left = 3 (nums[3] = 0)`, `right = 4 (nums[4] = 1)`. `currentSum = -1 + 0 + 1 = 0`.
        *   `0 == 0`: Found a triplet! Add `[-1, 0, 1]` to `results`. `results = [[-1, -1, 2], [-1, 0, 1]]`.
            *   Increment `left` to `4`.
            *   Decrement `right` to `3`.
            *   Skip duplicates: `nums[left-1]` (`nums[3] = 0`) is not equal to `nums[left]` (`nums[4] = 1`).
            *   Skip duplicates: `nums[right+1]` (`nums[4] = 1`) is not equal to `nums[right]` (`nums[3] = 0`).
        *   Now `left = 4`, `right = 3`. `left` is no longer less than `right`. The inner loop terminates.

4.  **`i = 2`, `nums[i] = -1`**:
    *   `i > 0` and `nums[2] (-1)` is equal to `nums[1] (-1)`. This is a duplicate `i`. Skip and `continue`.

5.  **`i = 3`, `nums[i] = 0`**:
    *   `i > 0` but `nums[3] (0)` is not equal to `nums[2] (-1)`. Proceed.
    *   Initialize `left = 4`, `right = 5`.
    *   Target for `nums[left] + nums[right]` is `0 - 0 = 0`.
    *   **Loop (`left < right`)**:
        *   `left = 4 (nums[4] = 1)`, `right = 5 (nums[5] = 2)`. `currentSum = 0 + 1 + 2 = 3`.
        *   `3 > 0`: Need a smaller sum. `right--` -> `right = 4`.
        *   Now `left = right`. The inner loop terminates.

6.  The outer loop finishes because `i` has reached `n-2` (`6-2=4`, `i` can go up to 3).

Final `results = [[-1, -1, 2], [-1, 0, 1]]`, which matches the example output.

## Complexity Analysis

### Time Complexity
*   **Sorting**: `O(N log N)` for sorting the `nums` array.
*   **Outer loop**: The main loop iterates `N` times (for `i`).
*   **Inner two-pointer loop**: For each `i`, the `left` and `right` pointers traverse the remaining `N-i-1` elements. In the worst case, `left` and `right` together make `O(N)` movements.
*   **Total**: The dominant part is `N` (outer loop) * `N` (inner two-pointer scan) = `O(N^2)`.
Therefore, the overall time complexity is **O(N^2)**.

### Space Complexity
*   **Sorting**: Depends on the sorting algorithm. If an in-place sort like Heapsort or introsort (often used by `Arrays.sort` in Java) is used, it's `O(log N)` for recursion stack space. If Mergesort is used, it can be `O(N)` for auxiliary space. Assuming a typical implementation for competitive programming, it's often considered **O(log N)** or **O(1)** if the sort can be truly in-place.
*   **Result List**: In the worst case, the number of triplets can be `O(N^2)`. However, for complexity analysis, we usually consider the auxiliary space used by the algorithm itself, not the space for the output.
Therefore, the auxiliary space complexity is **O(log N)** (due to sorting) or **O(1)** if an in-place sort with minimal stack space is assumed. If we must include the output list, it can be O(N^2) in the worst case (e.g., for `nums = [0,0,...,0]`, although this case has `N/3` unique triplets, not `N^2`). For the algorithm's operational space, it's typically **O(log N)** for sorting.