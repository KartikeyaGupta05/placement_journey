# Substring with Concatenation of All Words

🔴 Hard

**Topics**: Hash Table, String, Sliding Window

## Problem Summary

This problem asks us to find all starting indices in a given string `s` where a substring can be formed by concatenating all words from a given array `words` in any order. All words in `words` have the same length. The concatenated substring must contain each word exactly once, without any intervening characters.

## Approach

The provided Java solution uses a brute-force sliding window approach combined with hash maps to check for word frequencies.

1.  **Pre-computation of Word Frequencies**: First, a hash map (`map`) is created to store the exact frequency of each word in the input `words` array. This `map` acts as our target frequency count.
2.  **Determine Window Size**: The length of each word (`n`) and the total number of words (`words.length`) are used to calculate the exact `window` size. This `window` represents the total length a valid concatenated substring must have (`n * words.length`).
3.  **Iterate Through `s`**: The main loop iterates through the string `s` from `i = 0` up to `s.length() - window + 1`. In each iteration, it considers a substring of `s` of length `window` starting at index `i`.
4.  **Helper Function Validation**: For each candidate substring, a `helper` function is called to determine if it is a valid concatenated string.
    *   Inside the `helper` function, a new hash map (`map2`) is initialized to count word frequencies within the current candidate substring.
    *   The candidate substring is then divided into chunks, each of length `n` (the length of a single word).
    *   For each chunk, it's added to `map2`, incrementing its count.
    *   Finally, `map.equals(map2)` is used to compare the frequencies. If they are identical, it means the candidate substring contains all words from the `words` array with the correct frequencies, and its starting index `i` is added to the result list.

This approach effectively checks every possible starting position for a valid concatenated substring by re-evaluating the word frequencies within each `window` from scratch.

## Algorithm Walkthrough

Let's trace Example 1: `s = "barfoothefoobarman"`, `words = ["foo","bar"]`

1.  **Initial Setup**:
    *   `n = words[0].length() = 3` (length of "foo" or "bar")
    *   `words.length = 2`
    *   `window = n * words.length = 3 * 2 = 6`
    *   `map = {"foo": 1, "bar": 1}` (target word frequencies)
    *   `ans = []` (result list)

2.  **Main Loop (Iterating `i` from 0 to `s.length() - window + 1 = 16 - 6 + 1 = 11`)**:

    *   **`i = 0`**:
        *   Candidate substring: `s.substring(0, 6)` = `"barfoo"`
        *   Call `helper("barfoo", map, 3)`:
            *   `map2 = {}`
            *   Chunk 1: `substring(0, 3)` = `"bar"`. `map2 = {"bar": 1}`.
            *   Chunk 2: `substring(3, 6)` = `"foo"`. `map2 = {"bar": 1, "foo": 1}`.
            *   `map.equals(map2)` is `true` (both are `{"bar": 1, "foo": 1}`).
            *   Add `i = 0` to `ans`. `ans = [0]`.

    *   **`i = 1`**:
        *   Candidate substring: `s.substring(1, 7)` = `"arfoot"`
        *   Call `helper("arfoot", map, 3)`:
            *   `map2 = {}`
            *   Chunk 1: `substring(0, 3)` = `"arf"`. `map2 = {"arf": 1}`.
            *   Chunk 2: `substring(3, 6)` = `"oot"`. `map2 = {"arf": 1, "oot": 1}`.
            *   `map.equals(map2)` is `false` (does not match `{"bar": 1, "foo": 1}`).

    *   ... (Skipping iterations where `helper` returns `false` for brevity) ...

    *   **`i = 9`**:
        *   Candidate substring: `s.substring(9, 15)` = `"foobar"`
        *   Call `helper("foobar", map, 3)`:
            *   `map2 = {}`
            *   Chunk 1: `substring(0, 3)` = `"foo"`. `map2 = {"foo": 1}`.
            *   Chunk 2: `substring(3, 6)` = `"bar"`. `map2 = {"foo": 1, "bar": 1}`.
            *   `map.equals(map2)` is `true`.
            *   Add `i = 9` to `ans`. `ans = [0, 9]`.

    *   ... (Continue until `i = 11`) ...

3.  **Result**: The function returns `ans = [0, 9]`.

## Complexity Analysis

Let `N` be the length of string `s`, `M` be the number of words in `words`, and `K` be the length of each word in `words`.

*   **Time Complexity**: `O(N * M * K)`
    *   The outer loop runs `N - (M * K) + 1` times, which is approximately `O(N)` iterations.
    *   Inside the outer loop, `s.substring(i, i + window)` creates a new string of length `M * K`. This operation takes `O(M * K)` time.
    *   The `helper` function then iterates `M` times (once for each word in the window).
        *   Inside the `helper` loop, `s.substring(i, i + n)` creates a word string of length `K`. This takes `O(K)` time.
        *   `map2.put` and `map2.getOrDefault` operations on hash maps take `O(K)` time on average, because string keys need to be hashed and compared.
        *   Finally, `map.equals(map2)` compares two hash maps. In the worst case, this involves iterating through all `M` entries in one map and comparing their keys (length `K`) and values, leading to `O(M * K)` time.
    *   Therefore, the total time for each outer loop iteration is `O(M * K) + O(M * K) = O(M * K)`.
    *   Combining the outer loop, the total time complexity is `O(N * M * K)`.

*   **Space Complexity**: `O(M * K)`
    *   The `map` (target word frequencies) stores `M` words, each of length `K`. So, it requires `O(M * K)` space.
    *   The `map2` (current window word frequencies) inside the `helper` function also stores up to `M` words of length `K`, requiring `O(M * K)` space.
    *   The `ans` list could store up to `N` indices in the worst case, but the dominant factor for string storage is `M * K`.
    *   The temporary substrings created by `s.substring()` contribute to space, but they are transient within loop iterations.
    *   Overall, the space complexity is dominated by the hash maps, leading to `O(M * K)`.

## Java Solution

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    // This 'window' variable is static in the provided solution, which might lead to issues
    // if 'findSubstring' is called multiple times without resetting or if executed in a multi-threaded environment.
    // For a typical LeetCode single-run submission, it works.
    // A better practice would be to pass it as an argument or make it a local variable in findSubstring.
    static int window; 
    
    private static boolean helper(String s, Map<String, Integer> map, int n) {
        Map<String, Integer> map2 = new HashMap<>();
        // Iterate through the candidate substring 's' (which is of length 'window')
        // in chunks of size 'n' (word length).
        for (int i = 0; i < window; i += n) {
            String t = s.substring(i, i + n); // Extract a word-sized chunk
            map2.put(t, map2.getOrDefault(t, 0) + 1); // Count its frequency
        }
        // Compare the frequencies in the current window with the target frequencies
        return map.equals(map2);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        // Build the target frequency map for all words
        Map<String, Integer> map = new HashMap<>();
        for (String t : words) {
            map.put(t, map.getOrDefault(t, 0) + 1);
        }

        int n = words[0].length(); // Length of a single word
        window = n * words.length; // Total length of all words concatenated
        List<Integer> ans = new ArrayList<>();

        // Iterate through the main string 's'
        // 'i' represents the starting index of a potential concatenated substring
        for (int i = 0; i <= s.length() - window; i++) {
            // Extract the candidate substring of length 'window'
            // and check if it's a valid concatenation using the helper function
            if (helper(s.substring(i, i + window), map, n)) {
                ans.add(i); // If valid, add its starting index to the result
            }
        }
        return ans;
    }
}
```