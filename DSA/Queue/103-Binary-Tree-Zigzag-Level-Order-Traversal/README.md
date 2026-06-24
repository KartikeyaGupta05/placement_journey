# 103. Binary Tree Zigzag Level Order Traversal

**Difficulty:** Medium | **Topic:** Queue | **Tags:** Tree, Breadth-First Search, Binary Tree

---

## Problem

Given the `root` of a binary tree, return the zigzag level order traversal of its nodes' values. That is, the values should be returned from left to right for the first level, then right to left for the second level, left to right for the third, and so on, alternating direction for each subsequent level.

Constraints:
*   The number of nodes in the tree is in the range `[0, 2000]`.
*   `-100 <= Node.val <= 100`

## Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]
```

**Example 2:**
```
Input: root = [1]
Output: [[1]]
```

**Example 3:**
```
Input: root = []
Output: []
```

## Approach

The problem asks for a level-order traversal with an alternating direction for each level. This strongly suggests a Breadth-First Search (BFS) approach. We'll need to keep track of the current level and reverse the order of elements collected for every other level.

Here's the step-by-step algorithm:

1.  **Initialization**:
    *   Create an empty list of lists, `result`, which will store the final zigzag traversal.
    *   If the `root` is `null` (empty tree), return `result` immediately.
    *   Initialize a queue and add the `root` node to it.
    *   Initialize a boolean flag, `is_left_to_right`, to `true`. This flag will determine the traversal direction for the current level. It will flip after each level.

2.  **Level-by-Level Traversal (BFS)**:
    *   Continue as long as the queue is not empty.
    *   At the beginning of each level, get the current `level_size` (the number of nodes currently in the queue). This is crucial because it represents all nodes at the current level.
    *   Create an empty temporary list, `current_level_values`, to store the values of nodes at the current level.

3.  **Process Current Level**:
    *   Iterate `level_size` times (for each node at the current level):
        *   Dequeue a node from the front of the queue.
        *   Add the node's value to `current_level_values`.
        *   If the dequeued node has a left child, enqueue the left child.
        *   If the dequeued node has a right child, enqueue the right child.

4.  **Handle Zigzag Direction**:
    *   After processing all nodes for the current level (i.e., after the loop iterates `level_size` times):
        *   If `is_left_to_right` is `false` (meaning this is an "odd" level that should be traversed right-to-left), reverse the `current_level_values` list.
        *   Add `current_level_values` to the `result` list.
        *   Flip the `is_left_to_right` flag (`is_left_to_right = !is_left_to_right`) for the next level.

5.  **Return Result**:
    *   Once the queue becomes empty, all nodes have been processed. Return the `result` list.

This approach effectively uses BFS to process level by level and then applies a conditional reversal to achieve the zigzag pattern.

## Formula (if applicable)

N/A. This problem does not involve a central mathematical formula.

## Dry Run

Let's trace the algorithm with **Example 1:** `root = [3,9,20,null,null,15,7]`

*   Initial: `result = []`, `queue = []`, `is_left_to_right = true`
*   Add `root` to queue: `queue = [3]`

---

**Level 0 (is_left_to_right = true)**:
1.  `queue` is `[3]`. `level_size = 1`. `current_level_values = []`.
2.  Dequeue `3`.
    *   Add `3` to `current_level_values`: `[3]`
    *   Enqueue left child `9`: `queue = [9]`
    *   Enqueue right child `20`: `queue = [9, 20]`
3.  End of level. `is_left_to_right` is `true`, so `current_level_values` (`[3]`) is NOT reversed.
4.  Add `[3]` to `result`: `result = [[3]]`
5.  Flip `is_left_to_right`: `is_left_to_right = false`.

---

**Level 1 (is_left_to_right = false)**:
1.  `queue` is `[9, 20]`. `level_size = 2`. `current_level_values = []`.
2.  Dequeue `9`.
    *   Add `9` to `current_level_values`: `[9]`
    *   No children for `9` to enqueue. `queue = [20]`
3.  Dequeue `20`.
    *   Add `20` to `current_level_values`: `[9, 20]`
    *   Enqueue left child `15`: `queue = [15]`
    *   Enqueue right child `7`: `queue = [15, 7]`
4.  End of level. `is_left_to_right` is `false`. Reverse `current_level_values`.
    *   `current_level_values` becomes `[20, 9]`.
5.  Add `[20, 9]` to `result`: `result = [[3], [20, 9]]`
6.  Flip `is_left_to_right`: `is_left_to_right = true`.

---

**Level 2 (is_left_to_right = true)**:
1.  `queue` is `[15, 7]`. `level_size = 2`. `current_level_values = []`.
2.  Dequeue `15`.
    *   Add `15` to `current_level_values`: `[15]`
    *   No children for `15` to enqueue. `queue = [7]`
3.  Dequeue `7`.
    *   Add `7` to `current_level_values`: `[15, 7]`
    *   No children for `7` to enqueue. `queue = []`
4.  End of level. `is_left_to_right` is `true`, so `current_level_values` (`[15, 7]`) is NOT reversed.
5.  Add `[15, 7]` to `result`: `result = [[3], [20, 9], [15, 7]]`
6.  Flip `is_left_to_right`: `is_left_to_right = false`.

---

**Next Iteration**: `queue` is empty. The loop terminates.

**Final `result`:** `[[3],[20,9],[15,7]]`. This matches Example 1's output.

## Time Complexity

*   **O(N)**, where N is the number of nodes in the binary tree.
*   Each node is visited and processed exactly once (dequeued, its value added to a list, and its children enqueued).
*   For each level, we may perform a reversal on the list of node values for that level. The sum of lengths of all such lists across all levels is N. Thus, the total time spent on reversals is also O(N).

## Space Complexity

*   **O(N)**, where N is the number of nodes in the binary tree.
*   The `result` list stores all N node values.
*   The queue, in the worst case (e.g., a complete binary tree), can hold up to `W` nodes, where `W` is the maximum width of the tree. In the case of a complete binary tree, `W` can be approximately `N/2`. Therefore, the space used by the queue is O(N) in the worst case.