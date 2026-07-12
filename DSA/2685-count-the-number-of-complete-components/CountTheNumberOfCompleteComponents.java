class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for(int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        boolean[] vis = new boolean[n];
        int count = 0;

        for(int i = 0; i < n; i++) {
            if(!vis[i]) {
                List<Integer> component = new ArrayList<>();
                Queue<Integer> q = new LinkedList<>();
                q.add(i);
                vis[i] = true;

                while(!q.isEmpty()) {
                    int node = q.poll();
                    component.add(node);
                    for(int neighbor : adj.get(node)) {
                        if(!vis[neighbor]) {
                            vis[neighbor] = true;
                            q.add(neighbor);
                        }
                    }
                }

                int v = component.size();
                int totalEdges = 0;

                for(int node : component) {
                    totalEdges += adj.get(node).size();
                }

                totalEdges /= 2;

                if(totalEdges == (v * (v - 1)) / 2) {
                    count++;
                }
            }
        }
        return count;
    }
}
