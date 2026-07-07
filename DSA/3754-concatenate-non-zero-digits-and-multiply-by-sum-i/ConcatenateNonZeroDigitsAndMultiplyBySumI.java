class Solution {
    public long sumAndMultiply(int n) {
        if(n == 0) return (long) 0;
        String w = String.valueOf(n).replace("0", "");
        long num = Long.parseLong(w);
        long sum = 0;
        long ans = num;
        while(num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return ans * sum;
    }
}
