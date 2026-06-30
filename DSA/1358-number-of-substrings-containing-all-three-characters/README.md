# [Number of Substrings Containing All Three Characters](https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/)

🟡 Medium

**Topics**: Hash Table, String, Sliding Window

## Problem Summary

Given a string `s` consisting solely of 'a', 'b', and 'c' characters, the goal is to determine the total count of substrings that contain at least one occurrence of each of these three distinct characters. The output should be this total count.

## Approach

This problem is efficiently solved using the **Sliding Window** technique. The core idea is to maintain a window `[left, right]` and expand it by moving the `right` pointer. Once the window contains at least one 'a', 'b', and 'c', we know that this window `s[left...right]` is valid. A crucial observation is that if `s[left...right]` is valid, then any substring starting at `left` and ending at `right` or any character to its right (up to the end of the string `s`) will also be valid.

1.  **Initialize**:
    *   `ans = 0`: To store the total count of valid substrings.
    *   `left = 0`: The left boundary of the sliding window.
    *   `charCounts = new int[3]`: A frequency array to keep track of character counts within the current window. `charCounts[0]` for 'a', `charCounts[1]` for 'b', `charCounts[2]` for 'c'.

2.  **Expand Window (Right Pointer)**: Iterate `right` from `0` to `s.length - 1`.
    *   For each `s[right]`, increment its corresponding count in `charCounts`.

3.  **Shrink Window (Left Pointer) and Count**: After updating `charCounts` for `s[right]`, check if the current window `s[left...right]` is valid (i.e., `charCounts[0] >= 1`, `charCounts[1] >= 1`, and `charCounts[2] >= 1`).
    *   If the window is valid:
        *   All substrings starting from `left` and ending at `right`, `right+1`, ..., `s.length-1` will be valid. The number of such substrings is `s.length - right`. Add this value to `ans`.
        *   Now, try to shrink the window from the left. Decrement the count of `s[left]` in `charCounts` and increment `left`.
        *   Continue shrinking (`while` loop) as long as the window `s[left...right]` remains valid. This ensures we count all possible valid substrings starting from `left` at the earliest possible positions.

4.  **Repeat**: Continue expanding the window with `right` and shrinking with `left` until `right` reaches the end of the string.

This approach ensures that each time we find a valid window `s[left...right]`, we efficiently count all valid substrings that *can be formed by extending this specific `left` boundary to the right*. By then shrinking `left`, we find other valid substrings that start later.

## Algorithm Walkthrough

Let's use `s = "abcabc"` and `N = 6`.

1.  **Initialize**: `ans = 0`, `left = 0`, `charCounts = {0, 0, 0}`.

2.  **`right = 0` (char 'a')**:
    *   `charCounts['a']++` -> `{1, 0, 0}`.
    *   Window `[0,0]` is invalid (missing 'b', 'c').

3.  **`right = 1` (char 'b')**:
    *   `charCounts['b']++` -> `{1, 1, 0}`.
    *   Window `[0,1]` is invalid (missing 'c').

4.  **`right = 2` (char 'c')**:
    *   `charCounts['c']++` -> `{1, 1, 1}`.
    *   Window `[0,2]` ("abc") is valid.
        *   Add `N - right = 6 - 2 = 4` to `ans`. `ans = 4`. (These correspond to "abc", "abca", "abcab", "abcabc")
        *   **Shrink**: `s[left]` is 'a'. `charCounts['a']--` -> `{0, 1, 1}`. `left++` -> `1`.
        *   Window `[1,2]` ("bc") is now invalid (no 'a'). Stop shrinking.

5.  **`right = 3` (char 'a')**:
    *   `charCounts['a']++` -> `{1, 1, 1}`.
    *   Window `[1,3]` ("bca") is valid.
        *   Add `N - right = 6 - 3 = 3` to `ans`. `ans = 4 + 3 = 7`. (These correspond to "bca", "bcab", "bcabc")
        *   **Shrink**: `s[left]` is 'b'. `charCounts['b']--` -> `{1, 0, 1}`. `left++` -> `2`.
        *   Window `[2,3]` ("ca") is now invalid (no 'b'). Stop shrinking.

6.  **`right = 4` (char 'b')**:
    *   `charCounts['b']++` -> `{1, 1, 1}`.
    *   Window `[2,4]` ("cab") is valid.
        *   Add `N - right = 6 - 4 = 2` to `ans`. `ans = 7 + 2 = 9`. (These correspond to "cab", "cabc")
        *   **Shrink**: `s[left]` is 'c'. `charCounts['c']--` -> `{1, 1, 0}`. `left++` -> `3`.
        *   Window `[3,4]` ("ab") is now invalid (no 'c'). Stop shrinking.

7.  **`right = 5` (char 'c')**:
    *   `charCounts['c']++` -> `{1, 1, 1}`.
    *   Window `[3,5]` ("abc") is valid.
        *   Add `N - right = 6 - 5 = 1` to `ans`. `ans = 9 + 1 = 10`. (This corresponds to "abc")
        *   **Shrink**: `s[left]` is 'a'. `charCounts['a']--` -> `{0, 1, 1}`. `left++` -> `4`.
        *   Window `[4,5]` ("bc") is now invalid (no 'a'). Stop shrinking.

8.  Loop finishes. Return `ans = 10`.

## Complexity Analysis

*   **Time Complexity**: O(N), where N is the length of the input string `s`.
    *   The `right` pointer iterates through the string once (N steps).
    *   The `left` pointer also iterates through the string at most once, as it only moves forward.
    *   Each character is added to `charCounts` once and removed from `charCounts` at most once.
    *   Therefore, the total number of operations is proportional to N.

*   **Space Complexity**: O(1).
    *   We use a fixed-size integer array `charCounts` of size 3 to store character frequencies ('a', 'b', 'c'). This space is independent of the input string length.