class Solution {
    public int[] resultArray(int[] nums) {

        ArrayList<Integer> arr1 = new ArrayList<>();
        ArrayList<Integer> arr2 = new ArrayList<>();

        // First element -> arr1
        arr1.add(nums[0]);

        // Second element -> arr2
        arr2.add(nums[1]);

        // Process remaining elements
        for (int i = 2; i < nums.length; i++) {

            int last1 = arr1.get(arr1.size() - 1);
            int last2 = arr2.get(arr2.size() - 1);

            if (last1 > last2) {
                arr1.add(nums[i]);
            } else {
                arr2.add(nums[i]);
            }
        }

        // Add arr2 at the end of arr1
        arr1.addAll(arr2);

        // Convert ArrayList to int[]
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = arr1.get(i);
        }

        return result;
    }
}