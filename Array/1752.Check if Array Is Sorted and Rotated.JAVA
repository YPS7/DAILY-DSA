class solution {
public boolean check(int[] nums)
{
int n = nums.length;
int rot = 0;
for(int i=0;i<n;i++)
{
if(nums[(i+1)%n] < nums[i])
{
rot++;
}
if(rot>1)
{
return false;
}
}
return true;
}}