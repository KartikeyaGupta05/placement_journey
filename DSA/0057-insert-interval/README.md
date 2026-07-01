# [Insert Interval](https://leetcode.com/problems/insert-interval/)

🟡 Medium

Topics: Array

## Problem Summary

This problem asks us to insert a given `newInterval` into an existing sorted list of non-overlapping `intervals`. The goal is to return a new list that remains sorted by start times and contains no overlapping intervals, merging them if necessary.

## Approach

The core idea is to process the existing `intervals` array in a single pass, categorizing each interval relative to the `newInterval` into three distinct phases:

1.  **Intervals Before `newInterval`**: Collect all intervals that end before `newInterval` begins and do not overlap with it. These intervals can be added directly to our result list.
2.  **Overlapping Intervals**: Identify all intervals that overlap with `newInterval`. For each such interval, we merge it into `newInterval` by updating `newInterval`'s start to the minimum of the current `newInterval`'s start and the overlapping interval's start, and similarly for the end. This process effectively expands `newInterval` to encompass all overlapping segments.
3.  **Intervals After `newInterval`**: Once all overlaps have been processed and `newInterval` has been finalized (and added to the result), collect any remaining intervals that start after `newInterval` ends. These intervals also do not overlap and can be added directly to the result.

This approach ensures that the final list is sorted and contains no overlaps.

## Algorithm Walkthrough

Let's trace the algorithm with **Example 2**:
`intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]`, `newInterval = [4,8]`

1.  Initialize `result = []` (an empty list to store the final intervals), `i = 0` (index for `intervals`), and `newInterval = [4,8]`.

2.  **Phase 1: Add intervals completely before `newInterval`**
    *   `i = 0`: Current interval is `[1,2]`.
        *   `intervals[0][1] (2) < newInterval[0] (4)` is `true`.
        *   Add `[1,2]` to `result`. `result = [[1,2]]`.
        *   Increment `i` to `1`.
    *   `i = 1`: Current interval is `[3,5]`.
        *   `intervals[1][1] (5) < newInterval[0] (4)` is `false`. (5 is not less than 4).
        *   Exit Phase 1 loop.

3.  **Phase 2: Merge overlapping intervals with `newInterval`**
    *   `i = 1`: Current interval is `[3,5]`.
        *   `intervals[1][0] (3) <= newInterval[1] (8)` is `true`. (Overlap detected: `[3,5]` overlaps with `[4,8]`).
        *   Update `newInterval`:
            *   `newInterval[0] = Math.min(newInterval[0], intervals[1][0]) = Math.min(4, 3) = 3`.
            *   `newInterval[1] = Math.max(newInterval[1], intervals[1][1]) = Math.max(8, 5) = 8`.
        *   `newInterval` is now `[3,8]`.
        *   Increment `i` to `2`.
    *   `i = 2`: Current interval is `[6,7]`.
        *   `intervals[2][0] (6) <= newInterval[1] (8)` is `true`. (Overlap detected: `[6,7]` overlaps with `[3,8]`).
        *   Update `newInterval`:
            *   `newInterval[0] = Math.min(newInterval[0], intervals[2][0]) = Math.min(3, 6) = 3`.
            *   `newInterval[1] = Math.max(newInterval[1], intervals[2][1]) = Math.max(8, 7) = 8`.
        *   `newInterval` is now `[3,8]`.
        *   Increment `i` to `3`.
    *   `i = 3`: Current interval is `[8,10]`.
        *   `intervals[3][0] (8) <= newInterval[1] (8)` is `true`. (Overlap detected: `[8,10]` overlaps with `[3,8]`).
        *   Update `newInterval`:
            *   `newInterval[0] = Math.min(newInterval[0], intervals[3][0]) = Math.min(3, 8) = 3`.
            *   `newInterval[1] = Math.max(newInterval[1], intervals[3][1]) = Math.max(8, 10) = 10`.
        *   `newInterval` is now `[3,10]`.
        *   Increment `i` to `4`.
    *   `i = 4`: Current interval is `[12,16]`.
        *   `intervals[4][0] (12) <= newInterval[1] (10)` is `false`. (12 is not less than or equal to 10).
        *   Exit Phase 2 loop.

4.  **Phase 3: Add the merged `newInterval`**
    *   The `newInterval` is now `[3,10]`.
    *   Add `[3,10]` to `result`. `result = [[1,2], [3,10]]`.

5.  **Phase 4: Add intervals completely after `newInterval`**
    *   `i = 4`: Current interval is `[12,16]`.
        *   `i < intervals.length` (4 < 5) is `true`.
        *   Add `[12,16]` to `result`. `result = [[1,2], [3,10], [12,16]]`.
        *   Increment `i` to `5`.
    *   `i = 5`: `i < intervals.length` (5 < 5) is `false`.
        *   Exit Phase 4 loop.

6.  Return `result`: `[[1,2], [3,10], [12,16]]`. This matches the expected output.

## Complexity Analysis

*   **Time Complexity**: The algorithm iterates through the `intervals` array exactly once. Each interval is processed a constant number of times (either added to the result list or used to update `newInterval`). Therefore, the time complexity is **O(N)**, where N is the number of intervals in the input array.

*   **Space Complexity**: We create a new list (`result`) to store the output intervals. In the worst case, if no merging occurs (or only `newInterval` is merged without affecting the count of other intervals), the `result` list could contain up to N (original intervals) + 1 (new interval) entries. Thus, the space complexity is **O(N)**.