# Smallest Palindromic Rearrangement II

🔴 Hard

**Topics**: Hash Table, Math, String, Combinatorics, Counting

## Problem Summary

Given a palindromic string `s` and an integer `k`, the task is to find the `k`-th lexicographically smallest distinct palindromic permutation of `s`. If there are fewer than `k` such permutations possible, an empty string should be returned. The core challenge involves generating and counting palindromic permutations efficiently while adhering to lexicographical order and handling potentially large numbers of permutations up to `k=10^6`.

## Approach

The problem asks for the `k`-th lexicographically smallest palindromic permutation. Palindromes are symmetric; they consist of a first half, an optional middle character (for odd-length strings), and a second half that is the reverse of the first. This means we only need to construct the first half of the palindrome, and the rest will follow. To achieve lexicographical order, we greedily choose the smallest possible character for each position in the first half.

Here's the detailed strategy:

1.  **Character Frequencies and Initialization**:
    *   Count the frequencies of all characters in the input string `s`. An `int[26]` array is sufficient for this.
    *   Identify the `middleChar`: Since `s` is guaranteed to be palindromic, at most one character will have an odd frequency. This character (if any) will form the center of the palindrome. Decrement its count by one.
    *   For all remaining characters, divide their counts by two. These represent the "pairs" of characters available to form the first half of the palindrome. The length of this first half will be `halfLen = s.length() / 2`.

2.  **Greedy Construction of the First Half**:
    *   Initialize an empty `StringBuilder` called `firstHalf`.
    *   Iterate from `i = 0` to `halfLen - 1` (for each position in the first half):
        *   For each character `ch` from 'a' to 'z':
            *   If `ch` has at least one pair available (`charCounts[ch - 'a'] > 0`):
                *   **Temporarily use `ch`**: Decrement `charCounts[ch - 'a']` by one (we are hypothetically placing `ch` at the current position `i`).
                *   **Calculate Permutations**: Determine the number of distinct palindromic permutations that can be formed for the *remaining* `halfLen - 1 - i` positions using the *currently available* `charCounts`. This is a multinomial coefficient calculation: `(remaining_slots)! / (count_a! * count_b! * ...)` where `remaining_slots` is `halfLen - 1 - i` and `count_x` is `charCounts[x - 'a']`.
                *   **Overflow Handling (`K_CAP`)**: The number of permutations can be very large. Since `k` is at most `10^6`, we don't need the exact permutation count if it exceeds this threshold. The `countPermutations` helper function caps its result at `K_CAP` (a value slightly larger than `k_max`) to prevent `long` overflows while still allowing comparisons with `k`.
                *   **Decision**:
                    *   If `k <= permutations`: This means `ch` is the correct character for the current position. Append `ch` to `firstHalf`, mark `foundCharForPosition` as true, and break from the inner `ch` loop to move to the next position `i+1`.
                    *   Else (`k > permutations`): `ch` is not the character for this position. We must skip this block of `permutations`. Decrement `k` by `permutations`, and **backtrack** by restoring `charCounts[ch - 'a']` (increment it back).
        *   If no character was found for the current position `i` (meaning `k` was too large even for the smallest possible characters), return an empty string.

3.  **Construct Final Palindrome**:
    *   Once the `firstHalf` is fully constructed, create the final `result` string by concatenating: `firstHalf + middleChar + firstHalf.reverse()`.

4.  **`countPermutations` Helper Function**:
    *   This function calculates the multinomial coefficient `N! / (k1! * k2! * ... * km!)`.
    *   It does so by iteratively calculating `C(N, k1) * C(N-k1, k2) * ...`, where `C(n, r)` is "n choose r".
    *   The `C(n, r)` calculation is also done iteratively: `C(n, r) = C(n, r-1) * (n-r+1) / r`.
    *   Crucially, at each multiplication and division step, it checks for potential `long` overflows and caps the intermediate and final results at `K_CAP`. This ensures correctness for `k` comparisons without needing `BigInteger`.

### Complexity Analysis

