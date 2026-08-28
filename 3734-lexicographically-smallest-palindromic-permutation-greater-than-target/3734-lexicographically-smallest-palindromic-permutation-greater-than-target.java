class Solution {

    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // A palindromic permutation is possible only if
        // at most one character has odd frequency.
        int odd = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                mid = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // halfCount[i] = number of character i in left half
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        int m = n / 2;

        /*
         * First try to match target's left half.
         *
         * We consume characters pair by pair.
         */
        int[] remaining = halfCount.clone();
        int i = 0;

        while (i < m) {
            int c = target.charAt(i) - 'a';

            if (remaining[c] == 0) {
                break;
            }

            remaining[c]--;
            i++;
        }

        /*
         * If we matched the complete left half,
         * check whether the palindrome formed from it
         * is strictly greater than target.
         */
        if (i == m) {
            String left = target.substring(0, m);
            String candidate = buildPalindrome(left, mid);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Backtrack from right to left.
         *
         * At position i, try to replace target[i]
         * with the smallest available character > target[i].
         */
        for (int pos = Math.min(i, m - 1); pos >= 0; pos--) {

            // If pos was previously matched, restore it
            if (pos < i) {
                int old = target.charAt(pos) - 'a';
                remaining[old]++;
            }

            int current = target.charAt(pos) - 'a';

            // Find smallest available character > current
            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger == -1) {
                continue;
            }

            // Build smallest possible answer
            StringBuilder left = new StringBuilder();

            // Same prefix
            left.append(target, 0, pos);

            // Put the smallest character greater than target[pos]
            left.append((char) ('a' + bigger));

            remaining[bigger]--;

            // Fill remaining positions with smallest characters
            for (int c = 0; c < 26; c++) {
                while (remaining[c] > 0) {
                    left.append((char) ('a' + c));
                    remaining[c]--;
                }
            }

            return buildPalindrome(left.toString(), mid);
        }

        return "";
    }


    private String buildPalindrome(String left, char mid) {
        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if (mid != 0) {
            ans.append(mid);
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }
}