class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> 
            a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        int ans = 0, max = 0;
        for(int i[] : intervals) {
            if(i[1] > max) {
                ans++;
                max = i[1];
            }
        }          
        return ans;
    }
}
