class Solution {

    int n;
    int[] suffix;
    int[][] dp;

    int solve(int i, int M) {

        // No stones left
        if (i >= n) {
            return 0;
        }

        // Can take all remaining piles
        if (i + 2 * M >= n) {
            return suffix[i];
        }

        // Already calculated
        if (dp[i][M] != -1) {
            return dp[i][M];
        }

        int ans = 0;

        // Take X piles, where 1 <= X <= 2*M
        for (int X = 1; X <= 2 * M; X++) {

            // Opponent starts from i + X
            // New M = max(M, X)
            int opponent = solve(i + X, Math.max(M, X));

            // Total remaining - opponent's best
            int currentPlayer = suffix[i] - opponent;

            ans = Math.max(ans, currentPlayer);
        }

        return dp[i][M] = ans;
    }

    public int stoneGameII(int[] piles) {

        n = piles.length;

        // suffix[i] = sum of piles from i to n-1
        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // dp[i][M]
        dp = new int[n][n + 1];

        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(dp[i], -1);
        }

        // Game starts at index 0 with M = 1
        return solve(0, 1);
    }
}