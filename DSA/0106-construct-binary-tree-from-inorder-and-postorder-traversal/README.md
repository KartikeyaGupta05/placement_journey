# [Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

🟡 Medium

**Topics**: Array, Hash Table, Divide and Conquer, Tree, Binary Tree

## Problem Summary

Given the inorder and postorder traversals of a binary tree, the task is to reconstruct and return the original binary tree. Both traversals consist of unique values, and their lengths are equal.

## Approach

The core idea for reconstructing a binary tree from two traversals (one of which is inorder) is to leverage the unique properties of each traversal type to identify the root, and then recursively build the left and right subtrees.

1.  **Identify the Root**: In a postorder traversal `[Left, Right, Root]`, the very last element is always the root of the current subtree.
2.  **Locate Root in Inorder**: Once the root is identified, find its position in the inorder traversal `[Left..., Root, ...Right]`. The elements to the left of the root in `inorder` constitute the left subtree, and elements to the right constitute the right subtree.
3.  **Divide and Conquer**: The number of elements in the left subtree (from `inorder`) tells us how many elements belong to the left subtree in the `postorder` array (excluding the current root). Similarly for the right subtree. This allows us to define the `inorder` and `postorder` ranges for the left and right subtrees.
4.  **Recursion**: We then recursively call the function to build the left and right subtrees using their respective `inorder` and `postorder` subarrays/ranges.

To optimize the search for the root's index in the `inorder` array, we can pre-process the `inorder` array into a hash map, mapping each value to its index. This allows for O(1) lookup instead of O(N) for each root. We'll use index pointers to manage the sub-arrays efficiently instead of physically slicing them.

## Algorithm Walkthrough

Let's use `inorder = [9,3,15,20,7]` and `postorder = [9,15,7,20,3]`.

We'll use a helper function `build(inStart, inEnd, postStart, postEnd)` which returns the root of the subtree.

1.  **Pre-processing**: Create a map for `inorder` values to their indices:
    `map = {9:0, 3:1, 15:2, 20:3, 7:4}`

2.  **Initial Call**: `build(0, 4, 0, 4)`
    *   **Base Case**: `inStart > inEnd` or `postStart > postEnd` -> return `null`. (Not met yet)
    *   **Root**: The current root is `postorder[postEnd]`, which is `postorder[4] = 3`. Create `TreeNode root = new TreeNode(3)`.
    *   **Find Root in Inorder**: Get index of `3` from map: `inRootIndex = map.get(3) = 1`.
    *   **Calculate Subtree Sizes**:
        *   `leftSubtreeSize = inRootIndex - inStart = 1 - 0 = 1`.
    *   **Recursive Calls**:
        *   **Left Child**:
            *   `inorder` range: `inStart` to `inRootIndex - 1` (i.e., `0` to `0`)
            *   `postorder` range: `postStart` to `postStart + leftSubtreeSize - 1` (i.e., `0` to `0 + 1 - 1 = 0`)
            *   Call `root.left = build(0, 0, 0, 0)`
        *   **Right Child**:
            *   `inorder` range: `inRootIndex + 1` to `inEnd` (i.e., `2` to `4`)
            *   `postorder` range: `postStart + leftSubtreeSize` to `postEnd - 1` (i.e., `0 + 1 = 1` to `4 - 1 = 3`)
            *   Call `root.right = build(2, 4, 1, 3)`
    *   Return `root` (which is `3` at this point, with `null` children).

3.  **Recursive Call for Left Child**: `build(0, 0, 0, 0)`
    *   **Root**: `postorder[0] = 9`. Create `TreeNode leftChild = new TreeNode(9)`.
    *   **Find Root in Inorder**: `inRootIndex = map.get(9) = 0`.
    *   **Calculate Subtree Sizes**:
        *   `leftSubtreeSize = inRootIndex - inStart = 0 - 0 = 0`.
    *   **Recursive Calls**:
        *   **Left Child**: `build(0, -1, 0, -1)` -> `inStart > inEnd`, returns `null`. `leftChild.left = null`.
        *   **Right Child**: `build(1, 0, 0, -1)` -> `inStart > inEnd`, returns `null`. `leftChild.right = null`.
    *   Return `leftChild` (`9`). So, `root.left = 9`.

4.  **Recursive Call for Right Child**: `build(2, 4, 1, 3)`
    *   **Root**: `postorder[3] = 20`. Create `TreeNode rightChild = new TreeNode(20)`.
    *   **Find Root in Inorder**: `inRootIndex = map.get(20) = 3`.
    *   **Calculate Subtree Sizes**:
        *   `leftSubtreeSize = inRootIndex - inStart = 3 - 2 = 1`.
    *   **Recursive Calls**:
        *   **Left Child**:
            *   `inorder` range: `inStart` to `inRootIndex - 1` (i.e., `2` to `2`)
            *   `postorder` range: `postStart` to `postStart + leftSubtreeSize - 1` (i.e., `1` to `1 + 1 - 1 = 1`)
            *   Call `rightChild.left = build(2, 2, 1, 1)`
        *   **Right Child**:
            *   `inorder` range: `inRootIndex + 1` to `inEnd` (i.e., `4` to `4`)
            *   `postorder` range: `postStart + leftSubtreeSize` to `postEnd - 1` (i.e., `1 + 1 = 2` to `3 - 1 = 2`)
            *   Call `rightChild.right = build(4, 4, 2, 2)`
    *   Return `rightChild` (which is `20` at this point, with `null` children).

5.  **Recursive Call for `rightChild.left`**: `build(2, 2, 1, 1)`
    *   **Root**: `postorder[1] = 15`. Create `TreeNode leftGrandChild = new TreeNode(15)`.
    *   **Find Root in Inorder**: `inRootIndex = map.get(15) = 2`.
    *   **Calculate Subtree Sizes**: `leftSubtreeSize = inRootIndex - inStart = 2 - 2 = 0`.
    *   **Recursive Calls**: Left and Right calls will return `null` due to empty ranges.
    *   Return `leftGrandChild` (`15`). So, `rightChild.left = 15`.

6.  **Recursive Call for `rightChild.right`**: `build(4, 4, 2, 2)`
    *   **Root**: `postorder[2] = 7`. Create `TreeNode rightGrandChild = new TreeNode(7)`.
    *   **Find Root in Inorder**: `inRootIndex = map.get(7) = 4`.
    *   **Calculate Subtree Sizes**: `leftSubtreeSize = inRootIndex - inStart = 4 - 4 = 0`.
    *   **Recursive Calls**: Left and Right calls will return `null` due to empty ranges.
    *   Return `rightGrandChild` (`7`). So, `rightChild.right = 7`.

Combining all parts, the final tree structure is `[3,9,20,null,null,15,7]`.

## Complexity Analysis

### Time Complexity

*   **O(N)**: Building the `inorder` value-to-index map takes O(N) time, where N is the number of nodes.
*   The recursive `build` function is called for each node exactly once. In each call, operations like getting the root, looking up its index in the map, and calculating subtree sizes are all O(1).
*   Therefore, the total time complexity is dominated by iterating through the nodes and map construction, resulting in O(N).

### Space Complexity

*   **O(N)**: The hash map to store `inorder` element indices takes O(N) space.
*   The recursion stack can go as deep as the height of the tree. In the worst case (a skewed tree), the height can be N. Thus, the recursion stack space is O(N).
*   Overall, the space complexity is O(N).