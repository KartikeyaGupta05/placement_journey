# [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)

🔴 Hard

**Topics**: Hash Table, String, Sliding Window

## Problem Summary

Given two strings, `s` and `t`, the task is to find the shortest contiguous substring within `s` that contains all characters from `t`, including duplicates. If no such substring exists, an empty string should be returned. The problem requires an algorithm with O(m + n) time complexity, where `m` and `n` are the lengths of `s` and `t` respectively.

## Approach

This problem is a quintessential application of the **Sliding Window** technique, complemented by **Hash Tables** for efficient character frequency management.

1.  **Initialize Frequency Maps**: We start by creating a frequency map (`char_counts_t`) for all characters in `t`. This map stores the *required* count for each character that must be present in our window. We also initialize a `window_counts` map to track character frequencies within our current sliding window.

2.  **Sliding Window Pointers**: Two pointers, `left` and `right`, define the boundaries of our current window `s[left...right]`. The `right` pointer expands the window, while the `left` pointer contracts it.

3.  **Track Matches**: A `matched_chars` counter is crucial. It keeps track of how many characters (including duplicates) from `t` have been successfully found and accounted for within the current window. This count increments when `window_counts[char]` reaches `char_counts_t[char]` for a character `char`. The window is considered "valid" when `matched_chars` equals the total number of characters in `t` (i.e., `t.length()`).

4.  **Expand Window (Right Pointer)**:
    *   The `right` pointer iterates through `s` from left to right.
    *   For each character `s[right]`:
        *   Increment its count in `window_counts`.
        *   If `s[right]` is a character from `t` and its count in `window_counts` (after increment) is now less than or equal to its required count in `char_counts_t`, it means we've successfully matched one more required character. So, we increment `matched_chars`.

5.  **Shrink Window (Left Pointer)**:
    *   Once the window is valid (`matched_chars == t.length()`), we have a candidate substring. We record its length and starting index if it's the smallest found so far.
    *   Then, we attempt to shrink the window from the `left` to find an even smaller valid substring.
    *   For `s[left]`:
        *   Decrement its count in `window_counts`.
        *   If `s[left]` was a required character and its count in `window_counts` (after decrement) becomes strictly less than its required count in `char_counts_t`, it means removing this character makes the window insufficient for that specific character requirement. So, we decrement `matched_chars`.
    *   Increment `left`.
    *   This shrinking continues as long as the window remains valid (`matched_chars == t.length()`).

6.  **Iteration and Result**: The process of expanding `right` and conditionally shrinking `left` continues until `right` reaches the end of `s`. Finally, if a valid window was ever found (i.e., `min_len` was updated from its initial maximum value), we return the substring corresponding to the `min_start` and `min_len`. Otherwise, an empty string is returned.

## Algorithm Walkthrough

Let's trace the algorithm with `s = "ADOBECODEBANC"` and `t = "ABC"`.

**1. Initialization:**
*   `char_counts_t = {'A': 1, 'B': 1, 'C': 1}` (from `t`)
*   `window_counts = {}`
*   `left = 0`, `right = 0`
*   `min_len = infinity`, `min_start = 0`
*   `matched_chars = 0` (target is 3 characters from `t`)

**2. Expand Window (Right Pointer moves):**

