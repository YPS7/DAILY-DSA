Sliding Window — deep dive (Java)

Great — we’ll go pattern by pattern. Below is a focused, practical walkthrough of the Sliding Window pattern with intuition, how to spot it, several fully-commented Java code templates (real, runnable method bodies), and 5 curated practice problems (easy → medium → hard) with company tags. I researched common interview instances and problem pages to pick representative problems. 
GeeksforGeeks
LeetCode
+3
LeetCode
+3
LeetCode
+3

1) Intuition (in one line)

When the answer depends on a contiguous subarray/substring and you can expand/contract a window while maintaining an aggregate (sum, counts, max/min), move two pointers (left, right) instead of recomputing from scratch. This reduces many O(n·k) approaches to O(n). 
GeeksforGeeks

2) Basic idea & variants

Fixed-size sliding window: window size k is given. Move the window one step at a time, update aggregate by removing the left element and adding the new right element. (e.g., max sum of subarray of size k).

Variable-size sliding window: expand right until the window breaks a constraint; then advance left until the window is valid again. Use for “longest/shortest substring/subarray meeting a rule.” (e.g., longest substring without repeating characters, minimum window substring).

Counting / combinatorial variants: sometimes you count all subarrays satisfying a property — use techniques like count(atMost(K)) - count(atMost(K-1)). (e.g., number of subarrays with exactly K distinct). 
LeetCode
AlgoMonster

3) How to recognize a sliding-window problem

The problem mentions contiguous subarray/substring and asks for longest/shortest length, sum under/over constraint, or count of subarrays meeting some property.

Keywords: “longest substring”, “shortest subarray”, “subarray sum ≤ X”, “at most K distinct”, “no repeating characters”, “window of size k”. 
GeeksforGeeks

4) Code templates (Java) — fully commented, read line-by-line

I’ll give five fully implemented methods covering the principal sliding window variants:

Fixed-size window — maximum sum subarray of size K (Easy)

Variable-size — longest substring without repeating characters (classic) (Easy/Medium)

Monotonic deque variant — sliding window maximum (Medium)

Minimum window substring — smallest window containing all chars of t (Hard)

Count subarrays with exactly K distinct integers — atMost trick (Medium/Hard)

1) Fixed-size window — Maximum Sum Subarray of Size K

Problem idea: given nums[] and k, find maximum sum of any contiguous subarray of length k.

// Return maximum sum of any contiguous subarray of length k.
// If nums length < k, returns Integer.MIN_VALUE (or choose behavior desired).
public static int maxSumSubarrayOfSizeK(int[] nums, int k) {
    if (nums == null || nums.length < k || k <= 0) return Integer.MIN_VALUE;
    int windowSum = 0;
    // Build initial window [0..k-1]
    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }
    int maxSum = windowSum; // best seen so far
    // Slide the window one step at a time: remove left, add new right
    for (int right = k; right < nums.length; right++) {
        windowSum += nums[right];           // add new element entering window
        windowSum -= nums[right - k];       // remove element exiting window
        if (windowSum > maxSum) maxSum = windowSum;
    }
    return maxSum;
}


Why it’s O(n): each element enters and leaves the running sum once. No nested loops.

2) Variable-size window — Longest Substring Without Repeating Characters

Classic: maintain a map of last index seen; expand right; when a duplicate appears inside window move left right after previous index of that char.

// Returns length of the longest substring with all unique characters.
public static int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    // Map char -> last index seen (we store index + 1 to avoid -1 checks)
    int[] lastIndex = new int[256]; // assume ASCII; for unicode use Map<Character,Integer>
    for (int i = 0; i < lastIndex.length; i++) lastIndex[i] = -1;
    int left = 0, maxLen = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        // If c was seen and its last index >= left, it lies inside current window.
        if (lastIndex[c] >= left) {
            // move left to (lastIndex[c] + 1) to exclude previous occurrence
            left = lastIndex[c] + 1;
        }
        // update last seen index for c
        lastIndex[c] = right;
        // update best length
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}


Key idea: left only moves forward; each character processed once → O(n).

3) Sliding Window Maximum — Monotonic Deque

Find the max in each window of size k. Use a deque that stores indices of candidates in decreasing order of values. Front of deque = index of current max.

