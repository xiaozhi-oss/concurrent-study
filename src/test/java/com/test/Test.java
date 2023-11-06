package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaozhi
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = { 1, 3, 5, 5, 2, 5};
        int[] arr2 = { 1, 3, 5, 5, 2, 5};
        Solution solution = new Solution();
        int[] intersection = solution.intersection(arr2, arr);
        System.out.println(Arrays.toString(intersection));
    }
}

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        int[] hash1 = new int[1002];
        int[] hash2 = new int[1002];
        for(int i : nums1)
            hash1[i]++;
        for(int i : nums2)
            hash2[i]++;
        List<Integer> resList = new ArrayList<>();
        for(int i = 0; i < 1002; i++)
            if(hash1[i] > 0 && hash2[i] > 0)
                resList.add(i);
        int index = 0;
        int res[] = new int[resList.size()];
        for(int i : resList)
            res[index++] = i;
        return res;
    }
}
