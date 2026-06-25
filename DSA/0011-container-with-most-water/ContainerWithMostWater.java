class Solution {
    public int maxArea(int[] height) {
        int mW = 0;
        int l = 0;
        int r = height.length - 1;
        while(l < r) {
            int ht = Math.min(height[r], height[l]);
            int w = r - l;
            int cW = ht * w;
            mW = Math.max(mW, cW);

            if(height[l] < height[r]) l++;
            else r--;
        }
        return mW;
    }
}