// Returns array of sliding window maximums for each window of size k.
// Example: nums = [1,3,-1,-3,5,3,6,7], k = 3 -> [3,3,5,5,6,7]
public static int[] slidingWindowMaximum(int[] nums, int k) {
    if (nums == null || k <= 0) return new int[0];
    int n = nums.length;
    if (k > n) k = n;
    int[] ans = new int[n - k + 1];
    Deque<Integer> dq = new ArrayDeque<>(); // will store indices, values decreasing

    for (int i = 0; i < n; i++) {
        // 1) Remove indices out of current window (i - k)
        while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
            dq.pollFirst();
        }

        // 2) Maintain deque as strictly decreasing values:
        // remove from tail while current value >= value at tail index
        while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
            dq.pollLast();
        }
        // add current index to tail
        dq.offerLast(i);

        // 3) First window completes at i >= k-1
        if (i >= k - 1) {
            ans[i - k + 1] = nums[dq.peekFirst()]; // front is max index
        }
    }
    return ans;
}


Why deque works: each index enters and can be removed at most once — O(n).

Reference: standard deque solution for LeetCode 239. 
LeetCode
AlgoMonster

4) Minimum Window Substring — smallest substring of s that contains all chars of t

This is a more delicate variable window: expand right to include required chars, then advance left to shrink to minimal valid window. Track counts to know when window is “valid”.

// Returns the minimum window substring of s that contains all chars of t (including duplicates).
// If nothing found, return "".
public static String minWindow(String s, String t) {
    if (s == null || t == null || s.length() < t.length()) return "";
    int[] need = new int[128]; // frequency of chars needed from t
    for (char c : t.toCharArray()) need[c]++;

    int required = 0; // number of distinct characters we need to satisfy counts for
    for (int c = 0; c < need.length; c++) if (need[c] > 0) required++;

    int[] window = new int[128]; // freq in current window
    int formed = 0; // how many distinct chars currently meet required freq
    int l = 0, r = 0;
    int minLen = Integer.MAX_VALUE, minLeft = 0;

    while (r < s.length()) {
        char cr = s.charAt(r);
        window[cr]++;
        // if after adding, this char now meets the required count, increment formed
        if (need[cr] > 0 && window[cr] == need[cr]) formed++;

        // Try and contract the window till it ceases to be 'desirable'
        while (l <= r && formed == required) {
            // Update result
            if (r - l + 1 < minLen) {
                minLen = r - l + 1;
                minLeft = l;
            }
            // pop from left
            char cl = s.charAt(l);
            window[cl]--;
            // if this char was required and now window lacks enough, formed decreases
            if (need[cl] > 0 && window[cl] < need[cl]) formed--;
            l++;
        }
        r++;
    }

    return (minLen == Integer.MAX_VALUE) ? "" : s.substring(minLeft, minLeft + minLen);
}


Notes: this is the standard solution for LeetCode 76. Carefully tracking formed vs required prevents unnecessary scans. 
LeetCode
AlgoMonster

5) Count subarrays with exactly K distinct integers — using atMost(K) trick

Count(exactly K) = Count(at most K) − Count(at most K−1). Implement countAtMost(K) with sliding window and frequency map.

// Return number of subarrays with exactly K distinct integers.
public static int subarraysWithKDistinct(int[] nums, int K) {
    return countAtMost(nums, K) - countAtMost(nums, K - 1);
}

// Helper: count subarrays with at most K distinct elements
private static int countAtMost(int[] nums, int K) {
    if (K < 0) return 0;
    Map<Integer, Integer> freq = new HashMap<>();
    int left = 0;
    int res = 0;
    for (int right = 0; right < nums.length; right++) {
        freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);
        // If we exceed K distinct, move left to reduce distinct count
        while (freq.size() > K) {
            int val = nums[left++];
            freq.put(val, freq.get(val) - 1);
            if (freq.get(val) == 0) freq.remove(val);
        }
        // All subarrays ending at right and starting anywhere in [left..right] are valid
        res += (right - left + 1);
    }
    return res;
}


Why it works: countAtMost(K) sums the number of subarrays ending at each right with ≤ K distinct elements. Subtracting gives exactly K. See LeetCode 992 and common solutions.





Practice Set (company tags ≈ most frequent sightings)

Easy

Maximum Sum Subarray of Size K — (Amazon)

Best Time to Buy and Sell Stock (I) — (Amazon, Google)

Longest Substring with At Most K Distinct (K small) — (Amazon)

Medium

Longest Substring Without Repeating Characters — (Google, Amazon)

Longest Repeating Character Replacement — (Google, Meta)

Fruit Into Baskets (at most 2 distinct) — (Amazon)

Subarrays with K Different Integers (exactly K) — (Amazon)

Sliding Window Maximum — (Amazon, Google)

Hard

Minimum Window Substring — (Amazon)

Substring with Concatenation of All Words — (Google)
