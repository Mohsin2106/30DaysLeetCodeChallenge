class Solution {
    public int maxSubarrayLength(int[] num, int k) {
        HashMap<Integer , Integer> map = new HashMap<>();
        int left = 0;
        int maxCount = 0;
        for( int right = 0 ; right< num.length ; right++){
            map.put(num[right] , map.getOrDefault(num[right],0) +1);
            while(map.get(num[right]) > k){
                map.put(num[left] , map.get(num[left]) - 1);
                left++;
            }
            maxCount= Math.max(maxCount,right - left +1);
        } 
        return maxCount;
        
    }
}