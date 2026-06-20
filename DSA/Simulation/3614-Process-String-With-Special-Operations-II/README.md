# 3614. Process String with Special Operations II

**Difficulty:** Hard | **Topic:** Simulation | **Tags:** String, Simulation

---

## Problem

You are given a string `s` consisting of lowercase English letters and the special characters: `*`, `#`, and `%`. You are also given an integer `k`.

Build a new string `result` by processing `s` according to the following rules from left to right:

*   If the character is a lowercase English letter, append it to `result`.
*   A `*` removes the last character from `result`, if it exists.
*   A `#` duplicates the current `result` and appends it to itself.
*   A `%` reverses the current `result`.

Return the `k`-th character of the final string `result`. If `k` is out of the bounds of `result` (i.e., `k >= result.length()`), return `.`.

**Constraints:**

*   `1 <= s.length <= 10^5`
*   `s` consists of only lowercase English letters and special characters `*`, `#`, and `%`.
*   `0 <= k <= 10^15`
*   The length of `result` after processing `s` will not exceed `10^15`.

## Examples

**Example 1:**
```
Input: s = "a#b%*", k = 1
Output: "a"
Explanation:
i   s[i]    Operation                 Current result
--------------------------------------------------
0   'a'     Append 'a'                "a"
1   '#'     Duplicate result          "aa"
2   'b'     Append 'b'                "aab"
3   '%'     Reverse result            "baa"
4   '*'     Remove the last character "ba"

The final result is "ba". The character at index k = 1 is 'a'.
```

**Example 2:**
```
Input: s = "cd%#*#", k = 3
Output: "d"
Explanation:
i   s[i]    Operation                 Current result
--------------------------------------------------
0   'c'     Append 'c'                "c"
1   'd'     Append 'd'                "cd"
2   '%'     Reverse result            "dc"
3   '#'     Duplicate result          "dcdc"
4   '*'     Remove the last character "dcd"
5   '#'     Duplicate result          "dcddcd"

The final result is "dcddcd". The character at index k = 3 is 'd'.
```

**Example 3:**
```
Input: s = "z*#", k = 0
Output: "."
Explanation:
i   s[i]    Operation                 Current result
--------------------------------------------------
0   'z'     Append 'z'                "z"
1   '*'     Remove the last character ""
2   '#'     Duplicate the string      ""

The final result is "". Since index k = 0 is out of bounds, the output is '.'.
```

## Approach

The main challenge in this problem is that the length of the `result` string can grow extremely large (up to `10^15`), which means we cannot actually construct the string in memory. We need a way to determine the `k`-th character without storing the entire string. This suggests a simulation approach focusing on string length and index transformations.

The solution involves two main passes:

1.  **Forward Pass: Calculate Final Length**
    *   Iterate through the input string `s` from left to right.
    *   Maintain a variable, `current_length`, representing the length of the `result` string *if we were to build it*.
    *   For each character `c` in `s`:
        *   If `c` is a lowercase English letter: `current_length` increases by 1.
        *   If `c` is `*`: `current_length` decreases by 1, but never goes below 0. (i.e., `current_length = max(0, current_length - 1)`).
        *   If `c` is `#`: `current_length` doubles. (i.e., `current_length *= 2`).
        *   If `c` is `%`: This operation reverses the string, but its length remains unchanged. So, `current_length` does not change.
    *   After this pass, `current_length` will hold the final length of the `result` string.
    *   **Edge Case**: If `k` is greater than or equal to this `final_length`, then the `k`-th character is out of bounds, and we should immediately return `.`.

2.  **Backward Pass: Locate the Character**
    *   Now that we know the `final_length` and that `k` is within bounds, we need to find the `k`-th character. We do this by iterating through `s` from right to left, essentially *undoing* each operation and transforming `k` accordingly.
    *   Initialize `current_length` with the `final_length` obtained from the forward pass.
    *   Iterate `i` from `s.length() - 1` down to `0`. For each character `c = s[i]`:
        *   **If `c` is `*` (Remove last character):**
            *   To undo a `*`, we effectively add a character back. So, `current_length` increases by 1.
        *   **If `c` is `#` (Duplicate string):**
            *   This operation doubled the string. To undo it, `current_length` is halved.
            *   The critical part is adjusting `k`. If `k` was in the second half of the duplicated string (i.e., `k >= current_length / 2`), it now refers to the corresponding position in the first half. So, we adjust `k = k - (current_length / 2)`.
        *   **If `c` is `%` (Reverse string):**
            *   This operation reversed the string. To undo it, we reverse `k`'s position within the current `current_length`. The new `k` becomes `current_length - 1 - k`.
        *   **If `c` is a lowercase English letter (Append letter):**
            *   This operation appended `c`.
            *   If `k` is exactly at the position where this character was appended (i.e., `k == current_length - 1`), then `s[i]` is our target character. We return `s[i]`.
            *   To undo the append, `current_length` decreases by 1.
    *   This backward loop is guaranteed to find a character because we already checked that `k` is within bounds of the `final_length`, and every character eventually traces back to an appended letter.

