# [74] Search a 2D Matrix
[Link to LeetCode Problem](https://leetcode.com/problems/search-a-2d-matrix/)

![Difficulty: Medium](https://img.shields.io/badge/Difficulty-Medium-yellow.svg)

## Topics
![Topic: Array](https://img.shields.io/badge/Topic-Array-blue.svg)
![Topic: Binary Search](https://img.shields.io/badge/Topic-Binary%20Search-blue.svg)
![Topic: Matrix](https://img.shields.io/badge/Topic-Matrix-blue.svg)

## Problem Summary
This problem asks us to determine if a given `target` integer exists within an `m x n` matrix. The matrix has two crucial properties: each row is sorted in non-decreasing order, and the first element of any row is strictly greater than the last element of the previous row. We are required to find a solution with an optimal O(log(m * n)) time complexity.

## Approach
The key to solving this problem efficiently lies in recognizing that despite being a 2D matrix, the two given properties imply that the entire matrix can be treated as a single sorted 1D array. If we were to flatten the matrix row by row, the resulting 1D array would be entirely sorted in non-decreasing order. This allows us to apply a standard binary search algorithm over this conceptual 1D array.

Our strategy is to perform a single binary search on the entire `m*n` elements. We can maintain `left` and `right` pointers representing the start and end indices of this flattened array (from `0` to `m*n - 1`). In each iteration, we calculate `mid` and then map this 1D `mid` index back to its corresponding 2D coordinates `(row, col)` using integer division and modulo operations: `row = mid / n` and `col = mid % n`. We then compare `matrix[row][col]` with the `target`. Based on this comparison, we adjust `left` or `right` just like a regular binary search until the `target` is found or the search space is exhausted.

## Algorithm Walkthrough
Let's trace the algorithm with Example 1: `matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]]`, `target = 3`.
Here, `m = 3`, `n = 4`. The total number of elements is `m * n = 12`.

1.  Initialize `left = 0`, `right = 11` (since `m*n - 1 = 12 - 1 = 11`).

2.  **Iteration 1**:
    *   `mid = left + (right - left) / 2 = 0 + (11 - 0) / 2 = 5`.
    *   Map `mid = 5` to `(row, col)`: `row = 5 / 4 = 1`, `col = 5 % 4 = 1`.
    *   `matrix[1][1]` is `11`.
    *   Since `matrix[1][1] (11) > target (3)`, we need to search in the left half.
    *   Update `right = mid - 1 = 4`.
    *   Current search space: `[0, 4]`.

3.  **Iteration 2**:
    *   `mid = left + (right - left) / 2 = 0 + (4 - 0) / 2 = 2`.
    *   Map `mid = 2` to `(row, col)`: `row = 2 / 4 = 0`, `col = 2 % 4 = 2`.
    *   `matrix[0][2]` is `5`.
    *   Since `matrix[0][2] (5) > target (3)`, we need to search in the left half.
    *   Update `right = mid - 1 = 1`.
    *   Current search space: `[0, 1]`.

4.  **Iteration 3**:
    *   `mid = left + (right - left) / 2 = 0 + (1 - 0) / 2 = 0`.
    *   Map `mid = 0` to `(row, col)`: `row = 0 / 4 = 0`, `col = 0 % 4 = 0`.
    *   `matrix[0][0]` is `1`.
    *   Since `matrix[0][0] (1) < target (3)`, we need to search in the right half.
    *   Update `left = mid + 1 = 1`.
    *   Current search space: `[1, 1]`.

5.  **Iteration 4**:
    *   `mid = left + (right - left) / 2 = 1 + (1 - 1) / 2 = 1`.
    *   Map `mid = 1` to `(row, col)`: `row = 1 / 4 = 0`, `col = 1 % 4 = 1`.
    *   `matrix[0][1]` is `3`.
    *   Since `matrix[0][1] (3) == target (3)`, we found the target.
    *   Return `true`.

## Complexity Analysis
*   **Time Complexity**: O(log(m * n))
    *   We perform a single binary search on a conceptual 1D array of `m * n` elements. Each step of the binary search effectively halves the search space. The operations for calculating `mid` and mapping the 1D index to 2D coordinates `(row, col)` take constant time.
*   **Space Complexity**: O(1)
    *   The algorithm uses a constant number of extra variables (`left`, `right`, `mid`, `row`, `col`) regardless of the input matrix size. Therefore, the space complexity is constant.