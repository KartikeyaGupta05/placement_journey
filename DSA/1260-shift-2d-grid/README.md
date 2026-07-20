# [Shift 2D Grid](https://leetcode.com/problems/shift-2d-grid/)

🟢 Easy
Topics: Array, Matrix, Simulation

## Problem Summary

Given a 2D grid of integers and an integer `k`, the task is to shift the grid's elements `k` times. In a single shift, each element moves one position to its right (i.e., `grid[i][j]` moves to `grid[i][j+1]`). The element in the last column of a row (`grid[i][n-1]`) moves to the first column of the next row (`grid[i+1][0]`), and the element at the very last cell (`grid[m-1][n-1]`) wraps around to the top-left cell (`grid[0][0]`). The goal is to return the state of the grid after `k` such shifts.

## Approach

The core idea is to treat the 2D grid as a 1D array for conceptual shifting, as the shift operation effectively cycles all elements. Instead of simulating `k` individual shifts (which can be inefficient if `k` is large), we can determine the final position of each element directly.

1.  **Flatten Conceptually**: Imagine the `m x n` grid elements are laid out in a single 1D array of `m * n` elements, reading row by row. An element at `grid[r][c]` corresponds to the `(r * n + c)`-th element in this flattened 1D array.

2.  **Effective Shifts**: Since the shift is cyclic, `k` shifts are equivalent to `k % (m * n)` shifts. We calculate `total_elements = m * n` and `k_effective = k % total_elements`.

3.  **Reverse Mapping to Populate New Grid**: We create a new `m x n` grid (let's call it `shiftedGrid`) to store the result. To fill `shiftedGrid` efficiently, for each cell `(r, c)` in `shiftedGrid`, we determine which element from the *original* grid should occupy this position.
    *   First, calculate the 1D index of the current `shiftedGrid[r][c]` cell: `new_flat_idx = r * n + c`.
    *   Then, determine the 1D index `old_flat_idx` from the *original* grid that *moves into* `new_flat_idx` after `k_effective` shifts. This can be found by reversing the shift: `old_flat_idx = (new_flat_idx - k_effective + total_elements) % total_elements`. Adding `total_elements` before the modulo ensures that the result is always non-negative, even if `new_flat_idx - k_effective` is negative.
    *   Finally, convert `old_flat_idx` back to its 2D coordinates `(old_r, old_c)` in the original grid: `old_r = old_flat_idx / n` and `old_c = old_flat_idx % n`.
    *   Place `grid[old_r][old_c]` into `shiftedGrid[r][c]`.

This approach ensures that each element is placed in its final shifted position in `O(m * n)` time.

## Algorithm Walkthrough

Let's use Example 1: `grid = [[1,2,3],[4,5,6],[7,8,9]]`, `k = 1`.

1.  **Dimensions**: `m = 3`, `n = 3`.
2.  **Total Elements**: `total_elements = m * n = 3 * 3 = 9`.
3.  **Effective Shifts**: `k_effective = k % total_elements = 1 % 9 = 1`.
4.  **Initialize Result Grid**: Create `shiftedGrid` of size `3x3` filled with zeros:
    `shiftedGrid = [[0,0,0],[0,0,0],[0,0,0]]`

Now, we iterate through each cell `(r, c)` of `shiftedGrid` and determine its value:

*   **For `shiftedGrid[0][0]`**:
    *   `r = 0, c = 0`.
    *   `new_flat_idx = 0 * 3 + 0 = 0`.
    *   `old_flat_idx = (0 - 1 + 9) % 9 = 8 % 9 = 8`.
    *   `old_r = 8 / 3 = 2`.
    *   `old_c = 8 % 3 = 2`.
    *   `shiftedGrid[0][0] = grid[2][2] = 9`.
    `shiftedGrid` is now `[[9,0,0],[0,0,0],[0,0,0]]`

*   **For `shiftedGrid[0][1]`**:
    *   `r = 0, c = 1`.
    *   `new_flat_idx = 0 * 3 + 1 = 1`.
    *   `old_flat_idx = (1 - 1 + 9) % 9 = 0 % 9 = 0`.
    *   `old_r = 0 / 3 = 0`.
    *   `old_c = 0 % 3 = 0`.
    *   `shiftedGrid[0][1] = grid[0][0] = 1`.
    `shiftedGrid` is now `[[9,1,0],[0,0,0],[0,0,0]]`

*   **For `shiftedGrid[0][2]`**:
    *   `r = 0, c = 2`.
    *   `new_flat_idx = 0 * 3 + 2 = 2`.
    *   `old_flat_idx = (2 - 1 + 9) % 9 = 1 % 9 = 1`.
    *   `old_r = 1 / 3 = 0`.
    *   `old_c = 1 % 3 = 1`.
    *   `shiftedGrid[0][2] = grid[0][1] = 2`.
    `shiftedGrid` is now `[[9,1,2],[0,0,0],[0,0,0]]`

Continuing this process for all cells:
*   `shiftedGrid[1][0]` gets `grid[0][2]` (value `3`).
*   `shiftedGrid[1][1]` gets `grid[1][0]` (value `4`).
*   `shiftedGrid[1][2]` gets `grid[1][1]` (value `5`).
*   `shiftedGrid[2][0]` gets `grid[1][2]` (value `6`).
*   `shiftedGrid[2][1]` gets `grid[2][0]` (value `7`).
*   `shiftedGrid[2][2]` gets `grid[2][1]` (value `8`).

The final `shiftedGrid` will be:
`[[9,1,2],[3,4,5],[6,7,8]]`
This matches the expected output for Example 1.

## Complexity Analysis

*   **Time Complexity**: `O(m * n)`
    We iterate through each cell of the `m x n` grid exactly once to populate the new `shiftedGrid`. Each calculation inside the loop (arithmetic operations, modulo, division) takes constant time. Therefore, the total time complexity is proportional to the number of elements in the grid.

*   **Space Complexity**: `O(m * n)`
    We create a new `m x n` grid (`shiftedGrid`) to store the result of the shift operation. This new grid requires `m * n` space. If modifying the input grid in-place were allowed and feasible, space could be `O(1)`, but standard practice for returning a "new" grid typically requires additional space.