class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length())
            return "";

        Map<Character, Integer> map = new HashMap<>();

        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0, count = t.length(), start = 0, minLen = Integer.MAX_VALUE;

        while (right < s.length()) {

            char ch = s.charAt(right);

            if (map.containsKey(ch)) {
                if (map.get(ch) > 0)
                    count--;

                map.put(ch, map.get(ch) - 1);
            }

            right++;

            while (count == 0) {

                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }

                char c = s.charAt(left);

                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);

                    if (map.get(c) > 0)
                        count++;
                }

                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
