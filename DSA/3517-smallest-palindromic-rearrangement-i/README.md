# [Smallest Palindromic Rearrangement I](https://leetcode.com/problems/smallest-palindromic-rearrangement-i/)

🟡 Medium

**Topics**: String, Sorting, Counting Sort

## Problem Summary

Given an input string `s` that is guaranteed to be a palindrome, the task is to rearrange its characters to form a new string that is also a palindrome and is lexicographically the smallest possible. This means constructing a palindrome that would appear earliest in dictionary order using all characters from the original string `s`.

## Approach

The core idea is to leverage the property of lexicographical ordering: to make a string as small as possible, we want to place the smallest characters at its beginning. Since we are constructing a palindrome, any character placed at the beginning must also be mirrored at the end.

1.  **Character Frequency Counting**: First, we count the occurrences of each character in the input string `s`. An array of size 26 (for 'a' through 'z') can efficiently store these counts.

2.  **Identify the Middle Character**: A palindrome can have at most one character with an odd frequency. If the input string has an odd length, there will be exactly one such character; if it has an even length, all character frequencies will be even. This character, if it exists, will form the very center of our new palindrome. We iterate through our frequency counts to find this character.

3.  **Construct the First Half**: To ensure the lexicographically smallest palindrome, we build its first half by iterating through characters from 'a' to 'z'. For each character `c` with an *even* count (after potentially using one instance for the middle character):
    *   We append `count[c] / 2` instances of `c` to a `StringBuilder`. This forms the first half of our palindrome. By iterating `c` from 'a' to 'z', we guarantee that the smallest available characters are placed earliest in this first half.

4.  **Assemble the Final Palindrome**: The complete smallest palindromic rearrangement is formed by concatenating:
    *   The `StringBuilder` containing the first half.
    *   The single middle character (if one was identified).
    *   The reversed version of the `StringBuilder` (which forms the second half of the palindrome).

## Algorithm Walkthrough

Let's use `s = "daccad"` as an example.

1.  **Count Character Frequencies**:
    *   `a`: 2
    *   `c`: 2
    *   `d`: 2
    *   All other characters: 0

2.  **Identify Middle Character**:
    *   We iterate through the counts. All counts (2, 2, 2) are even.
    *   Therefore, there is no character with an odd frequency to be the single middle character. `middleChar` will be an empty string.

3.  **Construct the First Half**:
    *   Initialize `firstHalf = new StringBuilder()`.
    *   Iterate from 'a' to 'z':
        *   **'a'**: Count is 2. Append `2 / 2 = 1` 'a' to `firstHalf`. `firstHalf` is now "a".
        *   **'b'**: Count is 0. Skip.
        *   **'c'**: Count is 2. Append `2 / 2 = 1` 'c' to `firstHalf`. `firstHalf` is now "ac".
        *   **'d'**: Count is 2. Append `2 / 2 = 1` 'd' to `firstHalf`. `firstHalf` is now "acd".
        *   ... (continue for 'e' through 'z', all counts are 0)

4.  **Assemble the Final Palindrome**:
    *   `firstHalf` = "acd"
    *   `middleChar` = "" (empty string)
    *   `firstHalf.reverse()` = "dca"
    *   Result: `firstHalf` + `middleChar` + `firstHalf.reverse()`
        *   "acd" + "" + "dca" = "acddca"

This matches Example 3's output.

## Complexity Analysis

### Time Complexity

*   **O(N)**: Where `N` is the length of the input string `s`.
    *   Counting character frequencies takes O(N) as we iterate through the input string once.
    *   Identifying the middle character takes O(1) (26 iterations for English alphabet).
    *   Building the first half involves iterating 26 times (O(1)) and appending characters. The total number of `append` operations sums up to `N/2` characters.
    *   Reversing the `StringBuilder` takes O(N/2) time, and final string concatenation takes O(N) time.
    *   Overall, the dominant operation is iterating through the input string and building the result, making the total time complexity O(N).

### Space Complexity

*   **O(N)**:
    *   A frequency array of size 26 is used, which is O(1) as it's constant regardless of `N`.
    *   The `StringBuilder` used to construct the first half (and ultimately the full palindrome) will store up to `N` characters.
    *   Therefore, the space complexity is dominated by the storage for the resulting palindrome, making it O(N).