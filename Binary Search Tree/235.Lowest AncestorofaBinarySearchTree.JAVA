class Solution {
    public TreeNode lowestComAnce(TreeNode root, TreeNode p, TreeNode q)
    {
        if(root==null)
        return null;

        int cur = root.val;
        if(cur<p && cur<q)
        {
            return lowestComAnce(root.right, p, q);
        }
        if(cur>p && cur>q)
        {
            return lowestComAnce(root.left, p, q);
        }
        return root;
    }
}