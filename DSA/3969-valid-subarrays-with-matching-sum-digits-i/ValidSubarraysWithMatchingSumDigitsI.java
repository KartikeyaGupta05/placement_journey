class Solution {
    public int countValidSubarrays(int[] nums, int x) {
        int count = 0;

        for (int l = 0; l < nums.length; l++) {
            long sum = 0;

            for (int r = l; r < nums.length; r++) {
                sum += nums[r];

                if (checkValid(sum, x)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean checkValid(long sum, int x) {
        long first = sum;
        while (first >= 10) {
            first /= 10;
        }

        return first == x && sum % 10 == x;
    }
}
