# 1402. Reducing Dishes

**Difficulty:** Hard | **Topic:** Greedy | **Tags:** Array, Dynamic Programming, Greedy, Sorting

---

## Problem

A chef has collected data on the satisfaction level of his `n` dishes. The chef can cook any dish in 1 unit of time.

The like-time coefficient of a dish is defined as the time taken to cook that dish (including previous dishes) multiplied by its satisfaction level, i.e., `time[i] * satisfaction[i]`.

Return the maximum sum of like-time coefficients that the chef can obtain after preparing some amount of dishes.

Dishes can be prepared in any order, and the chef can discard some dishes to get this maximum value.

**Constraints:**
*   `n == satisfaction.length`
*   `1 <= n <= 500`
*   `-1000 <= satisfaction[i] <= 1000`

## Examples

**Example 1:**
```
Input: satisfaction = [-1,-8,0,5,-9]
Output: 14
Explanation: After removing the second and last dish, the maximum total like-time coefficient will be equal to (-1*1 + 0*2 + 5*3 = 14). Each dish is prepared in one unit of time.
```

**Example 2:**
```
Input: satisfaction = [4,3,2]
Output: 20
Explanation: Dishes can be prepared in any order, (2*1 + 3*2 + 4*3 = 20)
```

**Example 3:**
```
Input: satisfaction = [-1,-4,-5]
Output: 0
Explanation: People do not like the dishes. No dish is prepared.
```

## Approach

The problem asks us to select a subset of dishes and arrange them in an order to maximize the total like-time coefficient.

Let's break down the problem with two key observations:

1.  **Optimal Ordering for a Selected Subset:** If we decide to cook a specific set of `k` dishes with satisfaction levels `s_a, s_b, ..., s_k`, to maximize their combined like-time coefficient, we should always cook them in ascending order of their satisfaction levels. That is, the dish with the lowest satisfaction among the `k` chosen dishes gets a time multiplier of `1`, the next lowest gets `2`, and so on, until the dish with the highest satisfaction gets `k`. This ensures that dishes with higher satisfaction levels contribute more by being multiplied by larger time coefficients.

2.  **Greedy Selection of Dishes:** Given the above, the first step is to sort the entire `satisfaction` array in ascending order. Let the sorted array be `s_0, s_1, ..., s_{n-1}`. Now, we need to decide which suffix of this sorted array (i.e., `s_i, s_{i+1}, ..., s_{n-1}` for some `i`) to cook.

    Consider iterating through the sorted `satisfaction` array from right to left (from the highest satisfaction value to the lowest). We will maintain two variables:
    *   `current_cumulative_satisfaction_sum`: The sum of satisfaction levels of all dishes currently considered for cooking.
    *   `max_like_time_coefficient_sum`: The maximum total like-time coefficient found so far.

    Initially, both `current_cumulative_satisfaction_sum` and `max_like_time_coefficient_sum` are `0`.

    We iterate `i` from `n-1` down to `0`:
    *   For each `satisfaction[i]`, we provisionally add it to our set of chosen dishes.
    *   Update `current_cumulative_satisfaction_sum` by adding `satisfaction[i]`.
    *   Now, `current_cumulative_satisfaction_sum` represents the sum of satisfaction levels of all dishes in our current subset (which are `satisfaction[i], satisfaction[i+1], ..., satisfaction[n-1]`).
    *   If `current_cumulative_satisfaction_sum` is positive (or non-negative):
        This indicates that including `satisfaction[i]` and having it contribute `1 * satisfaction[i]`, while also incrementing the time coefficient of all previously chosen dishes by `1` (which collectively adds `current_cumulative_satisfaction_sum - satisfaction[i]` to `max_like_time_coefficient_sum`), results in a net positive increase to the `max_like_time_coefficient_sum`. A simpler way to understand this is that the current `current_cumulative_satisfaction_sum` is exactly the amount added to the total sum if we consider all current dishes `s_i, ..., s_{n-1}`.
        So, we update `max_like_time_coefficient_sum` by adding the `current_cumulative_satisfaction_sum`.
    *   If `current_cumulative_satisfaction_sum` becomes negative:
        This means that adding `satisfaction[i]` (and effectively shifting the time multipliers of all previously chosen dishes) would result in a *decrease* of the overall `max_like_time_coefficient_sum`. Since we are moving from higher satisfaction values to lower ones, any subsequent dish `satisfaction[k]` (where `k < i`) would be even smaller or more negative. Therefore, adding it would only make `current_cumulative_satisfaction_sum` even more negative, further reducing the total. So, we stop and the current `max_like_time_coefficient_sum` is our answer.

