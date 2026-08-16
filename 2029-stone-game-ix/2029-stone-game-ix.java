class Solution {
    public boolean stoneGameIX(int[] stones) {
        int [] count = new int[3];
        for(int stone : stones){
            count[stone % 3]++;
        }
        // check for remainder 0 stone is even 
        if(count[0] % 2 ==0){
            return count[1] > 0 && count[2] >0;
        }
        // check for remainder 0 stone is odd
        return Math.abs(count[1] - count[2] ) > 2;
    }
}