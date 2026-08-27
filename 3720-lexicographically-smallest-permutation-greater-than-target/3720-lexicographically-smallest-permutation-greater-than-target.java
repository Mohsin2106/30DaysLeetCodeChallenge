class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        // Count frequency of characters in s
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder ans = new StringBuilder();

        // Try to match target from left to right
        int i = 0;

        while (i < n) {
            int idx = target.charAt(i) - 'a';

            // If we can match target[i], do it
            if (freq[idx] > 0) {
                ans.append(target.charAt(i));
                freq[idx]--;
                i++;
            } else {
                // Cannot match, so try smallest character > target[i]
                int greater = findGreater(freq, idx);

                if (greater != -1) {
                    ans.append((char) ('a' + greater));
                    freq[greater]--;

                    // Add remaining characters in sorted order
                    appendRemaining(ans, freq);

                    return ans.toString();
                }

                break;
            }
        }

        // If we matched the entire target,
        // or got stuck, backtrack
        while (ans.length() > 0) {

            // Remove last matched character
            int pos = ans.length() - 1;

            char removed = ans.charAt(pos);
            ans.deleteCharAt(pos);

            // Put it back into available characters
            freq[removed - 'a']++;

            int targetChar = target.charAt(pos) - 'a';

            // Find smallest available character > target[pos]
            int greater = findGreater(freq, targetChar);

            if (greater != -1) {

                ans.append((char) ('a' + greater));
                freq[greater]--;

                // Remaining characters should be smallest possible
                appendRemaining(ans, freq);

                return ans.toString();
            }
        }

        return "";
    }

    // Find smallest available character strictly greater than target character
    private int findGreater(int[] freq, int targetChar) {

        for (int i = targetChar + 1; i < 26; i++) {
            if (freq[i] > 0) {
                return i;
            }
        }

        return -1;
    }

    // Append remaining characters in lexicographically smallest order
    private void appendRemaining(StringBuilder ans, int[] freq) {

        for (int i = 0; i < 26; i++) {
            while (freq[i] > 0) {
                ans.append((char) ('a' + i));
                freq[i]--;
            }
        }
    }
}