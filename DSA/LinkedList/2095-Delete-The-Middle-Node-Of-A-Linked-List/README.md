# 2095. Delete the Middle Node of a Linked List

**Difficulty:** Medium | **Topic:** LinkedList | **Tags:** Linked List, Two Pointers

---

## Problem

You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.

The middle node of a linked list of size `n` is the `⌊n / 2⌋`th node from the start using 0-based indexing, where `⌊x⌋` denotes the largest integer less than or equal to `x`.

For example, for `n = 1, 2, 3, 4,` and `5`, the middle nodes are at 0-based indices `0, 1, 1, 2,` and `2`, respectively.

**Constraints:**
*   The number of nodes in the list is in the range `[1, 10^5]`.
*   `1 <= Node.val <= 10^5`

## Examples

**Example 1:**
```
Input: head = [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
Explanation: For n = 7, node 3 with value 7 is the middle node (0-indexed). After removal, the list is [1,3,4,1,2,6].
```

**Example 2:**
```
Input: head = [1,2,3,4]
Output: [1,2,4]
Explanation: For n = 4, node 2 with value 3 is the middle node (0-indexed). After removal, the list is [1,2,4].
```

**Example 3:**
```
Input: head = [2,1]
Output: [2]
Explanation: For n = 2, node 1 with value 1 is the middle node (0-indexed). After removal, the list is [2].
```

## Approach

The problem asks us to delete the middle node of a linked list. The definition of the middle node is crucial: it's the `⌊n / 2⌋`th node (0-indexed). To delete a node in a singly linked list, we need access to the node *preceding* the one we want to delete. This suggests that simply finding the middle node isn't enough; we need to keep track of its predecessor.

A classic technique for finding the middle of a linked list, or a node relative to the end, is the **two-pointer approach** (also known as the `fast` and `slow` pointer method).

**Intuition:**
If we have a `slow` pointer that moves one step at a time, and a `fast` pointer that moves two steps at a time, when the `fast` pointer reaches the end of the list, the `slow` pointer will be exactly at the middle (or the node just before the middle, depending on the exact termination condition and middle definition).
To delete the node pointed to by `slow`, we need a pointer that trails `slow` by one step. Let's call this `node_before_middle`. When `slow` reaches the middle, `node_before_middle` will be pointing to the node right before it.

**Algorithm Steps:**

1.  **Handle Edge Cases:**
    *   If the list is empty (`head == null`) or has only one node (`head.next == null`), the middle node is effectively the head itself (index 0). Deleting it means the list becomes empty. In this scenario, return `null`.

2.  **Initialize Pointers:**
    *   Initialize `slow` pointer to `head`.
    *   Initialize `fast` pointer to `head`.
    *   Initialize `node_before_middle` pointer to `head`. This pointer will track the node just before `slow`.

3.  **Traverse with Two Pointers:**
    *   Iterate while `fast` is not `null` AND `fast.next` is not `null`. This condition ensures `fast` always has at least one node ahead, allowing `fast.next.next` to be accessed safely.
    *   Inside the loop:
        *   Update `node_before_middle` to point to the current `slow` node.
        *   Move `slow` one step forward: `slow = slow.next`.
        *   Move `fast` two steps forward: `fast = fast.next.next`.

4.  **Identify Middle and Predecessor:**
    *   When the loop terminates, `slow` will be pointing to the middle node according to the problem's 0-indexed `⌊n / 2⌋` definition.
    *   `node_before_middle` will be pointing to the node immediately preceding `slow`.

5.  **Perform Deletion:**
    *   To delete the `slow` node, update the `next` pointer of `node_before_middle` to bypass `slow`. That is, set `node_before_middle.next = slow.next`. This effectively removes `slow` from the list.

6.  **Return Head:**
    *   Return the original `head` of the list.

## Formula

The problem defines the middle node of a linked list of size `n` as the `⌊n / 2⌋`th node from the start using 0-based indexing.
This can be expressed as:

```
middle_node_index = floor(n / 2)
```

