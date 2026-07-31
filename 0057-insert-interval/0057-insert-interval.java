class Solution {
    public int[][] insert(int[][] intervals, int[] newIntervals) {
        List<int[]> ans = new ArrayList<>();
        int i =0;
        int n = intervals.length;
        while(i < n && intervals[i][1] < newIntervals[0]){
            ans.add(intervals[i]);
            i++;
        }
        while(i < n && intervals[i][0] <= newIntervals[1]){
            newIntervals[0] = Math.min(newIntervals[0] , intervals[i][0]);
            newIntervals[1] = Math.max(newIntervals[1] , intervals[i][1]);
            i++;
        }
        ans.add(newIntervals);
        while(i < n){
            ans.add(intervals[i]);
            i++;
        }
        return ans.toArray(new int[ans.size()][]);
        
    }
}