# Count Subarrays With Majority Element II

[Link to LeetCode Problem](https://leetcode.com/problems/count-subarrays-with-majority-element-ii/)

🔴 Hard

**Topics**: Array, Hash Table, Divide and Conquer, Segment Tree, Merge Sort, Prefix Sum, Binary Indexed Tree (Fenwick Tree)

## Problem Summary

Given an integer array `nums` and an integer `target`, the goal is to find the number of subarrays where `target` is the majority element. A majority element is defined as an element that appears strictly more than half the times within that subarray.

## Approach

The core idea is to transform the problem into a prefix sum problem. For any given subarray, if `target` is the majority element, it means its count (`count_target`) must be strictly greater than the count of all other elements (`count_other`). That is, `count_target > count_other`.

We can convert the `nums` array into a new array where:
- Each occurrence of `target` is replaced by `+1`.
- Every other element (`num != target`) is replaced by `-1`.

Let's call this new array `mapped_nums`. For any subarray in `mapped_nums`, if the sum of its elements is `S`, then `S = count_target - count_other`.
The condition `count_target > count_other` is equivalent to `count_target - count_other > 0`, which means `S > 0`.

So, the problem boils down to finding the number of subarrays in `mapped_nums` whose sum is strictly positive.

This can be solved efficiently using prefix sums and a data structure. We maintain a running `balance` (which is the current prefix sum of `mapped_nums`). For each current `balance` at index `i`, we need to count how many previous `balance` values `prev_balance` (from index `j < i`) exist such that `current_balance - prev_balance > 0`, or `current_balance > prev_balance`.

To efficiently count `prev_balance` values strictly less than `current_balance`, we can use a Binary Indexed Tree (BIT), also known as a Fenwick Tree. The `balance` can range from `-N` to `N` (where `N` is `nums.length`). To use a BIT, which typically operates on non-negative, 1-based indices, we perform a coordinate compression or simply shift the balance values. By adding `N` to each `balance`, we map the range `[-N, N]` to `[0, 2N]`.

The algorithm proceeds as follows:
1. Initialize `total_subarrays = 0` and `current_balance = 0`.
2. Create a Binary Indexed Tree (BIT) of size `2N + 1`. This BIT will store frequencies of shifted `balance` values.
3. Initialize the BIT by marking the initial `current_balance` (which is `0` before processing any elements) as seen once. So, `BIT.update(0 + N, 1)`.
4. Iterate through `num` in `nums`:
   a. Update `current_balance`: if `num == target`, `current_balance++`; otherwise, `current_balance--`.
   b. We need to find `prev_balance` values such that `prev_balance < current_balance`. In the shifted coordinate system, this means we query the BIT for the sum of frequencies up to `(current_balance + N - 1)`. This sum represents the count of all valid `prev_balance` values.
   c. Add this count to `total_subarrays`.
   d. Update the BIT to mark the current `current_balance` as seen. So, `BIT.update(current_balance + N, 1)`.
5. Return `total_subarrays`.

## Algorithm Walkthrough

Let's use `nums = [1,2,2,3]` and `target = 2`. `N = 4`.
The shifted balance range for the BIT will be `[-4, 4]` mapped to `[0, 8]` (by adding `N=4`).
BIT size = `2*N+1 = 9`.

1.  Initialize `total_subarrays = 0`, `current_balance = 0`.
2.  Initialize BIT (all zeros).
3.  **Initial state**: `current_balance = 0`. Shifted index: `0 + 4 = 4`.
    `BIT.update(4, 1)`. (BIT now indicates `balance=0` has appeared once, representing the empty prefix).

4.  **Process `nums[0] = 1`**:
    *   `1 != target (2)`. `current_balance = 0 - 1 = -1`.
    *   Shifted index for current balance: `-1 + 4 = 3`.
    *   Query BIT for counts less than `current_balance`: `BIT.query(current_balance + N - 1)` = `BIT.query(3 - 1)` = `BIT.query(2)`.
        *   The BIT currently only has a count at index 4. `BIT.query(2)` returns 0.
    *   `total_subarrays += 0`. (`total_subarrays = 0`).
    *   Update BIT with current balance: `BIT.update(3, 1)`. (BIT now has counts for `balance=0` at index 4, and `balance=-1` at index 3).

5.  **Process `nums[1] = 2`**:
    *   `2 == target (2)`. `current_balance = -1 + 1 = 0`.
    *   Shifted index for current balance: `0 + 4 = 4`.
    *   Query BIT for counts less than `current_balance`: `BIT.query(4 - 1)` = `BIT.query(3)`.
        *   BIT has count at index 3 (for `balance=-1`). `BIT.query(3)` returns 1.
    *   `total_subarrays += 1`. (`total_subarrays = 1`).
    *   Update BIT with current balance: `BIT.update(4, 1)`. (BIT now has counts for `balance=0` twice at index 4, and `balance=-1` once at index 3).

6.  **Process `nums[2] = 2`**:
    *   `2 == target (2)`. `current_balance = 0 + 1 = 1`.
    *   Shifted index for current balance: `1 + 4 = 5`.
    *   Query BIT for counts less than `current_balance`: `BIT.query(5 - 1)` = `BIT.query(4)`.
        *   BIT has counts at index 3 (once) and index 4 (twice). `BIT.query(4)` sums these: `1 + 2 = 3`.
    *   `total_subarrays += 3`. (`total_subarrays = 1 + 3 = 4`).
    *   Update BIT with current balance: `BIT.update(5, 1)`. (BIT now has counts: index 3 (1), index 4 (2), index 5 (1)).

7.  **Process `nums[3] = 3`**:
    *   `3 != target (2)`. `current_balance = 1 - 1 = 0`.
    *   Shifted index for current balance: `0 + 4 = 4`.
    *   Query BIT for counts less than `current_balance`: `BIT.query(4 - 1)` = `BIT.query(3)`.
        *   BIT has count at index 3 (once). `BIT.query(3)` returns 1.
    *   `total_subarrays += 1`. (`total_subarrays = 4 + 1 = 5`).
    *   Update BIT with current balance: `BIT.update(4, 1)`. (BIT now has counts: index 3 (1), index 4 (3), index 5 (1)).

8.  End of array. Return `total_subarrays = 5`.

This matches Example 1's output.

## Complexity Analysis

Let `N` be the length of `nums`.

### Time Complexity

- Transforming the array implicitly and iterating through `nums` takes `O(N)` time.
- Inside the loop, we perform one `BIT.query` operation and one `BIT.update` operation. Both operations take `O(log K)` time, where `K` is the size of the BIT.
- The BIT size is `2N + 1`. So, each BIT operation is `O(log(2N+1)) = O(log N)`.
- Since there are `N` iterations, the total time complexity is `O(N log N)`.

### Space Complexity

- The Binary Indexed Tree requires `O(K)` space, where `K` is its size.
- The BIT's size is `2N + 1`.
- Therefore, the space complexity is `O(N)`.