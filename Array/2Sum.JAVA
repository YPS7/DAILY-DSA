class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> ma = new HashMap<>();
        for(int i=0;i<nums.length;i++)
        {
            if(ma.containsKey(target-nums[i]))
            {
                res[1]=i;
                res[0]=ma.get(target-nums[i]);
            }
            ma.put(nums[i],i);
        }
        return res;
    }
}
