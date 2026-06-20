# 3612. Process String with Special Operations I

**Difficulty:** Medium | **Topic:** Stack | **Tags:** String, Simulation

---

## Problem

You are given a string `s` consisting of lowercase English letters and the special characters: `*`, `#`, and `%`.

Build a new string `result` by processing `s` according to the following rules from left to right:

*   If the character is a lowercase English letter, append it to `result`.
*   A `*` removes the last character from `result`, if it exists.
*   A `#` duplicates the current `result` and appends it to itself.
*   A `%` reverses the current `result`.

Return the final string `result` after processing all characters in `s`.

**Constraints:**
*   `1 <= s.length <= 20`
*   `s` consists of only lowercase English letters and special characters `*`, `#`, and `%`.

## Examples

**Example 1:**
```
Input: s = "a#b%*"
Output: "ba"
```

**Example 2:**
```
Input: s = "z*#"
Output: ""
```

## Approach

### Intuition

The problem requires us to simulate a series of string manipulations based on a given input string `s`. We need a data structure that allows efficient appending, deleting the last character, duplicating, and reversing. A mutable string builder (like `StringBuilder` in Java or a dynamic array of characters) is ideal for this purpose, as it offers these operations with reasonable efficiency compared to immutable strings. We will process `s` character by character, updating our `result` string builder according to the specified rules.

### Algorithm

1.  Initialize an empty mutable string builder, let's call it `currentResultBuilder`. This will hold the progressively built string.
2.  Iterate through each character `ch` in the input string `s` from left to right.
3.  For each character `ch`:
    *   **If `ch` is a lowercase English letter (i.e., `'a'` through `'z'`)**:
        *   Append `ch` to `currentResultBuilder`.
    *   **If `ch` is `'*'`**:
        *   Check if `currentResultBuilder` is not empty. If it has characters, remove the last character from it.
    *   **If `ch` is `'#'`**:
        *   Create a copy of the current content of `currentResultBuilder`.
        *   Append this copy to `currentResultBuilder`. (Effectively doubling the string's length by appending itself).
    *   **If `ch` is `'%'`**:
        *   Reverse the `currentResultBuilder` in place.
4.  After processing all characters in `s`, convert `currentResultBuilder` to an immutable string and return it.

## Formula

Not applicable. This problem involves direct simulation and string manipulation, not a mathematical formula.

## Dry Run

Let's trace the algorithm with **Example 1: `s = "a#b%*"`**

Initial state: `currentResultBuilder = ""`

| `i` | `s[i]` | Operation                               | `currentResultBuilder` (after operation) |
| :-- | :----- | :-------------------------------------- | :--------------------------------------- |
| 0   | `'a'`  | `ch` is 'a'. Append 'a'.                | `"a"`                                    |
| 1   | `'#'`  | `ch` is '#'. Duplicate `currentResultBuilder`. | `"aa"`                                   |
| 2   | `'b'`  | `ch` is 'b'. Append 'b'.                | `"aab"`                                  |
| 3   | `'%'`  | `ch` is '%'. Reverse `currentResultBuilder`. | `"baa"`                                  |
| 4   | `'*'`  | `ch` is '*'. Remove last character.     | `"ba"`                                   |

After processing all characters, the final `currentResultBuilder` is `"ba"`.
Return `"ba"`.

## Time Complexity

The time complexity is **O(N * L_max)**, where `N` is the length of the input string `s`, and `L_max` is the maximum possible length of the `currentResultBuilder` during processing.

*   We iterate through `s` once, which is `N` operations.
*   For each character in `s`:
    *   Appending a character or removing the last character from `currentResultBuilder` takes amortized O(1) time.
    *   Duplicating or reversing `currentResultBuilder` takes time proportional to its current length, O(L_current).
*   In the worst case (e.g., `s = "a##############..."`), the length of `currentResultBuilder` can double with each `'#'` character. If `s` has `k` hash characters, `L_max` can grow exponentially up to `2^k`. Since `s.length` is at most 20, `L_max` can be up to `2^(N-1)` (e.g., for `s="a" + "###############..."` where there are 19 `#`s).
*   Thus, the worst-case `L_max` is `O(2^N)`.
*   Combining these, the total time complexity is `N` (for iteration) multiplied by `O(L_max)` (for string operations in the worst case), leading to `O(N * 2^N)`. Given `N <= 20`, this is computationally feasible.

## Space Complexity

The space complexity is **O(L_max)**, where `L_max` is the maximum possible length of the `currentResultBuilder`.

*   `currentResultBuilder` stores the intermediate and final string. In the worst case, as explained above, its length can grow exponentially up to `O(2^N)`.
*   No other significant auxiliary space is used.