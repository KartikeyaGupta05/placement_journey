class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for(int node = 2; node <= n; node++) {
            for(int r = 1; r <= node; r++) {
                dp[node] += dp[r - 1] * dp[node - r];
            }
        }
        return dp[n];
    }
}