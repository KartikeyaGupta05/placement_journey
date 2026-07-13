# Binary Tree Level Order Traversal II
*   **Link**: [https://leetcode.com/problems/binary-tree-level-order-traversal-ii/](https://leetcode.com/problems/binary-tree-level-order-traversal-ii/)
*   **Difficulty**: 🟡 Medium
*   **Topics**: Tree, Breadth-First Search, Binary Tree

## Problem Summary
This problem asks us to perform a level-order traversal of a binary tree, but with a twist: the output should be in bottom-up order. This means the list of nodes from the deepest level (leaves) should appear first, followed by the next level up, and so on, until the root level appears last.

## Approach
The core idea is to leverage a standard Breadth-First Search (BFS) for level-order traversal. BFS naturally processes nodes level by level from top to bottom. Once we have all levels stored in a list in their natural top-down order, we can simply reverse this list to achieve the required bottom-up order.

## Algorithm Walkthrough

1.  **Initialization**:
    *   Create an empty list of lists, `result`, to store the node values for each level.
    *   If the `root` is `null`, return `result` immediately.
    *   Create a `Queue` (e.g., `LinkedList`) and add the `root` node to it.

2.  **BFS Traversal**:
    *   While the `queue` is not empty:
        *   Get the current `levelSize` (number of nodes at the current level) by checking `queue.size()`.
        *   Create a new empty list, `currentLevelNodes`, to store the values of nodes at the current level.
        *   Iterate `levelSize` times:
            *   Dequeue a node from the `queue`.
            *   Add the node's value to `currentLevelNodes`.
            *   If the dequeued node has a left child, enqueue it.
            *   If the dequeued node has a right child, enqueue it.
        *   After processing all nodes for the current level, add `currentLevelNodes` to the `result` list.

3.  **Reverse Result**:
    *   Once the BFS completes and all levels have been added to `result` (in top-down order), use `Collections.reverse(result)` to reverse the order of the lists within `result`.

4.  **Return**:
    *   Return the `result` list.

### Example Walkthrough: `root = [3,9,20,null,null,15,7]`

**Initial State**:
*   `result = []`
*   `queue = [3]`

**Iteration 1 (Level 0)**:
*   `levelSize = 1`
*   `currentLevelNodes = []`
*   Dequeue `3`. Add `3` to `currentLevelNodes`. `currentLevelNodes = [3]`.
*   Enqueue `9` (left child of 3). Enqueue `20` (right child of 3).
*   Add `currentLevelNodes` to `result`. `result = [[3]]`.
*   `queue = [9, 20]`

**Iteration 2 (Level 1)**:
*   `levelSize = 2`
*   `currentLevelNodes = []`
*   Dequeue `9`. Add `9` to `currentLevelNodes`. `currentLevelNodes = [9]`. (No children for 9)
*   Dequeue `20`. Add `20` to `currentLevelNodes`. `currentLevelNodes = [9, 20]`.
*   Enqueue `15` (left child of 20). Enqueue `7` (right child of 20).
*   Add `currentLevelNodes` to `result`. `result = [[3], [9, 20]]`.
*   `queue = [15, 7]`

**Iteration 3 (Level 2)**:
*   `levelSize = 2`
*   `currentLevelNodes = []`
*   Dequeue `15`. Add `15` to `currentLevelNodes`. `currentLevelNodes = [15]`. (No children for 15)
*   Dequeue `7`. Add `7` to `currentLevelNodes`. `currentLevelNodes = [15, 7]`. (No children for 7)
*   Add `currentLevelNodes` to `result`. `result = [[3], [9, 20], [15, 7]]`.
*   `queue = []`

**BFS Complete.**

**Reverse Result**:
*   `Collections.reverse(result)` is called on `[[3], [9, 20], [15, 7]]`.
*   `result` becomes `[[15, 7], [9, 20], [3]]`.

**Return**:
*   `[[15, 7], [9, 20], [3]]`

## Complexity Analysis

### Time Complexity
*   **O(N)**, where N is the number of nodes in the binary tree.
*   Each node is visited exactly once: it's added to the queue, processed (its value is added to `currentLevelNodes`), and its children are added to the queue. The final `Collections.reverse()` operation also takes O(L) time where L is the number of levels, which is at most O(N).

### Space Complexity
*   **O(N)**, where N is the number of nodes in the binary tree.
*   **`result` list**: In the worst case (e.g., a skewed tree or a complete binary tree), the `result` list will store all N node values, contributing O(N) space.
*   **`queue`**: In the worst case (a complete binary tree), the queue can hold approximately N/2 nodes (all nodes at the deepest level), contributing O(N) space.
*   The overall space complexity is dominated by these factors, resulting in O(N).