class Solution {
    static int window;
    private static boolean helper(String s, Map<String, Integer> map, int n) {
        Map<String, Integer> map2 = new HashMap<>();
        for (int i = 0; i < window; i+=n) {
            String t = s.substring(i, i + n);
            map2.put(t, map2.getOrDefault(t, 0) + 1);
        }
        return map.equals(map2);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String t : words) {
            map.put(t, map.getOrDefault(t, 0) + 1);
        }

        int n = words[0].length();
        window = n * words.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < s.length() - window + 1; i++) {
            if (helper(s.substring(i, i + window), map, n)) {
                ans.add(i);
            }
        }
        return ans;
    }
}
