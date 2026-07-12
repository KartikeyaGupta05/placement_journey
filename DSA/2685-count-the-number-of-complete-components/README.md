# [684. Count the Number of Complete Components](https://leetcode.com/problems/count-the-number-of-complete-components/)

🟡 Medium

**Topics:** Depth-First Search, Breadth-First Search, Union-Find, Graph Theory

## Problem Summary

You are given an undirected graph with `n` vertices and a list of `edges`. The goal is to determine the total number of "complete connected components" within this graph. A connected component is considered "complete" if every pair of its vertices is directly connected by an edge.

## Approach

The core idea is to identify each connected component individually and then verify if that component satisfies the condition of being "complete."

1.  **Graph Representation**: First, convert the given `edges` list into an adjacency list for easy traversal.
2.  **Iterate and Explore Components**: Iterate through each vertex from `0` to `n-1`. If a vertex has not yet been visited, it means we've found a new connected component.
3.  **DFS/BFS Traversal**: Perform a graph traversal (Depth-First Search or Breadth-First Search) starting from this unvisited vertex. During the traversal:
    *   Keep track of all vertices (`V_count`) belonging to the current connected component.
    *   Keep track of the total number of edges (`E_count`) encountered *within* this component. Since each edge `(u, v)` will be visited twice (once from `u` to `v` and once from `v` to `u`) when iterating through neighbors, we'll divide `E_count` by 2 at the end.
4.  **Check for Completeness**: Once the traversal for a component finishes (i.e., all reachable nodes are visited), we have its `V_count` (number of vertices) and `E_count` (total unique edges). A complete graph with `V_count` vertices must have exactly `V_count * (V_count - 1) / 2` edges. Compare our `E_count` with this expected value.
5.  **Count Complete Components**: If `E_count` matches the expected number of edges, increment a global counter for complete components.
6.  **Repeat**: Continue iterating through the main loop until all vertices have been visited, ensuring every connected component is processed.

## Algorithm Walkthrough

Let's use Example 2: `n = 6`, `edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]`

**1. Build Adjacency List:**
```
0: [1, 2]
1: [0, 2]
2: [0, 1]
3: [4, 5]
4: [3]
5: [3]
```
Initialize `visited = [F,F,F,F,F,F]`, `completeComponentsCount = 0`.

**2. Iterate through vertices:**

*   **`i = 0`**: `visited[0]` is `F`. Start a new DFS.
    *   `component_nodes = 0`, `component_edges_sum = 0` (will be divided by 2 later).
    *   Call `DFS(0)`:
        *   `visited[0] = T`, `component_nodes = 1`.
        *   Neighbors of 0: `1`, `2`.
            *   Process neighbor `1`: `component_edges_sum = 1`. `visited[1]` is `F`. Call `DFS(1)`.
                *   `visited[1] = T`, `component_nodes = 2`.
                *   Neighbors of 1: `0`, `2`.
                    *   Process neighbor `0`: `component_edges_sum = 2`. `visited[0]` is `T`. Skip.
                    *   Process neighbor `2`: `component_edges_sum = 3`. `visited[2]` is `F`. Call `DFS(2)`.
                        *   `visited[2] = T`, `component_nodes = 3`.
                        *   Neighbors of 2: `0`, `1`.
                            *   Process neighbor `0`: `component_edges_sum = 4`. `visited[0]` is `T`. Skip.
                            *   Process neighbor `1`: `component_edges_sum = 5`. `visited[1]` is `T`. Skip.
                        *   Return from `DFS(2)`.
                *   Return from `DFS(1)`.
            *   Process neighbor `2`: `component_edges_sum = 6`. `visited[2]` is `T`. Skip.
        *   Return from `DFS(0)`.
    *   **Component complete**: `V_count = 3`, `E_sum = 6`.
    *   Actual edges in component = `E_sum / 2 = 6 / 2 = 3`.
    *   Expected edges for 3 nodes = `3 * (3 - 1) / 2 = 3`.
    *   `3 == 3`. This component is complete! `completeComponentsCount = 1`.

*   **`i = 1`**: `visited[1]` is `T`. Skip.
*   **`i = 2`**: `visited[2]` is `T`. Skip.

*   **`i = 3`**: `visited[3]` is `F`. Start a new DFS.
    *   `component_nodes = 0`, `component_edges_sum = 0`.
    *   Call `DFS(3)`:
        *   `visited[3] = T`, `component_nodes = 1`.
        *   Neighbors of 3: `4`, `5`.
            *   Process neighbor `4`: `component_edges_sum = 1`. `visited[4]` is `F`. Call `DFS(4)`.
                *   `visited[4] = T`, `component_nodes = 2`.
                *   Neighbors of 4: `3`.
                    *   Process neighbor `3`: `component_edges_sum = 2`. `visited[3]` is `T`. Skip.
                *   Return from `DFS(4)`.
            *   Process neighbor `5`: `component_edges_sum = 3`. `visited[5]` is `F`. Call `DFS(5)`.
                *   `visited[5] = T`, `component_nodes = 3`.
                *   Neighbors of 5: `3`.
                    *   Process neighbor `3`: `component_edges_sum = 4`. `visited[3]` is `T`. Skip.
                *   Return from `DFS(5)`.
        *   Return from `DFS(3)`.
    *   **Component complete**: `V_count = 3`, `E_sum = 4`.
    *   Actual edges in component = `E_sum / 2 = 4 / 2 = 2`.
    *   Expected edges for 3 nodes = `3 * (3 - 1) / 2 = 3`.
    *   `2 != 3`. This component is NOT complete.

*   **`i = 4`**: `visited[4]` is `T`. Skip.
*   **`i = 5`**: `visited[5]` is `T`. Skip.

The loop finishes.
Final `completeComponentsCount = 1`.

## Complexity Analysis

*   **Time Complexity**: `O(N + E)`
    *   Building the adjacency list takes `O(N + E)` time, where `N` is the number of vertices and `E` is the number of edges.
    *   The main loop iterates `N` times. Each vertex and each edge (in the DFS/BFS traversal) is visited at most a constant number of times across all component explorations. Therefore, the total time for all traversals is `O(N + E)`.
    *   Overall, the dominant factor is `O(N + E)`.

*   **Space Complexity**: `O(N + E)`
    *   The adjacency list stores `N` lists and a total of `2E` elements (for an undirected graph), resulting in `O(N + E)` space.
    *   The `visited` array takes `O(N)` space.
    *   The recursion stack for DFS (or queue for BFS) can go up to `O(N)` in the worst case (e.g., a path graph or a star graph).
    *   Thus, the total space complexity is `O(N + E)`.