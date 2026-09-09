class Solution {
    public long countCommas(long n) {
        long answer = 0;
        long threshold = 1000;

        while(threshold <= n){
            answer += n - threshold + 1;
            threshold *=1000;
        }
        return answer;
    }
}