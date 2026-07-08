class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int MOD = 1000000007;
        int n = s.length();
        int[] cnt = new int[n+1];          
        long[] sum = new long[n+1];        
        long[] val = new long[n+1];       
        long[] pow10 = new long[n+1];      

        pow10[0] = 1;
        for (int i = 1; i <= n; i++) pow10[i] = pow10[i-1] * 10 % MOD;

        for (int i = 1; i <= n; i++) {
            int d = s.charAt(i-1) - '0';

            cnt[i] = cnt[i-1];
            sum[i] = sum[i-1];
            val[i] = val[i-1];

            if (d != 0) {
                cnt[i] = cnt[i-1] + 1;
                sum[i] = sum[i-1] + d;
                val[i] = (val[i-1] * 10 + d) % MOD;
            }
        }

        int q = queries.length;
        int[] ans = new int[q];

        for (int i = 0; i < q; i++) {

            int l = queries[i][0] + 1;
            int r = queries[i][1] + 1;

            int len = cnt[r] - cnt[l-1];  
            long digSum = (sum[r] - sum[l-1] + MOD) % MOD;

            if (len == 0) {     
                ans[i] = 0;
                continue;
            }

            long rightVal = val[r];
            long leftVal = val[l-1] * pow10[len] % MOD;

            long x = (rightVal - leftVal + MOD) % MOD;

            long result = x * digSum % MOD;

            ans[i] = (int) result;
        }

        return ans;
    }
}
