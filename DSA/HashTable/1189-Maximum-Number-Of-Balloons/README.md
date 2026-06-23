# 1189. Maximum Number of Balloons

**Difficulty:** Easy | **Topic:** HashTable | **Tags:** Hash Table, String, Counting

---

## Problem

Given a string `text`, you want to use the characters of `text` to form as many instances of the word "balloon" as possible.

You can use each character in `text` at most once. Return the maximum number of instances that can be formed.

**Constraints:**
*   `1 <= text.length <= 10^4`
*   `text` consists of lower case English letters only.

## Examples

**Example 1:**

```
Input: text = "nlaebolko"
Output: 1
```

**Example 2:**

```
Input: text = "loonbalxballpoon"
Output: 2
```

**Example 3:**

```
Input: text = "leetcode"
Output: 0
```

## Approach

The core idea behind this problem is to determine a limiting factor. To form the word "balloon", we need specific counts of certain characters:
*   `b`: 1
*   `a`: 1
*   `l`: 2
*   `o`: 2
*   `n`: 1

We are given a `text` string, and we need to figure out how many times we can construct "balloon" using the characters available in `text`. Since each character from `text` can be used at most once, the number of times we can form "balloon" will be limited by the character that is least abundant relative to its requirement.

**Algorithm Steps:**

1.  **Count Character Frequencies:** First, we need to know how many of each relevant character ('b', 'a', 'l', 'o', 'n') are available in the input `text` string. A hash map (or an array of size 26 for lowercase English letters) is an efficient way to store these frequencies. Iterate through `text` once and populate this frequency map.
2.  **Calculate Potential Balloons Per Character:** For each unique character required by "balloon" ('b', 'a', 'l', 'o', 'n'), calculate how many complete "balloons" could be formed *if that character were the only limiting factor*.
    *   For 'b', 'a', 'n': Since only one of each is needed per "balloon", the number of potential balloons is simply its count in `text`.
    *   For 'l', 'o': Since two of each are needed per "balloon", the number of potential balloons is its count in `text` divided by 2 (integer division).
3.  **Find the Minimum:** The actual maximum number of "balloons" that can be formed is the minimum of all these calculated potential balloon counts. This is because we can only form as many "balloons" as the scarcest required character allows.

For example, if we have 10 'b's, 10 'a's, 1 'l', 10 'o's, and 10 'n's, we can only form 0 "balloons" because we need 2 'l's for just one "balloon", and we only have 1. The minimum of `10/1`, `10/1`, `1/2`, `10/2`, `10/1` will be `0`.

## Formula

The maximum number of "balloons" can be determined by the following formula:

```
max_balloons = min(
    count_of_b / 1,
    count_of_a / 1,
    count_of_l / 2,
    count_of_o / 2,
    count_of_n / 1
)
```

Where `count_of_x` refers to the frequency of character `x` found in the input `text` string. All divisions are integer divisions.

## Dry Run

Let's walk through **Example 2**: `text = "loonbalxballpoon"`

1.  **Count Character Frequencies:**
    Iterate through `text` to build a frequency map. We only care about 'b', 'a', 'l', 'o', 'n'.

    | Character | Count in `text` |
    | :-------- | :-------------- |
    | b         | 2               |
    | a         | 2               |
    | l         | 4               |
    | o         | 4               |
    | n         | 2               |
    | x         | 1               |
    | p         | 2               |

2.  **Calculate Potential Balloons Per Character:**

    *   For 'b': We have 2 'b's. Need 1 'b' per "balloon". `2 / 1 = 2` potential balloons.
    *   For 'a': We have 2 'a's. Need 1 'a' per "balloon". `2 / 1 = 2` potential balloons.
    *   For 'l': We have 4 'l's. Need 2 'l's per "balloon". `4 / 2 = 2` potential balloons.
    *   For 'o': We have 4 'o's. Need 2 'o's per "balloon". `4 / 2 = 2` potential balloons.
    *   For 'n': We have 2 'n's. Need 1 'n' per "balloon". `2 / 1 = 2` potential balloons.

3.  **Find the Minimum:**

    The potential balloon counts are `[2, 2, 2, 2, 2]`.
    The minimum of these values is `2`.

Therefore, the maximum number of instances of "balloon" that can be formed from "loonbalxballpoon" is **2**.

## Time Complexity

The time complexity is **O(N)**, where N is the length of the input string `text`.
*   We iterate through the input string once to count character frequencies. This takes O(N) time.
*   The subsequent calculations (dividing and finding the minimum) involve a fixed number of operations (5 characters), which is O(1).
Thus, the dominant factor is the initial scan of the string.

## Space Complexity

The space complexity is **O(1)**.
*   We use a hash map (or an array) to store character frequencies. Since the input consists only of lowercase English letters, the map will store at most 26 distinct character counts. This is a constant amount of space, regardless of the input string's length.