class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> res = new ArrayList<>();
        int st = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int cst = intervals[i][0];
            int cend = intervals[i][1];

            if (cst <= end) {
                end = Math.max(end, cend);
            } else {
                res.add(new int[] { st, end });
                st = cst;
                end = cend;
            }
        }
        res.add(new int[] { st, end });

        return res.toArray(new int[res.size()][]);
    }
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length, m = 2;
        int[][] arr = new int[n + 1][m + 1];
        for(int i = 0; i < n; i++) 
            for(int j = 0; j < m; j++) 
                arr[i][j] = intervals[i][j];
            
        arr[n][0] = newInterval[0];
        arr[n][1] = newInterval[1];
        return merge(arr);
    }
}
