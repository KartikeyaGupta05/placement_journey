# [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

🟡 Medium
**Topics**: Array, Two Pointers, Greedy

## Problem Summary
Given an array `height` representing `n` vertical lines, where `height[i]` is the height of the line at index `i`. The objective is to find two lines that, along with the x-axis, can form a container capable of holding the maximum amount of water. The container cannot be slanted.

## Approach
This problem is efficiently solved using a **Two-Pointers** technique. We initialize two pointers, `l` at the beginning of the `height` array (index 0) and `r` at the end (index `height.length - 1`). The area of a container formed by the lines at `l` and `r` is determined by `min(height[l], height[r]) * (r - l)`, where `(r - l)` is the width.

In each step, we calculate the current area and update our maximum area found so far. To maximize the area, we need to balance increasing the height and maintaining a sufficient width. Since the width `(r - l)` will always decrease as we move the pointers inward, our strategy focuses on maximizing the limiting factor: the height. The height of the container is always limited by the *shorter* of the two lines. Therefore, we greedily move the pointer corresponding to the shorter line inward. This is because moving the taller pointer would not increase the height (as it's still limited by the shorter line) but would definitely decrease the width, potentially leading to a smaller area. By moving the shorter pointer, we hope to find a taller line that could potentially form a larger container with the remaining lines, compensating for the reduced width. This process continues until `l` and `r` meet.

## Algorithm Walkthrough
Let's trace the execution with the example `height = [1,8,6,2,5,4,8,3,7]`.

*   `l = 0`, `r = 8` (`height[l] = 1`, `height[r] = 7`)
*   `mW = 0` (Maximum Water)

1.  **Iteration 1**:
    *   `ht = min(height[0], height[8]) = min(1, 7) = 1`
    *   `w = 8 - 0 = 8`
    *   `cW = ht * w = 1 * 8 = 8`
    *   `mW = max(0, 8) = 8`
    *   Since `height[0]` (1) < `height[8]` (7), increment `l`. Now `l = 1`.

2.  **Iteration 2**:
    *   `l = 1`, `r = 8` (`height[l] = 8`, `height[r] = 7`)
    *   `ht = min(height[1], height[8]) = min(8, 7) = 7`
    *   `w = 8 - 1 = 7`
    *   `cW = ht * w = 7 * 7 = 49`
    *   `mW = max(8, 49) = 49`
    *   Since `height[1]` (8) > `height[8]` (7), decrement `r`. Now `r = 7`.

3.  **Iteration 3**:
    *   `l = 1`, `r = 7` (`height[l] = 8`, `height[r] = 3`)
    *   `ht = min(height[1], height[7]) = min(8, 3) = 3`
    *   `w = 7 - 1 = 6`
    *   `cW = ht * w = 3 * 6 = 18`
    *   `mW = max(49, 18) = 49`
    *   Since `height[1]` (8) > `height[7]` (3), decrement `r`. Now `r = 6`.

4.  **Iteration 4**:
    *   `l = 1`, `r = 6` (`height[l] = 8`, `height[r] = 8`)
    *   `ht = min(height[1], height[6]) = min(8, 8) = 8`
    *   `w = 6 - 1 = 5`
    *   `cW = ht * w = 8 * 5 = 40`
    *   `mW = max(49, 40) = 49`
    *   Since `height[1]` (8) == `height[6]` (8), decrement `r` (as per the code's `else` branch). Now `r = 5`.

5.  **Iteration 5**:
    *   `l = 1`, `r = 5` (`height[l] = 8`, `height[r] = 4`)
    *   `ht = min(height[1], height[5]) = min(8, 4) = 4`
    *   `w = 5 - 1 = 4`
    *   `cW = ht * w = 4 * 4 = 16`
    *   `mW = max(49, 16) = 49`
    *   Since `height[1]` (8) > `height[5]` (4), decrement `r`. Now `r = 4`.

6.  **Iteration 6**:
    *   `l = 1`, `r = 4` (`height[l] = 8`, `height[r] = 5`)
    *   `ht = min(height[1], height[4]) = min(8, 5) = 5`
    *   `w = 4 - 1 = 3`
    *   `cW = ht * w = 5 * 3 = 15`
    *   `mW = max(49, 15) = 49`
    *   Since `height[1]` (8) > `height[4]` (5), decrement `r`. Now `r = 3`.

7.  **Iteration 7**:
    *   `l = 1`, `r = 3` (`height[l] = 8`, `height[r] = 2`)
    *   `ht = min(height[1], height[3]) = min(8, 2) = 2`
    *   `w = 3 - 1 = 2`
    *   `cW = ht * w = 2 * 2 = 4`
    *   `mW = max(49, 4) = 49`
    *   Since `height[1]` (8) > `height[3]` (2), decrement `r`. Now `r = 2`.

8.  **Iteration 8**:
    *   `l = 1`, `r = 2` (`height[l] = 8`, `height[r] = 6`)
    *   `ht = min(height[1], height[2]) = min(8, 6) = 6`
    *   `w = 2 - 1 = 1`
    *   `cW = ht * w = 6 * 1 = 6`
    *   `mW = max(49, 6) = 49`
    *   Since `height[1]` (8) > `height[2]` (6), decrement `r`. Now `r = 1`.

The loop terminates because `l` (1) is no longer less than `r` (1).
The final maximum water found is `49`.

## Complexity Analysis
*   **Time Complexity**: O(N)
    *   The two pointers `l` and `r` start at opposite ends of the array and move towards each other, traversing the array at most once. Each step within the `while` loop involves a constant number of operations (comparisons, arithmetic calculations, and pointer increments/decrements). Therefore, the total time complexity is linear with respect to the number of lines `N`.
*   **Space Complexity**: O(1)
    *   Only a few constant extra variables (`mW`, `l`, `r`, `ht`, `w`, `cW`) are used to store state during the execution, regardless of the input array's size. No auxiliary data structures that scale with `N` are allocated.

## Java Solution

```java
class Solution {
    public int maxArea(int[] height) {
        int mW = 0; // maximum water
        int l = 0; // left pointer
        int r = height.length - 1; // right pointer

        // Continue as long as the left pointer is to the left of the right pointer
        while(l < r) {
            // Calculate the height of the container, limited by the shorter line
            int ht = Math.min(height[r], height[l]);
            // Calculate the width of the container
            int w = r - l;
            // Calculate the current water contained
            int cW = ht * w;
            // Update the maximum water found so far
            mW = Math.max(mW, cW);

            // Move the pointer that points to the shorter line inward
            // This is a greedy choice, as moving the taller line
            // would not increase the height (still limited by the shorter)
            // but would definitely decrease the width.
            if(height[l] < height[r]) {
                l++; // Move left pointer right
            } else {
                r--; // Move right pointer left
            }
        }
        return mW;
    }
}

```