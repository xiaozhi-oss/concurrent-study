package com.xiaozhi.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaozhi
 */
public class Test {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.canConstruct("aa", "ab"));
    }
}
class Solution2 {
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for(int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            // 如果不存在该值或者值中的次数为0，那么返回false
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            }
            map.put(c, map.get(c) - 1);     // 如果存在，那么次数 -1
        }
        return true;
    }
}