Where `floor(x)` denotes the largest integer less than or equal to `x`.

## Dry Run

Let's walk through Example 1: `head = [1,3,4,7,1,2,6]`

*   **Initial List:** `1 -> 3 -> 4 -> 7 -> 1 -> 2 -> 6`
*   **Total nodes (n):** 7
*   **Middle node index:** `⌊7 / 2⌋ = 3` (0-indexed). This is the node with value `7`.

**Step-by-step Execution:**

1.  **Edge Case Check:** `head` (node `1`) is not `null`, `head.next` (node `3`) is not `null`. Proceed.

2.  **Initialization:**
    *   `slow` points to node `1`
    *   `fast` points to node `1`
    *   `node_before_middle` points to node `1`

3.  **Loop Iteration:** `while (fast != null && fast.next != null)`

    *   **Iteration 1:**
        *   Condition: `fast` (node `1`) is not `null`, `fast.next` (node `3`) is not `null`. **True**.
        *   `node_before_middle` now points to `slow` (node `1`).
        *   `slow` moves to `slow.next` (node `3`).
        *   `fast` moves to `fast.next.next` (node `4`).
        *   **State:** `slow = 3`, `fast = 4`, `node_before_middle = 1`
            *   List visualization: `1 (node_before_middle) -> 3 (slow) -> 4 (fast) -> 7 -> 1 -> 2 -> 6`

    *   **Iteration 2:**
        *   Condition: `fast` (node `4`) is not `null`, `fast.next` (node `7`) is not `null`. **True**.
        *   `node_before_middle` now points to `slow` (node `3`).
        *   `slow` moves to `slow.next` (node `4`).
        *   `fast` moves to `fast.next.next` (node `1`, the one after `7`).
        *   **State:** `slow = 4`, `fast = 1` (value `1` at index 4), `node_before_middle = 3`
            *   List visualization: `1 -> 3 (node_before_middle) -> 4 (slow) -> 7 -> 1 (fast) -> 2 -> 6`

    *   **Iteration 3:**
        *   Condition: `fast` (node `1`) is not `null`, `fast.next` (node `2`) is not `null`. **True**.
        *   `node_before_middle` now points to `slow` (node `4`).
        *   `slow` moves to `slow.next` (node `7`).
        *   `fast` moves to `fast.next.next` (node `6`).
        *   **State:** `slow = 7`, `fast = 6`, `node_before_middle = 4`
            *   List visualization: `1 -> 3 -> 4 (node_before_middle) -> 7 (slow) -> 1 -> 2 -> 6 (fast)`

    *   **Iteration 4:**
        *   Condition: `fast` (node `6`) is not `null`, but `fast.next` (which is `null`) *is* `null`. **False**.
        *   Loop terminates.

4.  **Identify Middle and Predecessor:**
    *   At loop termination:
        *   `slow` points to node `7` (this is the middle node, index 3).
        *   `node_before_middle` points to node `4` (this is the node immediately before the middle node).

5.  **Perform Deletion:**
    *   Set `node_before_middle.next = slow.next`.
    *   Node `4`'s `next` pointer, which was pointing to `7`, is now updated to point to `7.next` (which is node `1`).
    *   The link `4 -> 7` is broken, and a new link `4 -> 1` is established.

6.  **Return Head:**
    *   Return `head` (node `1`).

**Final List:** `1 -> 3 -> 4 -> 1 -> 2 -> 6`

This matches the expected output `[1,3,4,1,2,6]`.

## Time Complexity

The time complexity is **O(N)**, where `N` is the number of nodes in the linked list.
This is because both the `slow` and `fast` pointers traverse the list. The `fast` pointer covers approximately `N` nodes, and the `slow` pointer covers approximately `N/2` nodes. Since we effectively iterate through the list once (the total number of steps taken by pointers is proportional to `N`), the overall time complexity is linear.

## Space Complexity

The space complexity is **O(1)**.
We only use a fixed number of extra pointers (`slow`, `fast`, `node_before_middle`) regardless of the size of the input linked list. No additional data structures are used that grow with `N`.