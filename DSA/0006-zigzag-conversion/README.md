# [6. Zigzag Conversion](https://leetcode.com/problems/zigzag-conversion/)

**Difficulty:** 🟡 Medium

**Topics:** 🏷️ String

## Problem Summary

The problem asks us to convert a given string into a "zigzag" pattern across a specified number of rows (`numRows`). After forming this pattern, the string is then read line by line horizontally to produce a new string. We need to implement a function that takes the original string and the number of rows, and returns the converted string.

## Approach

The core idea is to simulate the zigzag pattern by distributing characters into `numRows` separate "rows" and then concatenating these rows.

1.  **Base Case Handling:** If `numRows` is 1, the string doesn't form a zigzag pattern; it remains unchanged. In this scenario, we can directly return the original string `s`.
2.  **Initialize Rows:** Create a `List` of `StringBuilder` objects, where the size of the list is `numRows`. Each `StringBuilder` in this list will accumulate characters for a specific row.
3.  **Simulate Zigzag Traversal:**
    *   We maintain a `currentRow` index, initially set to `0`. This index tells us which `StringBuilder` (row) to append the current character to.
    *   We also use a boolean flag `goingDown`, initially `true`, to track the direction of traversal (downwards or upwards).
    *   Iterate through each character of the input string `s`:
        *   Append the current character to the `StringBuilder` at `rows.get(currentRow)`.
        *   **Direction Change Logic:**
            *   If `currentRow` reaches `numRows - 1` (the bottom row), we need to change direction and start going upwards. Set `goingDown = false`.
            *   If `currentRow` reaches `0` (the top row), we need to change direction and start going downwards. Set `goingDown = true`.
        *   **Update `currentRow`:** Based on the `goingDown` flag, increment `currentRow` if `goingDown` is `true`, otherwise decrement `currentRow`.
4.  **Concatenate Rows:** After all characters from the input string `s` have been processed and distributed into their respective `StringBuilder`s, iterate through the `List` of `StringBuilder`s. Append the contents of each `StringBuilder` (from `rows[0]` to `rows[numRows-1]`) into a final `StringBuilder`.
5.  **Return Result:** Convert the final `StringBuilder` to a `String` and return it.

This approach efficiently builds the rows character by character, avoiding complex string manipulations or 2D array constructions, and then combines them linearly.

## Algorithm Walkthrough

Let's trace the execution with `s = "PAYPALISHIRING"` and `numRows = 3`.

1.  **Initialize:**
    *   `rows = [new StringBuilder(), new StringBuilder(), new StringBuilder()]`
    *   `currentRow = 0`
    *   `goingDown = true`

2.  **Iterate through `s`:**

| Char | `currentRow` | `goingDown` | Action                                | `rows` state (conceptual) |
| :--- | :----------- | :---------- | :------------------------------------ | :------------------------ |
| `P`  | 0            | `true`      | `rows[0].append('P')`; `currentRow` becomes 1 | `["P", "", ""]`           |
| `A`  | 1            | `true`      | `rows[1].append('A')`; `currentRow` becomes 2 | `["P", "A", ""]`          |
| `Y`  | 2            | `true`      | `rows[2].append('Y')`; `currentRow` is `numRows - 1` (2), so `goingDown` becomes `false`; `currentRow` becomes 1 | `["P", "A", "Y"]`         |
| `P`  | 1            | `false`     | `rows[1].append('P')`; `currentRow` becomes 0 | `["P", "AP", "Y"]`        |
| `A`  | 0            | `false`     | `rows[0].append('A')`; `currentRow` is `0`, so `goingDown` becomes `true`; `currentRow` becomes 1 | `["PA", "AP", "Y"]`       |
| `L`  | 1            | `true`      | `rows[1].append('L')`; `currentRow` becomes 2 | `["PA", "APL", "Y"]`      |
| `I`  | 2            | `true`      | `rows[2].append('I')`; `currentRow` is `numRows - 1` (2), so `goingDown` becomes `false`; `currentRow` becomes 1 | `["PA", "APL", "YI"]`     |
| `S`  | 1            | `false`     | `rows[1].append('S')`; `currentRow` becomes 0 | `["PA", "APLS", "YI"]`    |
| `H`  | 0            | `false`     | `rows[0].append('H')`; `currentRow` is `0`, so `goingDown` becomes `true`; `currentRow` becomes 1 | `["PAH", "APLS", "YI"]`   |
| `I`  | 1            | `true`      | `rows[1].append('I')`; `currentRow` becomes 2 | `["PAH", "APLSI", "YI"]`  |
| `R`  | 2            | `true`      | `rows[2].append('R')`; `currentRow` is `numRows - 1` (2), so `goingDown` becomes `false`; `currentRow` becomes 1 | `["PAH", "APLSI", "YIR"]` |
| `I`  | 1            | `false`     | `rows[1].append('I')`; `currentRow` becomes 0 | `["PAH", "APLSII", "YIR"]`|
| `N`  | 0            | `false`     | `rows[0].append('N')`; `currentRow` is `0`, so `goingDown` becomes `true`; `currentRow` becomes 1 | `["PAHN", "APLSII", "YIR"]`|
| `G`  | 1            | `true`      | `rows[1].append('G')`; `currentRow` becomes 2 | `["PAHN", "APLSIIG", "YIR"]`|

3.  **Concatenate Rows:**
    *   `finalResult = new StringBuilder()`
    *   `finalResult.append(rows[0])` -> "PAHN"
    *   `finalResult.append(rows[1])` -> "PAHNAPLSIIG"
    *   `finalResult.append(rows[2])` -> "PAHNAPLSIIGYIR"

4.  **Return:** "PAHNAPLSIIGYIR"

This matches the expected output for Example 1.

## Complexity Analysis

### Time Complexity

*   **O(N)**, where N is the length of the input string `s`.
*   We iterate through each character of the string `s` once to distribute them into the `StringBuilder` objects.
*   Then, we iterate through the `numRows` `StringBuilder`s and append their contents to a final `StringBuilder`. In the worst case, this concatenation also involves processing each character once.
*   The `numRows` is at most `N`, so both steps contribute linearly to the total time.

### Space Complexity

*   **O(N)**, where N is the length of the input string `s`.
*   We use a `List` of `StringBuilder` objects to store all characters from the input string `s`. In total, these `StringBuilder`s will hold exactly N characters.
*   The space required for the `List` itself and the `numRows` `StringBuilder` objects is proportional to `numRows`, but since `numRows` can be at most `N`, the dominant factor is N.