# 1840. Maximum Building Height

**Difficulty:** Hard | **Topic:** Math | **Tags:** Array, Math, Sorting

---

## Problem

You want to build `n` new buildings in a city. The new buildings will be built in a line and are labeled from 1 to `n`.

However, there are city restrictions on the heights of the new buildings:

*   The height of each building must be a non-negative integer.
*   The height of the first building (building 1) must be 0.
*   The height difference between any two adjacent buildings cannot exceed 1 (i.e., `|height[i] - height[i-1]| <= 1`).

Additionally, there are city restrictions on the maximum height of specific buildings. These restrictions are given as a 2D integer array `restrictions` where `restrictions[i] = [idi, maxHeighti]` indicates that building `idi` must have a height less than or equal to `maxHeighti`.

It is guaranteed that each building will appear at most once in `restrictions`, and building 1 will not be in `restrictions`.

Return the maximum possible height of the tallest building.

**Constraints:**

*   `2 <= n <= 10^9`
*   `0 <= restrictions.length <= min(n - 1, 10^5)`
*   `2 <= idi <= n`
*   `idi` is unique.
*   `0 <= maxHeighti <= 10^9`

## Examples

**Example 1:**

```
Input: n = 5, restrictions = [[2,1],[4,1]]
Output: 2
Explanation: We can build the buildings with heights [0,1,2,1,2], and the tallest building has a height of 2.
```

**Example 2:**

```
Input: n = 6, restrictions = []
Output: 5
Explanation: We can build the buildings with heights [0,1,2,3,4,5], and the tallest building has a height of 5.
```

**Example 3:**

```
Input: n = 10, restrictions = [[5,3],[2,5],[7,4],[10,3]]
Output: 5
Explanation: We can build the buildings with heights [0,1,2,3,3,4,4,5,4,3], and the tallest building has a height of 5.
```

## Approach

The problem asks us to find the maximum possible height of the tallest building given several constraints: a non-negative height, building 1 must be height 0, adjacent buildings can differ by at most 1 in height, and specific buildings have a maximum allowed height.

The core idea revolves around understanding how heights propagate due to the adjacency constraint and how to combine this with explicit maximum height restrictions.

1.  **Consolidate and Sort Restrictions:**
    *   The problem states building 1 must have height 0 and will not be in `restrictions`. To simplify processing, we add an implicit restriction `[1, 0]` to our list of restrictions.
    *   Sort all restrictions (including `[1, 0]`) by their building ID in ascending order. This allows us to process them sequentially. Let's call this sorted list `R`. Each entry `R[i]` will be `[id_i, current_max_height_i]`.

2.  **Left-to-Right Pass (Propagating Constraints from Left):**
    *   Iterate through the sorted restrictions from left to right, starting from the second restriction (`R[1]`).
    *   For each restriction `R[i] = [id_i, max_h_i]`, its height is constrained by `R[i-1] = [id_{i-1}, max_h_{i-1}]`. Specifically, due to the adjacency rule, the height of building `id_i` cannot exceed `max_h_{i-1} + (id_i - id_{i-1})`. This is because `id_i - id_{i-1}` is the minimum possible height difference if heights strictly increase by 1 at each step.
    *   We update `max_h_i = min(max_h_i, max_h_{i-1} + (id_i - id_{i-1}))`. This ensures that the height of building `id_i` respects both its original explicit maximum height and the constraint propagated from its left neighbor (`R[i-1]`).

3.  **Right-to-Left Pass (Propagating Constraints from Right):**
    *   After the left-to-right pass, the `max_h_i` values in `R` incorporate constraints from building 1 and all preceding explicit restrictions. Now, we need to consider constraints from the right.
    *   Iterate through the sorted restrictions from right to left, starting from the second to last restriction (`R[size-2]`).
    *   For each restriction `R[i] = [id_i, max_h_i]`, its height is also constrained by `R[i+1] = [id_{i+1}, max_h_{i+1}]`. Specifically, its height cannot exceed `max_h_{i+1} + (id_{i+1} - id_i)`.
    *   We update `max_h_i = min(max_h_i, max_h_{i+1} + (id_{i+1} - id_i))`. This final update ensures that `max_h_i` for each `R[i]` is the absolute maximum possible height for building `id_i`, respecting all explicit restrictions and the adjacency rule from both directions.

4.  **Calculate Overall Maximum Height:**
    *   After the two passes, `R[i].current_max_height` represents the maximum possible height for building `R[i].id`. The overall maximum height might be one of these values, or it might be a building *between* two restricted buildings.
    *   For any two adjacent restrictions in our sorted list `R[i-1]` and `R[i]` (let them be `(id_prev, h_prev)` and `(id_curr, h_curr)`), the buildings between them can form a "peak". The maximum height a building can reach in this segment `(id_prev, id_curr)` occurs at the peak.
    *   The height of this peak can be calculated using a specific formula (explained in the "Formula" section). Iterate through all adjacent pairs `(R[i-1], R[i])` and update the overall maximum height with the peak height found.
    *   Finally, we must consider the buildings after the last restriction `R[last]`. The maximum height for buildings from `R[last].id` to `n` can reach `R[last].current_max_height + (n - R[last].id)`. Update the overall maximum height with this value.

The maximum height found through these steps will be the answer.

## Formula

The maximum height for a building located between two restricted buildings `(x1, y1)` and `(x2, y2)` where `x2 > x1` and `y1, y2` are their maximum allowed heights after considering all constraints (including adjacency from both sides), can be found using the following formula:

