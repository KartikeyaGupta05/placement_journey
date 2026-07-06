# Remove Covered Intervals

🔗 [LeetCode Problem Link](https://leetcode.com/problems/remove-covered-intervals/)

## Difficulty
🟡 Medium

## Topics
Array, Sorting

## Problem Summary
Given a list of intervals, where each `intervals[i] = [li, ri)` represents a half-open interval. The goal is to identify and remove all intervals that are completely covered by another interval in the list. An interval `[a, b)` is covered by `[c, d)` if `c <= a` and `b <= d`. The task is to return the count of intervals remaining after all covered intervals have been removed.

## Approach

The most effective strategy to solve this problem involves sorting the intervals and then iterating through the sorted list to identify and count uncovered intervals.

1.  **Sorting Strategy**: The key to this problem lies in a specific sorting order. We need to sort the intervals primarily by their start points (`li`) in ascending order. If two intervals have the same start point, we then sort them by their end points (`ri`) in *descending* order.
    *   Why `li` ascending? This ensures we process intervals from left to right.
    *   Why `ri` descending for ties? If `[1,5]` and `[1,4]` both start at `1`, sorting `[1,5]` before `[1,4]` allows us to establish `[1,5]` as the "covering" interval first. When we later encounter `[1,4]`, we can easily determine it's covered by `[1,5]`. If sorted `[1,4]` then `[1,5]`, `[1,4]` would incorrectly be considered uncovered initially.

2.  **Iterating and Counting**: After sorting, we iterate through the intervals, maintaining two pieces of information:
    *   `remaining_intervals`: A counter for intervals that are not covered.
    *   `max_end_so_far`: The maximum end point encountered among the intervals processed *so far* that have *not* been covered. Initialize this to a very small value (e.g., `-1`).

3.  For each `current_interval = [current_l, current_r]` in the sorted list:
    *   **Check for Coverage**: If `current_r` is greater than `max_end_so_far`, it means this `current_interval` extends beyond any previously established "uncovered" interval. Therefore, `current_interval` itself is not covered by any prior interval (given our sorting, `current_l` is already `_>=` previous `l`s).
        *   In this case, `current_interval` is an uncovered interval. Increment `remaining_intervals`.
        *   Update `max_end_so_far = max(max_end_so_far, current_r)`.
    *   **Interval is Covered**: If `current_r` is less than or equal to `max_end_so_far`, it means this `current_interval` is entirely contained within an existing uncovered interval (whose end point contributed to `max_end_so_far`). We simply ignore it, effectively "removing" it.

Finally, return `remaining_intervals`.

## Algorithm Walkthrough

Let's use `intervals = [[1,4],[3,6],[2,8]]` as an example.

1.  **Sort the intervals**:
    *   Original: `[[1,4],[3,6],[2,8]]`
    *   Sort by `l` ascending, then `r` descending for ties:
        *   `[1,4]`
        *   `[2,8]`
        *   `[3,6]`
    *   Sorted list: `[[1,4],[2,8],[3,6]]` (In this specific example, there are no ties in `l`, so `r` descending for ties doesn't change the order from a simple `l` ascending sort).

2.  **Initialize**:
    *   `remaining_intervals = 0`
    *   `max_end_so_far = -1` (a value smaller than any possible interval end)

3.  **Iterate through sorted intervals**:

    *   **Interval 1: `[1,4]`**
        *   `current_l = 1`, `current_r = 4`.
        *   Is `current_r` (`4`) > `max_end_so_far` (`-1`)? Yes.
        *   This interval is not covered.
        *   Increment `remaining_intervals` to `1`.
        *   Update `max_end_so_far = max(-1, 4) = 4`.

    *   **Interval 2: `[2,8]`**
        *   `current_l = 2`, `current_r = 8`.
        *   Is `current_r` (`8`) > `max_end_so_far` (`4`)? Yes.
        *   This interval is not covered.
        *   Increment `remaining_intervals` to `2`.
        *   Update `max_end_so_far = max(4, 8) = 8`.

    *   **Interval 3: `[3,6]`**
        *   `current_l = 3`, `current_r = 6`.
        *   Is `current_r` (`6`) > `max_end_so_far` (`8`)? No (6 is not greater than 8).
        *   This interval is covered by a previous interval (specifically `[2,8]`, which contributed to `max_end_so_far = 8`).
        *   Do nothing.

4.  **End of iteration**: All intervals processed.

5.  **Result**: Return `remaining_intervals`, which is `2`.

## Complexity Analysis

*   **Time Complexity**: `O(N log N)`
    *   The dominant operation is sorting the `N` intervals, which takes `O(N log N)` time.
    *   The subsequent single pass through the sorted intervals takes `O(N)` time.
    *   Therefore, the total time complexity is `O(N log N)`.

*   **Space Complexity**: `O(N)` or `O(log N)`
    *   If the sorting algorithm uses auxiliary space (like Timsort, which Java's `Arrays.sort` uses for objects), the space complexity is `O(N)`.
    *   If an in-place sorting algorithm (like QuickSort) is used, the space complexity would be `O(log N)` due to the recursion stack.
    *   No additional data structures are used beyond a few variables for counting and tracking `max_end_so_far`.