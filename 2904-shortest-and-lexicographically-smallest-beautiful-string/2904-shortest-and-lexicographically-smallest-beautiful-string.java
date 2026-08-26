class Solution {
    public String shortestBeautifulSubstring(String s, int k) {

        int left = 0;
        int ones = 0;

        String ans = "";

        for (int right = 0; right < s.length(); right++) {

            // Add current character to the window
            if (s.charAt(right) == '1') {
                ones++;
            }

            // We have exactly k ones
            while (ones == k) {

                int length = right - left + 1;
                String current = s.substring(left, right + 1);

                // First valid substring
                if (ans.equals("")) {
                    ans = current;
                }

                // Shorter substring
                else if (length < ans.length()) {
                    ans = current;
                }

                // Same length but lexicographically smaller
                else if (length == ans.length() && current.compareTo(ans) < 0) {
                    ans = current;
                }

                // Move left to try making window smaller
                if (s.charAt(left) == '1') {
                    ones--;
                }

                left++;
            }
        }

        return ans;
    }
}