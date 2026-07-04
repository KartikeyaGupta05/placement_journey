# Minimum Score of a Path Between Two Cities

## 🟡 Medium

**Topics**: Depth-First Search, Breadth-First Search, Union-Find, Graph Theory

## Problem Summary

The problem asks us to find the minimum possible score of a path between city 1 and city `n`. The score of a path is defined as the minimum distance of a road encountered in that path. We are given `n` cities and a list of bidirectional roads, each with a specific distance. It's guaranteed that there's at least one path between city 1 and city `n`.

## Approach

The core insight for this problem lies in understanding the definition of a path's score and the allowance to traverse roads multiple times. Since we can revisit cities and roads, and the score is the *minimum* distance *within* any chosen path, we are essentially looking for the smallest possible edge weight `D` such that there exists a path from city 1 to city `n` where all edges in that path have distances greater than or equal to `D`.

Because we are guaranteed a path between city 1 and city `n`, these two cities must belong to the same connected component. Any road within this connected component can potentially be part of a path between 1 and `n`. If we want to achieve the "minimum possible score", it means we want to find the smallest possible `D`. This `D` will simply be the minimum distance of *any* road that belongs to the connected component containing city 1 (and thus city `n`). If there's an edge with distance `X` in this component, we can always construct a path from 1 to `n` that uses this edge, possibly multiple times, and the score for such a path would be at most `X`. Therefore, the overall minimum score will be the minimum distance among all roads that are part of the connected component containing city 1.

We can find all roads within this connected component using a standard graph traversal algorithm like Breadth-First Search (BFS) or Depth-First Search (DFS).

The algorithm proceeds as follows:

1.  **Build Adjacency List**: Represent the graph using an adjacency list. For each city, store a list of its neighbors along with the distance to that neighbor.
2.  **Initialize `min_score`**: Set `min_score` to a very large value (e.g., `Integer.MAX_VALUE`). This variable will store the minimum road distance found within the connected component.
3.  **BFS Traversal**:
    *   Start a BFS from city 1. Use a `visited` array or set to keep track of visited cities and prevent cycles/reprocessing.
    *   Add city 1 to the queue and mark it as visited.
    *   While the queue is not empty:
        *   Dequeue a city `u`.
        *   For each neighbor `v` of `u` with an associated distance `dist`:
            *   Update `min_score = Math.min(min_score, dist)`. This step is crucial as it finds the minimum road distance *among all roads in the current connected component*.
            *   If `v` has not been visited:
                *   Mark `v` as visited.
                *   Enqueue `v`.
4.  **Return `min_score`**: After the BFS completes, `min_score` will hold the minimum distance of any road in the connected component containing city 1 and `n`. This is our answer.

## Algorithm Walkthrough

Let's trace the algorithm with Example 1: `n = 4, roads = [[1,2,9],[2,3,6],[2,4,5],[1,4,7]]`

1.  **Adjacency List Construction**:
    *   City 1: `[(2, 9), (4, 7)]`
    *   City 2: `[(1, 9), (3, 6), (4, 5)]`
    *   City 3: `[(2, 6)]`
    *   City 4: `[(2, 5), (1, 7)]`
    (Note: Roads are bidirectional, so both directions are added.)

2.  **Initialization**:
    *   `min_score = Integer.MAX_VALUE`
    *   `visited = [false, false, false, false]` (for cities 1 to 4)
    *   `queue = []`

3.  **BFS Traversal**:
    *   Add city 1 to `queue`. `queue = [1]`. Mark `visited[1] = true`.

    *   **Dequeue 1**:
        *   Neighbors of 1:
            *   `(2, 9)`: `min_score = min(MAX_VALUE, 9) = 9`. City 2 is not visited. Mark `visited[2] = true`. Enqueue 2. `queue = [2]`.
            *   `(4, 7)`: `min_score = min(9, 7) = 7`. City 4 is not visited. Mark `visited[4] = true`. Enqueue 4. `queue = [2, 4]`.

    *   **Dequeue 2**:
        *   Neighbors of 2:
            *   `(1, 9)`: `min_score = min(7, 9) = 7`. City 1 is visited.
            *   `(3, 6)`: `min_score = min(7, 6) = 6`. City 3 is not visited. Mark `visited[3] = true`. Enqueue 3. `queue = [4, 3]`.
            *   `(4, 5)`: `min_score = min(6, 5) = 5`. City 4 is visited.

    *   **Dequeue 4**:
        *   Neighbors of 4:
            *   `(2, 5)`: `min_score = min(5, 5) = 5`. City 2 is visited.
            *   `(1, 7)`: `min_score = min(5, 7) = 5`. City 1 is visited.

    *   **Dequeue 3**:
        *   Neighbors of 3:
            *   `(2, 6)`: `min_score = min(5, 6) = 5`. City 2 is visited.

    *   `queue` is now empty.

4.  **Return `min_score`**: The final `min_score` is 5.

This matches the example output.

## Complexity Analysis

Let `N` be the number of cities and `M` be the number of roads.

*   **Time Complexity**:
    *   Building the adjacency list takes O(N + M) time, as we iterate through all roads and add them to the list (each road adds two entries for bidirectional connections).
    *   The BFS traversal visits each city and each road in the connected component containing city 1 at most once. In the worst case, the entire graph is a single connected component. Therefore, BFS takes O(N + M) time.
    *   Overall, the dominant factor is graph construction and BFS, resulting in **O(N + M)** time complexity.

*   **Space Complexity**:
    *   The adjacency list stores all connections, requiring O(N + M) space.
    *   The `visited` array requires O(N) space.
    *   The BFS queue, in the worst case (e.g., a star graph), can hold up to O(N) cities.
    *   Overall, the space complexity is **O(N + M)**.