This greedy strategy works because by continually adding dishes as long as their collective sum of satisfactions remains non-negative, we ensure that each incremental addition to the total like-time coefficient is either positive or zero, thus maximizing the overall sum.

## Formula

The core idea relies on the observation that if we choose `M` dishes, sorted by satisfaction `s'_1 <= s'_2 <= ... <= s'_M`, their total like-time coefficient is:

`TotalCoefficient = 1*s'_1 + 2*s'_2 + ... + M*s'_M`

The greedy algorithm iteratively builds this sum. If we have a current set of `k` dishes `s'_1, ..., s'_k` and their `CurrentTotalCoefficient`, and we consider adding an `(k+1)`-th dish `s'_{k+1}` (which will be `s'_0` from the sorted original array, or `satisfaction[i]` in the loop):

`NewTotalCoefficient = CurrentTotalCoefficient + (sum of satisfaction of all k+1 chosen dishes)`

Or, in terms of the variables used in the approach:

`current_cumulative_satisfaction_sum = sum(satisfaction[j] for all chosen dishes j)`
`max_like_time_coefficient_sum = max_like_time_coefficient_sum + current_cumulative_satisfaction_sum` (only if `current_cumulative_satisfaction_sum >= 0`)

## Dry Run

Let's use `satisfaction = [-1,-8,0,5,-9]` (Example 1).

1.  **Sort the array in ascending order:**
    `satisfaction = [-9, -8, -1, 0, 5]`
    `n = 5`

2.  **Initialize variables:**
    `current_cumulative_satisfaction_sum = 0`
    `max_like_time_coefficient_sum = 0`

3.  **Iterate from `i = n-1` down to `0`:**

    *   **`i = 4` (satisfaction[4] = 5):**
        *   `current_cumulative_satisfaction_sum = 0 + 5 = 5`
        *   Is `current_cumulative_satisfaction_sum (5) >= 0`? Yes.
        *   `max_like_time_coefficient_sum = 0 + 5 = 5`
        *   *Explanation*: We cook only dish `5`. `1*5 = 5`.

    *   **`i = 3` (satisfaction[3] = 0):**
        *   `current_cumulative_satisfaction_sum = 5 + 0 = 5`
        *   Is `current_cumulative_satisfaction_sum (5) >= 0`? Yes.
        *   `max_like_time_coefficient_sum = 5 + 5 = 10`
        *   *Explanation*: We cook dishes `0, 5`. Sorted order: `0, 5`. Coefficients: `1*0 + 2*5 = 10`.

    *   **`i = 2` (satisfaction[2] = -1):**
        *   `current_cumulative_satisfaction_sum = 5 + (-1) = 4`
        *   Is `current_cumulative_satisfaction_sum (4) >= 0`? Yes.
        *   `max_like_time_coefficient_sum = 10 + 4 = 14`
        *   *Explanation*: We cook dishes `-1, 0, 5`. Sorted order: `-1, 0, 5`. Coefficients: `1*(-1) + 2*0 + 3*5 = -1 + 0 + 15 = 14`.

    *   **`i = 1` (satisfaction[1] = -8):**
        *   `current_cumulative_satisfaction_sum = 4 + (-8) = -4`
        *   Is `current_cumulative_satisfaction_sum (-4) >= 0`? No.
        *   Break the loop.

4.  **Return `max_like_time_coefficient_sum`:** The final value is `14`.

This matches Example 1's output.

## Time Complexity

*   **Sorting the array**: `O(N log N)` where `N` is the number of dishes.
*   **Iterating through the sorted array**: `O(N)`.

Therefore, the overall time complexity is **`O(N log N)`**.

## Space Complexity

*   **Sorting the array**: If an in-place sort algorithm is used, the space complexity is `O(1)` (excluding the input array). Some sorting algorithms might use `O(log N)` or `O(N)` auxiliary space depending on implementation.
*   **Variables**: A few constant extra variables (`current_cumulative_satisfaction_sum`, `max_like_time_coefficient_sum`, loop counter).

Therefore, the auxiliary space complexity is **`O(1)`** if the sorting algorithm is in-place.