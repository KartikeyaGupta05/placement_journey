# [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

🟡 Medium

**Topics**: Array, Hash Table, Divide and Conquer, Tree, Binary Tree

## Problem Summary

Given the preorder and inorder traversal sequences of a binary tree, the task is to reconstruct and return the original binary tree. Both input arrays consist of unique values and are guaranteed to be valid traversals of the same tree.

## Approach

The strategy to reconstruct a binary tree from its preorder and inorder traversals relies on the unique properties of these traversals:

1.  **Preorder Traversal**: The first element in a preorder traversal is always the root of the current subtree (`Root -> Left -> Right`).
2.  **Inorder Traversal**: The inorder traversal provides the relative order of nodes in the left subtree, the root, and then the right subtree (`Left -> Root -> Right`).

**Algorithm Intuition:**

1.  **Identify Root**: From the `preorder` array, the first element (`preorder[0]`) is the root of the entire tree (or current subtree in a recursive call).
2.  **Split Inorder**: Find this root value in the `inorder` array. All elements to the left of the root in `inorder` belong to the left subtree, and all elements to the right belong to the right subtree.
3.  **Determine Subtree Sizes**: The number of elements in the left part of `inorder` directly tells us the size of the left subtree. Similarly for the right subtree.
4.  **Segment Preorder**: Use the `left_subtree_size` to divide the remaining `preorder` array (after the current root) into the `preorder` segments for the left and right subtrees. The elements immediately after the root in `preorder`, up to `left_subtree_size` elements, form the `preorder` for the left child. The remaining elements form the `preorder` for the right child.
5.  **Recursive Construction**: Recursively apply steps 1-4 to build the left and right children. The base case for the recursion is when a subtree's segment (defined by start and end indices) is empty, at which point `null` is returned.

**Optimization**: To efficiently find the root's index in the `inorder` array during each recursive step, we can pre-process the `inorder` array by storing its values and their corresponding indices in a `HashMap`. This allows for `O(1)` average time lookup. The recursive function should operate using start and end indices for `preorder` and `inorder` segments to avoid costly array slicing operations.

## Algorithm Walkthrough

Let's trace the algorithm with the example:
`preorder = [3,9,20,15,7]`
`inorder = [9,3,15,20,7]`

First, create a `HashMap` for `inorder` values to their indices:
`inorderMap = {9:0, 3:1, 15:2, 20:3, 7:4}`

We'll use a recursive helper function `buildTree(preStart, preEnd, inStart, inEnd)` which defines the current segments of `preorder` and `inorder` being considered.

**Initial Call**: `buildTree(0, 4, 0, 4)` (full arrays)

1.  **Root Identification**: The current root value is `preorder[preStart]` which is `preorder[0] = 3`. Create `TreeNode(3)`.

2.  **Locate Root in Inorder**: Look up `3` in `inorderMap`. `rootInInorderIndex = inorderMap.get(3) = 1`.

3.  **Calculate Left Subtree Size**: The number of elements to the left of `3` in `inorder` is `rootInInorderIndex - inStart = 1 - 0 = 1`.

4.  **Recursive Calls for Children**:

    *   **Construct Left Child**:
        *   `preorder` segment for left child: from `preStart + 1` up to `preStart + leftSubtreeSize`.
            `preStart + 1 = 0 + 1 = 1`
            `preEnd for left = preStart + leftSubtreeSize = 0 + 1 = 1`
            So, `preorder[1...1]` (value `9`).
        *   `inorder` segment for left child: from `inStart` up to `rootInInorderIndex - 1`.
            `inStart = 0`
            `inEnd for left = rootInInorderIndex - 1 = 1 - 1 = 0`
            So, `inorder[0...0]` (value `9`).
        *   Call: `root.left = buildTree(1, 1, 0, 0)`
            *   Inside this call: Root `9`. `rootInInorderIndex` for `9` is `0`. `leftSubtreeSize` is `0 - 0 = 0`.
            *   Recursive call for left child of `9`: `buildTree(1, 0, 0, -1)` (empty range, returns `null`).
            *   Recursive call for right child of `9`: `buildTree(2, 1, 1, 0)` (empty range, returns `null`).
            *   Returns `TreeNode(9)`. `TreeNode(3).left` is set to `TreeNode(9)`.

    *   **Construct Right Child**:
        *   `preorder` segment for right child: from `preStart + leftSubtreeSize + 1` up to `preEnd`.
            `preStart + leftSubtreeSize + 1 = 0 + 1 + 1 = 2`
            `preEnd for right = preEnd = 4`
            So, `preorder[2...4]` (values `[20,15,7]`).
        *   `inorder` segment for right child: from `rootInInorderIndex + 1` up to `inEnd`.
            `inStart for right = rootInInorderIndex + 1 = 1 + 1 = 2`
            `inEnd for right = inEnd = 4`
            So, `inorder[2...4]` (values `[15,20,7]`).
        *   Call: `root.right = buildTree(2, 4, 2, 4)`
            *   Inside this call: Root `20`. `rootInInorderIndex` for `20` is `3`. `leftSubtreeSize` is `3 - 2 = 1`.
            *   Recursive call for left child of `20`: `buildTree(3, 3, 2, 2)` (for value `15`). Returns `TreeNode(15)`.
            *   Recursive call for right child of `20`: `buildTree(4, 4, 4, 4)` (for value `7`). Returns `TreeNode(7)`.
            *   Returns `TreeNode(20)` with left child `15` and right child `7`. `TreeNode(3).right` is set to this subtree.

The function returns the initial `TreeNode(3)`, which is the root of the reconstructed tree.

## Complexity Analysis

### Time Complexity

The algorithm processes each node exactly once. For each node, the operations include:
*   Retrieving the root value from the `preorder` array (constant time).
*   Looking up the root's index in `inorder` using the pre-built `HashMap` (average `O(1)` time).
*   Creating a new `TreeNode` (constant time).
*   Performing arithmetic for index calculations (constant time).

Building the initial `HashMap` takes `O(N)` time, where `N` is the number of nodes in the tree. Since each node is then processed in constant time during the recursive calls, the overall time complexity is **O(N)**.

### Space Complexity

The space complexity is determined by two main factors:
1.  **Recursion Stack**: In the worst-case scenario, the tree could be skewed (e.g., a linked list), leading to a recursion depth equal to `N` (the number of nodes). This consumes `O(N)` space on the call stack.
2.  **HashMap**: Storing all `N` elements from the `inorder` array and their corresponding indices in the `HashMap` requires `O(N)` space.

Therefore, the total space complexity is **O(N)**.