*   **Time Complexity**: `O(L^2 * A)`
    *   Initial character frequency counting: `O(L)` where `L` is `s.length()`.
    *   The main loop iterates `halfLen = L/2` times.
    *   Inside the main loop, we iterate `A = 26` (alphabet size) times for each character `'a'` to `'z'`.
    *   The `countPermutations` helper function calculates a multinomial coefficient. This is equivalent to a series of `C(n, k)` calculations. Each `C(n, k)` takes `O(min(k, n-k))` time. In `countPermutations`, the sum of all `k` values (character counts) equals `slots`. Thus, `countPermutations` takes `O(slots)` time, which is `O(halfLen)`.
    *   Total time complexity: `O(L + halfLen * A * halfLen) = O(L/2 * A * L/2) = O(L^2 * A)`.
    *   Given `L <= 10^4`, `halfLen <= 5000`. `5000 * 26 * 5000 = 6.5 * 10^8` operations in the worst case, which is on the higher side but might pass within typical time limits due to test cases not always hitting the absolute worst-case scenario.

*   **Space Complexity**: `O(L + A)`
    *   `charCounts` array: `O(A)` for the alphabet size.
    *   `freqArray`: `O(A)`.
    *   `firstHalf` StringBuilder: `O(halfLen)` which is `O(L)`.
    *   Total space complexity: `O(L)`.

## Algorithm Walkthrough

Let's trace `s = "abba", k = 2`.

1.  **Initialization**:
    *   `s.length() = 4`. `halfLen = 2`.
    *   `freqArray`: `{'a': 2, 'b': 2}`.
    *   `middleChar = 0` (no odd counts).
    *   `charCounts`: `{'a': 1, 'b': 1}` (pairs available for the first half).
    *   `firstHalf = ""` (empty `StringBuilder`).
    *   `k = 2`.

2.  **Position `i = 0` (first character of `firstHalf`)**:
    *   `remainingSlots` for `countPermutations` will be `halfLen - 1 - i = 2 - 1 - 0 = 1`.
    *   **Try `ch = 'a'`**:
        *   `charCounts['a']` is `1`. Decrement `charCounts['a']` to `0`. (`charCounts` becomes `{'a':0, 'b':1}`).
        *   Call `countPermutations(1, {'a':0, 'b':1})`:
            *   `slots = 1`. Iterates through `charCounts`:
                *   For `charCounts['a']=0`: Skip.
                *   For `charCounts['b']=1`: Calculate `C(1, 1) = 1`.
            *   Returns `permutations = 1`.
        *   Compare `k` (2) with `permutations` (1): `k > permutations`.
            *   `k` becomes `k - permutations = 2 - 1 = 1`.
            *   Restore `charCounts['a']` to `1`. (`charCounts` is back to `{'a':1, 'b':1}`).
    *   **Try `ch = 'b'`**:
        *   `charCounts['b']` is `1`. Decrement `charCounts['b']` to `0`. (`charCounts` becomes `{'a':1, 'b':0}`).
        *   Call `countPermutations(1, {'a':1, 'b':0})`:
            *   `slots = 1`. Iterates through `charCounts`:
                *   For `charCounts['a']=1`: Calculate `C(1, 1) = 1`.
                *   For `charCounts['b']=0`: Skip.
            *   Returns `permutations = 1`.
        *   Compare `k` (1) with `permutations` (1): `k <= permutations`.
            *   `ch = 'b'` is the character for this position.
            *   Append `'b'` to `firstHalf`. `firstHalf` is now `"b"`.
            *   `foundCharForPosition = true`. Break from the `ch` loop.

3.  **Position `i = 1` (second character of `firstHalf`)**:
    *   `remainingSlots` for `countPermutations` will be `halfLen - 1 - i = 2 - 1 - 1 = 0`.
    *   `charCounts` is currently `{'a':1, 'b':0}` (from the previous step, 'b' pair was consumed).
    *   **Try `ch = 'a'`**:
        *   `charCounts['a']` is `1`. Decrement `charCounts['a']` to `0`. (`charCounts` becomes `{'a':0, 'b':0}`).
        *   Call `countPermutations(0, {'a':0, 'b':0})`:
            *   `slots = 0`. Iterates through `charCounts`. Returns `1` (base case: 1 way to arrange 0 items).
        *   Compare `k` (1) with `permutations` (1): `k <= permutations`.
            *   `ch = 'a'` is the character for this position.
            *   Append `'a'` to `firstHalf`. `firstHalf` is now `"ba"`.
            *   `foundCharForPosition = true`. Break from the `ch` loop.

4.  **Loop ends**. `firstHalf = "ba"`. `middleChar = 0`.

5.  **Construct Final Palindrome**:
    *   `result = firstHalf + middleChar + firstHalf.reverse()`
    *   `result = "ba" + "" + "ab"`
    *   `result = "baab"`.

The output is `"baab"`, which matches Example 1.