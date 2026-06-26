class Solution {
    static List<List<Integer>> ans;

    public static void helper(int[] c, int i, int s, List<Integer> l, int t) {
        if (s == t) {
            ans.add(new ArrayList<>(l));
            return;
        }
        if (s > t || i >= c.length)
            return;

        l.add(c[i]);
        helper(c, i, s + c[i], l, t);
        l.remove(l.size() - 1);
        helper(c, i + 1, s, l, t);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        ans = new ArrayList<>();
        helper(candidates, 0, 0, new ArrayList<>(), target);
        return ans;
    }
}
