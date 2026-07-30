# [Minimum Number of Pushes to Type Word I](https://leetcode.com/problems/minimum-number-of-pushes-to-type-word-i/)
🟢 Easy
Topics: Math, String, Greedy

## Problem Summary
The problem asks us to calculate the minimum total pushes required to type a given `word` composed of distinct lowercase English letters. We can remap letters to any of the 8 available keys (2-9) on a telephone keypad. The cost for a letter is 1 push if it's the first on its assigned key, 2 pushes if it's the second, and so on.

## Approach
The core idea to minimize total pushes is to assign letters such that as many as possible require only 1 push, then as many as possible require 2 pushes, and so forth. Since all letters in the `word` are distinct and must be typed, and we have 8 keys available, this problem has a clear greedy solution.

**Intuition:**
To minimize pushes, we should always prioritize assigning letters to key slots that require fewer pushes. There are 8 keys.
*   The first 8 distinct letters typed can each be assigned to a unique key (keys 2-9), costing 1 push each.
*   The next 8 distinct letters (9th to 16th overall) must then be assigned as the second letter on any of the 8 keys, costing 2 pushes each.
*   The subsequent 8 distinct letters (17th to 24th overall) will be assigned as the third letter on the keys, costing 3 pushes each.
*   This pattern continues.

**Strategy:**
Given that all letters in `word` are distinct, their specific alphabetical order doesn't matter, only the total count of letters. We iterate through the letters of the `word` (or simply count them). For each letter, its "turn" (0-indexed position if we imagine assigning them one by one) determines how many pushes it will cost.

A letter at 0-indexed position `i` (meaning it's the `(i+1)`-th letter to be assigned) will cost `(i / 8) + 1` pushes.
For example:
*   Letters at positions 0 to 7 (first 8 letters): `(0 / 8) + 1 = 1` push, `(7 / 8) + 1 = 1` push.
*   Letters at positions 8 to 15 (next 8 letters): `(8 / 8) + 1 = 2` pushes, `(15 / 8) + 1 = 2` pushes.
*   And so on.

We just sum up these push counts for all letters in `word`.

## Algorithm Walkthrough
Let's use `word = "xycdefghij"` as an example.
1.  Initialize `totalPushes = 0`.
2.  The length of `word` is 10. We iterate through the letters from `i = 0` to `9`.

    *   **Letter 'x' (at `i = 0`):**
        *   Pushes for 'x' = `(0 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 1`.
    *   **Letter 'y' (at `i = 1`):**
        *   Pushes for 'y' = `(1 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 1 + 1 = 2`.
    *   **Letter 'c' (at `i = 2`):**
        *   Pushes for 'c' = `(2 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 2 + 1 = 3`.
    *   **Letter 'd' (at `i = 3`):**
        *   Pushes for 'd' = `(3 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 3 + 1 = 4`.
    *   **Letter 'e' (at `i = 4`):**
        *   Pushes for 'e' = `(4 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 4 + 1 = 5`.
    *   **Letter 'f' (at `i = 5`):**
        *   Pushes for 'f' = `(5 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 5 + 1 = 6`.
    *   **Letter 'g' (at `i = 6`):**
        *   Pushes for 'g' = `(6 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 6 + 1 = 7`.
    *   **Letter 'h' (at `i = 7`):**
        *   Pushes for 'h' = `(7 / 8) + 1 = 0 + 1 = 1`.
        *   `totalPushes = 7 + 1 = 8`.
    *   **Letter 'i' (at `i = 8`):**
        *   Pushes for 'i' = `(8 / 8) + 1 = 1 + 1 = 2`.
        *   `totalPushes = 8 + 2 = 10`.
    *   **Letter 'j' (at `i = 9`):**
        *   Pushes for 'j' = `(9 / 8) + 1 = 1 + 1 = 2`.
        *   `totalPushes = 10 + 2 = 12`.

3.  The loop finishes. The final `totalPushes` is 12, which matches the example output.

## Complexity Analysis
*   **Time Complexity**: `O(L)`, where `L` is the length of the `word`. We iterate through the word once to calculate the pushes.
*   **Space Complexity**: `O(1)`. We only use a few integer variables to store the sum and loop counter.