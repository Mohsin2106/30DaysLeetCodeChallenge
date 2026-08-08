class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        // next[i] = earliest position in word1 from i onward
        // where word2[j] can be matched.
        int[] suffix = new int[m];

        int i = n - 1;

        // We need positions where word2 can be matched
        // from right to left.
        for (int j = m - 1; j >= 0; j--) {

            while (i >= 0 && word1.charAt(i) != word2.charAt(j)) {
                i--;
            }

            if (i < 0) {
                suffix[j] = -1;
            } else {
                suffix[j] = i;
                i--;
            }
        }

        int[] ans = new int[m];

        int j = 0;
        boolean usedMismatch = false;

        for (i = 0; i < n && j < m; i++) {

            // Normal matching
            if (word1.charAt(i) == word2.charAt(j)) {

                ans[j] = i;
                j++;
            }

            // Use the one allowed mismatch
            else if (!usedMismatch) {

                // If this is the last character, we can always
                // use the mismatch.
                if (j == m - 1) {

                    ans[j] = i;
                    usedMismatch = true;
                    j++;
                }

                // Otherwise, make sure the remaining word2
                // can still be matched after i.
                else if (suffix[j + 1] > i) {

                    ans[j] = i;
                    usedMismatch = true;
                    j++;
                }
            }
        }

        // Could not match all characters
        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}