| `right` | `s[right]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window (s[left...right]) | Notes                                                                                                                  |
| :------ | :--------- | :-------------------------------- | :-------------- | :------------------------------- | :--------------------------------------------------------------------------------------------------------------------- |
| 0       | 'A'        | `{'A': 1}`                        | 1               | "A"                              | `s[0]` is 'A'. `window_counts['A'] (1) <= char_counts_t['A'] (1)`. `matched_chars` becomes 1.                             |
| 1       | 'D'        | `{'A': 1}`                        | 1               | "AD"                             | `s[1]` is 'D', not in `t`.                                                                                             |
| 2       | 'O'        | `{'A': 1}`                        | 1               | "ADO"                            | `s[2]` is 'O', not in `t`.                                                                                             |
| 3       | 'B'        | `{'A': 1, 'B': 1}`                | 2               | "ADOB"                           | `s[3]` is 'B'. `window_counts['B'] (1) <= char_counts_t['B'] (1)`. `matched_chars` becomes 2.                             |
| 4       | 'E'        | `{'A': 1, 'B': 1}`                | 2               | "ADOBE"                          | `s[4]` is 'E', not in `t`.                                                                                             |
| 5       | 'C'        | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "ADOBEC"                         | `s[5]` is 'C'. `window_counts['C'] (1) <= char_counts_t['C'] (1)`. `matched_chars` becomes 3.                           |

`matched_chars` is now 3, which equals `t.length()`. The window "ADOBEC" is valid.
Current length = `right - left + 1 = 5 - 0 + 1 = 6`.
Update `min_len = 6`, `min_start = 0`.

**3. Shrink Window (Left Pointer moves):**

| `left` | `s[left]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window | Notes                                                                                                                                              |
| :----- | :-------- | :-------------------------------- | :-------------- | :------------- | :------------------------------------------------------------------------------------------------------------------------------------------------- |
| 0      | 'A'       | `{'A': 0, 'B': 1, 'C': 1}`        | 2               | "DOBEC"        | Decrement `window_counts['A']`. `window_counts['A'] (0) < char_counts_t['A'] (1)`. `matched_chars` becomes 2. Window is no longer valid. Stop shrinking. |

`left` increments to 1.

**4. Continue Expanding Window:**

| `right` | `s[right]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window (s[left...right]) | Notes                                                                                                                                                                                                                                   |
| :------ | :--------- | :-------------------------------- | :-------------- | :------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 6       | 'O'        | `{'A': 0, 'B': 1, 'C': 1}`        | 2               | "DOBECO"                         | `s[6]` is 'O', not in `t`.                                                                                                                                                                                                               |
| ...     | ...        | ...                               | ...             | ...                              | (Skipping non-relevant chars)                                                                                                                                                                                                           |
| 9       | 'B'        | `{'A': 0, 'B': 2, 'C': 1}`        | 2               | "DECODEB" (left=1)               | `s[9]` is 'B'. `window_counts['B']` becomes 2. No change to `matched_chars` since we already have enough 'B's (required 1, have 1, adding another just exceeds requirement but doesn't fill a *new* distinct requirement).                |
| 10      | 'A'        | `{'A': 1, 'B': 2, 'C': 1}`        | 3               | "DECODEBA" (left=1)              | `s[10]` is 'A'. `window_counts['A']` becomes 1. `window_counts['A'] (1) <= char_counts_t['A'] (1)`. `matched_chars` becomes 3. |

`matched_chars` is now 3. Window "DECODEBA" is valid.
Current length = `10 - 1 + 1 = 10`. This is not less than `min_len = 6`.

**5. Shrink Window:**

| `left` | `s[left]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window | Notes                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| :----- | :-------- | :-------------------------------- | :-------------- | :------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1      | 'D'       | `{'A': 1, 'B': 2, 'C': 1}`        | 3               | "ECODEBA"      | `s[1]` is 'D', not in `t`. No change to `matched_chars`. `left` increments to 2.                                                                                                                                                                                                                                                                                                                                                                                                                       |
| 2      | 'O'       | `{'A': 1, 'B': 2, 'C': 1}`        | 3               | "CODEBA"       | `s[2]` is 'O', not in `t`. No change to `matched_chars`. `left` increments to 3.                                                                                                                                                                                                                                                                                                                                                                                                                       |
| 3      | 'B'       | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "ECODEBA"      | Decrement `window_counts['B']`. `window_counts['B'] (1) <= char_counts_t['B'] (1)`. `matched_chars` remains 3. `left` increments to 4.                                                                                                                                                                                                                                                                                                                                                                   |
| 4      | 'E'       | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "CODEBA"       | `s[4]` is 'E', not in `t`. No change to `matched_chars`. `left` increments to 5.                                                                                                                                                                                                                                                                                                                                                                                                                       |
| 5      | 'C'       | `{'A': 1, 'B': 1, 'C': 0}`        | 2               | "ODEBA"        | Decrement `window_counts['C']`. `window_counts['C'] (0) < char_counts_t['C'] (1)`. `matched_chars` becomes 2. Window is no longer valid. Stop shrinking. `left` increments to 6. |

