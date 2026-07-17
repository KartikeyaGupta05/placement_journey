class Solution {
    public String convert(String s, int numRows) {

        if (numRows == 1 || s.length() <= numRows)
            return s;

        List<StringBuilder> l = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            l.add(new StringBuilder());
        }

        int c = 0;
        boolean down = true;

        for (int i = 0; i < s.length(); i++) {
            l.get(c).append(s.charAt(i));

            if (c == 0)
                down = true;
            else if (c == numRows - 1)
                down = false;

            c += down ? 1 : -1;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder row : l) {
            ans.append(row);
        }

        return ans.toString();
    }
}
