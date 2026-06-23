# 2130. Maximum Twin Sum of a Linked List

**Difficulty:** Medium | **Topic:** TwoPointers | **Tags:** Linked List, Two Pointers, Stack

---

## Problem

In a linked list of size `n`, where `n` is an even integer, the `i`th node (0-indexed) of the linked list is known as the twin of the `(n-1-i)`th node, provided that `0 <= i <= (n / 2) - 1`.

For example, if `n = 4`, then node 0 is the twin of node 3, and node 1 is the twin of node 2. These are the only nodes with twins for `n = 4`.

The twin sum is defined as the sum of a node and its twin.

Given the head of a linked list with an even length, return the maximum twin sum of the linked list.

**Constraints:**
*   The number of nodes in the list is an even integer in the range `[2, 10^5]`.
*   `1 <= Node.val <= 10^5`

## Examples

**Example 1:**
```
Input: head = [5,4,2,1]
Output: 6
Explanation:
Nodes 0 and 1 are the twins of nodes 3 and 2, respectively. All have twin sum = 6.
There are no other nodes with twins in the linked list.
Thus, the maximum twin sum of the linked list is 6. 
```

**Example 2:**
```
Input: head = [4,2,2,3]
Output: 7
Explanation:
The nodes with twins present in this linked list are:
- Node 0 is the twin of node 3 having a twin sum of 4 + 3 = 7.
- Node 1 is the twin of node 2 having a twin sum of 2 + 2 = 4.
Thus, the maximum twin sum of the linked list is max(7, 4) = 7. 
```

**Example 3:**
```
Input: head = [1,100000]
Output: 100001
Explanation:
There is only one node with a twin in the linked list having twin sum of 1 + 100000 = 100001.
```

## Approach

The problem asks us to find the maximum sum of twin nodes. Twin nodes are defined such that the `i`th node's twin is the `(n-1-i)`th node. This essentially means we need to pair the `i`th node from the beginning with the `i`th node from the end. Since `n` is always even, the list can be perfectly split into two halves of equal size `n/2`. The first node of the first half is the twin of the last node of the second half, the second node of the first half is the twin of the second-to-last node of the second half, and so on.

This observation suggests an approach where we either need to access the elements of the first half and the second half simultaneously, or somehow reverse one of the halves to facilitate direct pairing.

**Algorithm:**

1.  **Find the Middle of the Linked List**: Use the classic slow and fast pointer technique.
    *   Initialize `slow` and `fast` pointers to the `head`.
    *   Move `slow` one step at a time and `fast` two steps at a time.
    *   When `fast` reaches the end of the list (or `fast.next` is null), `slow` will be at the end of the first half of the linked list (i.e., the `(n/2 - 1)`-th node).
    *   Store the node that `slow.next` points to as the `second_half_start`. This marks the beginning of the second half of the original list.
    *   To effectively separate the two halves, set `slow.next = null`. This detaches the first half from the second.

2.  **Reverse the Second Half**:
    *   Starting from `second_half_start`, reverse the entire second half of the linked list.
    *   Standard linked list reversal: iterate through the nodes, changing `next` pointers. Keep track of `prev`, `current`, and `next_node`.
    *   The new head of the reversed second half will be our `reversed_second_head`.

3.  **Calculate Twin Sums and Find Maximum**:
    *   Initialize `max_twin_sum` to 0.
    *   Use two pointers: `p1` starting at the original `head` (the first half) and `p2` starting at `reversed_second_head`.
    *   Iterate while `p2` is not null (since both halves have `n/2` nodes, `p1` will also reach null simultaneously or just after `p2` if `n` is large, but `p2` being null is a safer termination condition).
        *   Calculate the current twin sum: `current_sum = p1.value + p2.value`.
        *   Update `max_twin_sum = max(max_twin_sum, current_sum)`.
        *   Move `p1` to `p1.next` and `p2` to `p2.next`.

4.  **Return `max_twin_sum`**.

## Formula

Not Applicable. This problem does not rely on a specific mathematical formula.

## Dry Run

Let's walk through Example 2: `head = [4,2,2,3]`

1.  **Input:** `head -> 4 -> 2 -> 2 -> 3 -> null`

2.  **Find the Middle:**
    *   Initialize `slow = 4`, `fast = 4`. `max_twin_sum = 0`.
    *   **Iteration 1:** `slow` moves to `2`, `fast` moves to `2` (original index 2).
        *   List: `4 -> (slow)2 -> 2 -> (fast)3 -> null`
    *   **Iteration 2:** `slow` moves to `2` (original index 1), `fast` moves to `null`.
        *   List: `4 -> 2(slow) -> 2 -> 3(fast is null after 3)`
        *   `fast` is now `null`, so `slow` (at value 2, original index 1) is the end of the first half.
    *   `second_half_start = slow.next` (the node with value 2, original index 2).
    *   Detach halves: `slow.next = null`.
        *   First Half: `head -> 4 -> 2 -> null`
        *   Second Half (original): `2 -> 3 -> null` (pointed to by `second_half_start`)

3.  **Reverse the Second Half:**
    *   Original second half: `2 -> 3 -> null`
    *   Let's reverse it:
        *   `prev = null`, `current = 2`
        *   `current = 2`: `next_node = 3`. `2.next = null`. `prev = 2`. `current = 3`.
        *   `current = 3`: `next_node = null`. `3.next = 2`. `prev = 3`. `current = null`.
    *   `reversed_second_head = 3` (the `prev` pointer after the loop finishes).
        *   Reversed Second Half: `reversed_second_head -> 3 -> 2 -> null`

4.  **Calculate Twin Sums and Find Maximum:**
    *   `p1 = head` (node `4`)
    *   `p2 = reversed_second_head` (node `3`)
    *   `max_twin_sum = 0`

    *   **Iteration 1 (p2 not null):**
        *   `p1` points to `4`, `p2` points to `3`.
        *   `current_sum = p1.val + p2.val = 4 + 3 = 7`.
        *   `max_twin_sum = max(0, 7) = 7`.
        *   Move `p1` to `p1.next` (node `2`).
        *   Move `p2` to `p2.next` (node `2`).

    *   **Iteration 2 (p2 not null):**
        *   `p1` points to `2`, `p2` points to `2`.
        *   `current_sum = p1.val + p2.val = 2 + 2 = 4`.
        *   `max_twin_sum = max(7, 4) = 7`.
        *   Move `p1` to `p1.next` (null).
        *   Move `p2` to `p2.next` (null).

    *   **Iteration 3 (p2 is null):** Loop terminates.

5.  **Return `max_twin_sum = 7`**.

## Time Complexity

The time complexity is **O(N)**.
*   Finding the middle of the linked list takes O(N/2) time, which simplifies to O(N).
*   Reversing the second half takes O(N/2) time, which simplifies to O(N).
*   Iterating through both halves to calculate twin sums takes O(N/2) time, which simplifies to O(N).
Overall, these steps are sequential and proportional to the number of nodes `N`.

## Space Complexity

The space complexity is **O(1)**.
*   We only use a few pointer variables (`slow`, `fast`, `prev`, `current`, `next_node`, `p1`, `p2`) which require constant extra space. We modify the linked list in-place (reversing the second half), but do not use any auxiliary data structures that grow with N (like a stack or array).

---