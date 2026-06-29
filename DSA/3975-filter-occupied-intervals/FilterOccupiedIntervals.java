class Solution {
    public List<List<Integer>> merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<List<Integer>> res = new ArrayList<>();
        int st = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int cst = intervals[i][0];
            int cend = intervals[i][1];

            if (cst <= end || cst == end + 1) {
                end = Math.max(end, cend);
            } else {
                res.add(new ArrayList<>(List.of(st, end)));
                st = cst;
                end = cend;
            }
        }
        res.add(new ArrayList<>(List.of(st, end)));

        return res;
    }

    public List<List<Integer>> filterOccupiedIntervals(int[][] occupiedIntervals,
            int freeStart,
            int freeEnd) {

        List<List<Integer>> merged = merge(occupiedIntervals);
        List<List<Integer>> ans = new ArrayList<>();

        for (List<Integer> l : merged) {
            int s = l.get(0);
            int e = l.get(1);

            if (e < freeStart || s > freeEnd) {
                ans.add(new ArrayList<>(List.of(s, e)));
            } else if (freeStart <= s && freeEnd >= e) {
                continue;
            } else if (s < freeStart && e > freeEnd) {
                ans.add(new ArrayList<>(List.of(s, freeStart - 1)));
                ans.add(new ArrayList<>(List.of(freeEnd + 1, e)));
            } else if (s < freeStart) {
                ans.add(new ArrayList<>(List.of(s, freeStart - 1)));
            } else {
                ans.add(new ArrayList<>(List.of(freeEnd + 1, e)));
            }
        }

        return ans;
    }
}
