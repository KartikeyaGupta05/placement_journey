# [Concatenate Non-Zero Digits and Multiply by Sum I](https://leetcode.com/problems/concatenate-non-zero-digits-and-multiply-by-sum-i/)

🟢 Easy
Topics: Math

## Problem Summary

Given an integer `n`, the task is to first construct a new integer `x` by extracting all non-zero digits from `n` and concatenating them in their original order. If no non-zero digits exist, `x` is 0. Then, calculate `sum`, which is the sum of all digits in `x`. Finally, return the product of `x` and `sum`.

## Approach

The core idea is to carefully extract the non-zero digits of `n` while maintaining their original relative order, form the integer `x`, then calculate the sum of its digits, and finally compute the product.

1.  **Forming `x`**: To maintain the original order of digits, it's most straightforward to convert the input integer `n` into a string. This allows us to easily iterate through its digits from left to right. We can then filter out the '0' characters and append the non-zero digit characters to a `StringBuilder`. After processing all characters, if the `StringBuilder` is empty (meaning `n` contained only zeros or was 0 itself), `x` is 0. Otherwise, parse the content of the `StringBuilder` to a `long` to get the value of `x`. Using `long` is important as `n` can be up to `10^9`, and `x` could potentially be a large number formed by its non-zero digits.

2.  **Calculating `sum`**: Once `x` is formed as a `long`, we need to sum its digits. This is a standard process: repeatedly take `x % 10` to get the last digit, add it to a running total, and then update `x` by integer division `x /= 10` until `x` becomes 0.

3.  **Final Calculation**: Multiply the derived `x` by the calculated `sum` and return the result.

This approach gracefully handles edge cases such as `n = 0` (resulting in `x=0`, `sum=0`, `x*sum=0`) or `n` containing only a single non-zero digit (`n = 1000` -> `x=1`, `sum=1`, `x*sum=1`).

## Algorithm Walkthrough

Let's use `n = 10203004` as an example.

1.  **Initialize**:
    *   `long n = 10203004`
    *   `StringBuilder sbX = new StringBuilder()`

2.  **Form `x`**:
    *   Convert `n` to its string representation: `String nStr = "10203004"`.
    *   Iterate through each character `c` in `nStr`:
        *   `c = '1'`: Not '0'. `sbX.append('1')`. `sbX` is now "1".
        *   `c = '0'`: Is '0'. Do nothing.
        *   `c = '2'`: Not '0'. `sbX.append('2')`. `sbX` is now "12".
        *   `c = '0'`: Is '0'. Do nothing.
        *   `c = '3'`: Not '0'. `sbX.append('3')`. `sbX` is now "123".
        *   `c = '0'`: Is '0'. Do nothing.
        *   `c = '0'`: Is '0'. Do nothing.
        *   `c = '4'`: Not '0'. `sbX.append('4')`. `sbX` is now "1234".
    *   After iteration, `sbX` contains "1234".
    *   Check if `sbX` is empty: No.
    *   Parse `sbX` to `long`: `long x = Long.parseLong("1234")` -> `x = 1234`.

3.  **Calculate `sum`**:
    *   Initialize `long currentX = x = 1234`.
    *   Initialize `long sum = 0`.
    *   **Loop while `currentX > 0`**:
        *   `digit = currentX % 10` -> `4`. `sum = sum + 4` -> `4`. `currentX = currentX / 10` -> `123`.
        *   `digit = currentX % 10` -> `3`. `sum = sum + 3` -> `7`. `currentX = currentX / 10` -> `12`.
        *   `digit = currentX % 10` -> `2`. `sum = sum + 2` -> `9`. `currentX = currentX / 10` -> `1`.
        *   `digit = currentX % 10` -> `1`. `sum = sum + 1` -> `10`. `currentX = currentX / 10` -> `0`.
    *   Loop ends. `sum` is `10`.

4.  **Final Calculation**:
    *   Return `x * sum` -> `1234 * 10 = 12340`.

This matches Example 1 output.

## Complexity Analysis

Let `D` be the number of digits in the input integer `n`. Since `n` is up to `10^9`, `D` is at most 10.

### Time Complexity

*   **Converting `n` to string**: `O(D)` to iterate through the digits and build the string.
*   **Building `sbX`**: Iterating through `nStr` takes `O(D)` time. Appending characters to a `StringBuilder` is amortized `O(1)`, so this step is `O(D)`.
*   **Parsing `sbX` to `long x`**: `O(D_x)` where `D_x` is the number of digits in `x`. In the worst case, `D_x = D`. So, `O(D)`.
*   **Calculating `sum` of digits of `x`**: Iterating through the digits of `x` takes `O(D_x)` time, which is `O(D)`.

Combining these, the overall time complexity is **O(D)**, which is equivalent to **O(log N)** where N is the value of `n`.

### Space Complexity

*   **Storing `nStr`**: `O(D)` to store the string representation of `n`.
*   **Storing `sbX`**: In the worst case (all digits of `n` are non-zero), `sbX` will store `D` characters. So, `O(D)`.

Combining these, the overall space complexity is **O(D)**, which is equivalent to **O(log N)**.