**6. Continue Expanding Window:**

| `right` | `s[right]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window (s[left...right]) | Notes                                                                                                                  |
| :------ | :--------- | :-------------------------------- | :-------------- | :------------------------------- | :--------------------------------------------------------------------------------------------------------------------- |
| 11      | 'N'        | `{'A': 1, 'B': 1, 'C': 0}`        | 2               | "ODEBANC" (left=6)               | `s[11]` is 'N', not in `t`.                                                                                             |
| 12      | 'C'        | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "ODEBANC" (left=6)               | `s[12]` is 'C'. `window_counts['C']` becomes 1. `window_counts['C'] (1) <= char_counts_t['C'] (1)`. `matched_chars` becomes 3. |

`matched_chars` is now 3. Window "ODEBANC" is valid.
Current length = `12 - 6 + 1 = 7`. This is not less than `min_len = 6`.

**7. Shrink Window:**

| `left` | `s[left]` | `window_counts` (relevant to `t`) | `matched_chars` | Current Window | Notes                                                                                                                                                                                                                                 |
| :----- | :-------- | :-------------------------------- | :-------------- | :------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 6      | 'O'       | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "DEBANC"       | `s[6]` is 'O', not in `t`. No change to `matched_chars`. `left` increments to 7. Current length `12 - 7 + 1 = 6`. `min_len = 6`, `min_start = 0`. Length is equal, but we can update `min_start = 7` to get the *latest* shortest window. |
| 7      | 'D'       | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "EBANC"        | `s[7]` is 'D', not in `t`. No change to `matched_chars`. `left` increments to 8. Current length `12 - 8 + 1 = 5`. `min_len = 5`, `min_start = 8`.                                                                                       |
| 8      | 'E'       | `{'A': 1, 'B': 1, 'C': 1}`        | 3               | "BANC"         | `s[8]` is 'E', not in `t`. No change to `matched_chars`. `left` increments to 9. Current length `12 - 9 + 1 = 4`. `min_len = 4`, `min_start = 9`.                                                                                       |
| 9      | 'B'       | `{'A': 1, 'B': 0, 'C': 1}`        | 2               | "ANC"          | Decrement `window_counts['B']`. `window_counts['B'] (0) < char_counts_t['B'] (1)`. `matched_chars` becomes 2. Window is no longer valid. Stop shrinking. `left` increments to 10. |

`right` reaches the end of `s`. Loop terminates.

**Final Result**: `min_len = 4`, `min_start = 9`.
The substring is `s.substring(9, 9 + 4)` which is **"BANC"**.

## Complexity Analysis

*   **Time Complexity**: O(m + n), where `m` is the length of `s` and `n` is the length of `t`.
    *   Pre-processing `t` to build `char_counts_t` takes O(n) time.
    *   The `right` pointer iterates through `s` once (O(m)).
    *   The `left` pointer also iterates through `s` at most once (it only ever moves forward).
    *   Hash map operations (insertion, retrieval, deletion) take O(1) on average.
    *   Therefore, the dominant operations are the linear traversals of `s` and `t`, resulting in O(m + n).

*   **Space Complexity**: O(k), where `k` is the size of the character set (alphabet).
    *   We use two hash maps (`char_counts_t` and `window_counts`) to store character frequencies.
    *   Since the problem specifies uppercase and lowercase English letters, `k` is at most 52. If we consider ASCII characters, `k` would be 256.
    *   As `k` is a fixed constant relative to the input string lengths `m` and `n`, this is often considered O(1) space complexity in competitive programming contexts.