```
max_height_between = floor((y1 + y2 + x2 - x1) / 2)
```

This formula works by finding the intersection point of two lines: one increasing from `(x1, y1)` at a slope of +1, and another decreasing from `(x2, y2)` at a slope of -1. The intersection represents the highest point where both adjacency constraints are met.
Specifically, if we let `d = x2 - x1` be the distance between buildings, the maximum height at the peak will be `(y1 + y2 + d) / 2`.

## Dry Run

Let's trace Example 1: `n = 5`, `restrictions = [[2,1],[4,1]]`

1.  **Consolidate and Sort Restrictions:**
    *   Add `[1, 0]`: `[[1,0], [2,1], [4,1]]`
    *   Sort by ID: Already sorted.
    *   Let `R = [[1,0], [2,1], [4,1]]`. `size = 3`.

2.  **Left-to-Right Pass:**
    *   Initialize `max_overall_height = 0`.
    *   `i = 1` (restriction `R[1] = [2,1]`):
        *   `R[0] = [1,0]`. `id_curr = 2, max_h_curr = 1`. `id_prev = 1, max_h_prev = 0`.
        *   `propagated_h = max_h_prev + (id_curr - id_prev) = 0 + (2 - 1) = 1`.
        *   `R[1][1] = min(R[1][1], propagated_h) = min(1, 1) = 1`.
        *   `R` is now `[[1,0], [2,1], [4,1]]`.
    *   `i = 2` (restriction `R[2] = [4,1]`):
        *   `R[1] = [2,1]`. `id_curr = 4, max_h_curr = 1`. `id_prev = 2, max_h_prev = 1`.
        *   `propagated_h = max_h_prev + (id_curr - id_prev) = 1 + (4 - 2) = 3`.
        *   `R[2][1] = min(R[2][1], propagated_h) = min(1, 3) = 1`.
        *   `R` is now `[[1,0], [2,1], [4,1]]`.

3.  **Right-to-Left Pass:**
    *   `i = 1` (restriction `R[1] = [2,1]`):
        *   `R[2] = [4,1]`. `id_curr = 2, max_h_curr = 1`. `id_next = 4, max_h_next = 1`.
        *   `propagated_h = max_h_next + (id_next - id_curr) = 1 + (4 - 2) = 3`.
        *   `R[1][1] = min(R[1][1], propagated_h) = min(1, 3) = 1`.
        *   `R` is now `[[1,0], [2,1], [4,1]]`.
    *   `i = 0` (restriction `R[0] = [1,0]`):
        *   `R[1] = [2,1]`. `id_curr = 1, max_h_curr = 0`. `id_next = 2, max_h_next = 1`.
        *   `propagated_h = max_h_next + (id_next - id_curr) = 1 + (2 - 1) = 2`.
        *   `R[0][1] = min(R[0][1], propagated_h) = min(0, 2) = 0`.
        *   `R` is now `[[1,0], [2,1], [4,1]]`.
    *   At this point, `R` contains the final maximum allowed height for each restricted building.

4.  **Calculate Overall Maximum Height:**
    *   `max_overall_height = 0`.

    *   **Segment 1:** Between `R[0]=[1,0]` and `R[1]=[2,1]`:
        *   `x1=1, y1=0`. `x2=2, y2=1`.
        *   `peak_height = floor((y1 + y2 + x2 - x1) / 2) = floor((0 + 1 + 2 - 1) / 2) = floor(2 / 2) = 1`.
        *   `max_overall_height = max(0, 1) = 1`.

    *   **Segment 2:** Between `R[1]=[2,1]` and `R[2]=[4,1]`:
        *   `x1=2, y1=1`. `x2=4, y2=1`.
        *   `peak_height = floor((y1 + y2 + x2 - x1) / 2) = floor((1 + 1 + 4 - 2) / 2) = floor(4 / 2) = 2`.
        *   `max_overall_height = max(1, 2) = 2`.

    *   **Buildings after the last restriction `R[2]=[4,1]` up to `n=5`:**
        *   `id_last = 4`, `h_last = 1`. `n = 5`.
        *   `tail_max_height = h_last + (n - id_last) = 1 + (5 - 4) = 1 + 1 = 2`.
        *   `max_overall_height = max(2, 2) = 2`.

    *   The maximum height for any restricted building itself is also considered in `max_overall_height` (e.g., `R[1][1]=1` and `R[2][1]=1` are implicitly covered by peak calculation or the tail).

    *   Final result: `2`.

## Time Complexity

Let `M` be the number of restrictions given in the input array.
*   Adding the `[1,0]` restriction and converting to a list takes `O(1)`.
*   Sorting the `M+1` restrictions takes `O((M+1) log (M+1))`, which simplifies to `O(M log M)`.
*   The left-to-right pass iterates through `M` restrictions: `O(M)`.
*   The right-to-left pass iterates through `M` restrictions: `O(M)`.
*   The final loop to calculate the overall maximum height by checking segments and the tail also iterates through `M` segments: `O(M)`.

Therefore, the dominant factor is sorting.
**Time Complexity:** `O(M log M)` where `M` is `restrictions.length`.

## Space Complexity

*   Storing the restrictions in a `List` (or similar dynamic array structure) requires `O(M)` space.
*   The sorting operation, if not in-place, might require `O(M)` auxiliary space.

**Space Complexity:** `O(M)` where `M` is `restrictions.length`.