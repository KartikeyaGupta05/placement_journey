# [Filter Occupied Intervals](https://leetcode.com/problems/filter-occupied-intervals/)

🟡 Medium

**Topics**: Intervals, Sorting, Merge Intervals

## Problem Summary

This problem requires processing a list of `occupiedIntervals` and a single `freeInterval`. The task involves two main steps: first, merge all given `occupiedIntervals` that overlap or touch each other into a minimal set of non-overlapping intervals. Second, from these merged occupied intervals, remove all points that fall within the specified `freeInterval`. The final output should be a sorted list of non-overlapping occupied intervals.

## Approach

The solution tackles the problem in two distinct phases:

1.  **Merging Overlapping/Touching Occupied Intervals**:
    *   The first crucial step for any interval problem involving merging is to **sort** the intervals. We sort `occupiedIntervals` based on their `start` times. If `start` times are equal, the `end` times can be used as a secondary sorting criterion (though not strictly necessary for this merging logic).
    *   After sorting, we iterate through the intervals, maintaining a list of merged intervals.
    *   For each interval, we compare it with the last interval added to our merged list. If the current interval overlaps with or touches the last merged interval (i.e., `current.start <= lastMerged.end + 1`), we merge them by extending the `lastMerged.end` to `max(lastMerged.end, current.end)`.
    *   If there's no overlap or touch, the current interval is distinct and is simply added as a new merged interval to our list.

2.  **Removing the Free Interval from Merged Occupied Intervals**:
    *   Once we have a clean, sorted list of non-overlapping `mergedOccupiedIntervals`, we iterate through each of these intervals.
    *   For each `[s, e]` from the `mergedOccupiedIntervals`, we compare it against the `freeInterval = [freeStart, freeEnd]`.
    *   There are three general scenarios for an `mergedOccupiedInterval` relative to the `freeInterval`:
        *   **No Overlap**: If `e < freeStart` (interval ends before free interval starts) or `s > freeEnd` (interval starts after free interval ends), the `mergedOccupiedInterval` is entirely outside the free period. We add it directly to our final result list.
        *   **Complete Containment (Free interval covers occupied)**: If `s >= freeStart` and `e <= freeEnd`, the entire `mergedOccupiedInterval` falls within the free period. In this case, no part of it remains occupied, so we skip it.
        *   **Partial Overlap or Occupied covers Free**: If there's any overlap, we need to potentially split the `mergedOccupiedInterval`.
            *   If `s < freeStart` (the occupied interval starts before the free period), the segment `[s, freeStart - 1]` remains occupied. We add this segment to our result list, provided `s <= freeStart - 1`.
            *   If `e > freeEnd` (the occupied interval ends after the free period), the segment `[freeEnd + 1, e]` remains occupied. We add this segment to our result list, provided `freeEnd + 1 <= e`.
            *   The critical condition `start <= end` must be checked when constructing new intervals (e.g., `[s, freeStart - 1]` or `[freeEnd + 1, e]`) to ensure valid intervals are added.

This two-step approach ensures that we first simplify the occupied times and then precisely subtract the free periods.

## Algorithm Walkthrough

Let's use Example 1: `occupiedIntervals = [[2,6],[4,8],[10,10],[10,12],[14,16]]`, `freeStart = 7`, `freeEnd = 11`.

**Step 1: Merge Occupied Intervals**

1.  **Sort `occupiedIntervals`**: The given list is already sorted by start times: `[[2,6],[4,8],[10,10],[10,12],[14,16]]`.
2.  **Initialize `merged` list**: `merged = []`
3.  **Iterate and Merge**:
    *   **`[2,6]`**: `merged` is empty. Add `[2,6]`. `merged = [[2,6]]`
    *   **`[4,8]`**: Last merged is `[2,6]`. `4 <= 6 + 1` (true). Merge: `[2, max(6,8)]` becomes `[2,8]`. Update `merged = [[2,8]]`.
    *   **`[10,10]`**: Last merged is `[2,8]`. `10 > 8 + 1` (true). No overlap/touch. Add `[10,10]`. `merged = [[2,8], [10,10]]`.
    *   **`[10,12]`**: Last merged is `[10,10]`. `10 <= 10 + 1` (true). Merge: `[10, max(10,12)]` becomes `[10,12]`. Update `merged = [[2,8], [10,12]]`.
    *   **`[14,16]`**: Last merged is `[10,12]`. `14 > 12 + 1` (true). No overlap/touch. Add `[14,16]`. `merged = [[2,8], [10,12], [14,16]]`.

    **Result of Step 1**: `mergedOccupiedIntervals = [[2,8], [10,12], [14,16]]`.

**Step 2: Remove Free Interval `[7, 11]`**

1.  **Initialize `finalResult` list**: `finalResult = []`
2.  **Iterate through `mergedOccupiedIntervals`**:
    *   **Current interval `[2,8]`**: (`s=2, e=8`). `freeInterval = [7,11]`.
        *   `e < freeStart` (`8 < 7`) is false. `s > freeEnd` (`2 > 11`) is false. There is an overlap.
        *   `s < freeStart` (`2 < 7`): Yes. Add `[s, freeStart - 1]` which is `[2, 7 - 1] = [2,6]`. `finalResult = [[2,6]]`.
        *   `e > freeEnd` (`8 > 11`) is false.
    *   **Current interval `[10,12]`**: (`s=10, e=12`). `freeInterval = [7,11]`.
        *   `e < freeStart` (`12 < 7`) is false. `s > freeEnd` (`10 > 11`) is false. There is an overlap.
        *   `s < freeStart` (`10 < 7`) is false.
        *   `e > freeEnd` (`12 > 11`): Yes. Add `[freeEnd + 1, e]` which is `[11 + 1, 12] = [12,12]`. `finalResult = [[2,6], [12,12]]`.
    *   **Current interval `[14,16]`**: (`s=14, e=16`). `freeInterval = [7,11]`.
        *   `e < freeStart` (`16 < 7`) is false.
        *   `s > freeEnd` (`14 > 11`): Yes. No overlap. Add `[14,16]`. `finalResult = [[2,6], [12,12], [14,16]]`.

    **Final Result**: `[[2,6],[12,12],[14,16]]`. This matches the example output.

## Complexity Analysis

Let `N` be the number of intervals in `occupiedIntervals`.

### Time Complexity

*   **Sorting `occupiedIntervals`**: `O(N log N)`. This is the dominant factor.
*   **Merging `occupiedIntervals`**: After sorting, iterating through the intervals and merging takes `O(N)` time, as each interval is visited a constant number of times.
*   **Removing the `freeInterval`**: We iterate through the `mergedOccupiedIntervals`. In the worst case, the number of merged intervals is `N`. This step takes `O(N)` time.

Combining these steps, the overall time complexity is **O(N log N)**.

### Space Complexity

*   **Storing Sorted Intervals**: If the sorting algorithm is not in-place (e.g., using Java's `Collections.sort` on a `List`), it might take `O(N)` auxiliary space.
*   **Storing Merged Intervals**: In the worst case, no intervals overlap, so the `mergedOccupiedIntervals` list can contain all `N` original intervals. This requires `O(N)` space.
*   **Storing Final Result Intervals**: Similarly, in the worst case (e.g., a single large occupied interval split by the free interval), the final result list could contain up to `2*N` intervals (if each original interval needs to be split). This requires `O(N)` space.

Therefore, the overall space complexity is **O(N)**.