By carefully tracking `current_length` and adjusting `k` in the backward pass, we can pinpoint the original character that occupies the `k`-th position in the final string without ever constructing it. It's crucial to use `long` data type for `k` and `current_length` to handle values up to `10^15`.

## Formula

The core of the backward pass relies on these transformations for `k` based on `current_length`:

*   **Undo `#` (Duplicate):**
    If `k >= current_length / 2` (meaning `k` was in the duplicated second half),
    then `k` shifts to the corresponding position in the first half:
    ```
    k = k - (current_length / 2)
    current_length = current_length / 2
    ```
    Otherwise, `k` remains the same relative to the first half, and only `current_length` is halved.
    A more concise way to represent the `k` adjustment is `k %= (current_length / 2)` if `k` is in the second half.

*   **Undo `%` (Reverse):**
    `k`'s new position is mirrored:
    ```
    k = (current_length - 1) - k
    ```

## Dry Run

Let's trace Example 1: `s = "a#b%*", k = 1`

Initialize `k = 1`.

**1. Forward Pass (Calculate Final Length)**

We track `current_length` (initially 0):

| `i` | `s[i]` | Operation                | `current_length` |
| :-- | :----- | :----------------------- | :--------------- |
| 0   | 'a'    | Append 'a'               | 1                |
| 1   | '#'    | Duplicate `result`       | 1 * 2 = 2        |
| 2   | 'b'    | Append 'b'               | 2 + 1 = 3        |
| 3   | '%'    | Reverse `result`         | 3 (length unchanged) |
| 4   | '*'    | Remove last character    | `max(0, 3 - 1)` = 2 |

Final `current_length` = 2.
Since `k = 1` is less than `final_length = 2`, `k` is in bounds. Proceed to backward pass.

**2. Backward Pass (Locate Character)**

We initialize `current_length` to `2` (the final length). We iterate `i` from `s.length() - 1` (4) down to `0`.

| `i` | `s[i]` | Operation (undo)             | `k` (before op) | `current_length` (before op) | `k` (after op) | `current_length` (after op) |
| :-- | :----- | :--------------------------- | :-------------- | :--------------------------- | :------------- | :-------------------------- |
| 4   | '*'    | `*` removed last char. Undo: add char back. | 1               | 2                            | 1              | 2 + 1 = 3                   |
| 3   | '%'    | `%` reversed string. Undo: reverse `k`.     | 1               | 3                            | `3 - 1 - 1` = 1 | 3                           |
| 2   | 'b'    | 'b' was appended. Is `k` at its position? (`1 == 3 - 1` is false). Undo: remove 'b'. | 1               | 3                            | 1              | 3 - 1 = 2                   |
| 1   | '#'    | `#` duplicated string. Undo: halve `current_length`. Is `k` in second half? (`1 >= 2/2` is true). `k` adjusts. | 1               | 2                            | `1 - (2/2)` = 0 | 2 / 2 = 1                   |
| 0   | 'a'    | 'a' was appended. Is `k` at its position? (`0 == 1 - 1` is true). **Found!** | 0               | 1                            |                |                             |

Since `k` matched `current_length - 1` for `s[0] = 'a'`, we return `'a'`.

## Time Complexity

The algorithm performs two passes over the input string `s`.
*   The first pass iterates `s` once to calculate the final length, taking `O(N)` time where `N` is `s.length()`.
*   The second pass iterates `s` once in reverse to locate the character, also taking `O(N)` time.
Each operation within the loops (arithmetic, comparisons) is `O(1)`.
Therefore, the total time complexity is **O(N)**.

## Space Complexity

The algorithm uses a few variables (`k`, `current_length`, loop counters) to store string lengths and the target index. These variables take a constant amount of memory, regardless of the input string length or `k`'s value.
Therefore, the space complexity is **O(1)**.