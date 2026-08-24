class Solution {
    public int stoneGameVIII(int[] stones) {

        int n = stones.length;

        // Step 1: Build prefix sum
        int[] prefix = new int[n];

        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // Step 2: Base case
        int best = prefix[n - 1];

        // Step 3: DP from right to left
        for (int i = n - 2; i > 0; i--) {

            best = Math.max(best,prefix[i] - best);
        }

        return best;
    }
}