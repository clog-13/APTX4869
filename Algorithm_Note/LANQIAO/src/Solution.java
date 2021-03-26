import java.math.BigInteger;
import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] s = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(sol.groupAnagrams(s));
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        int[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};

        HashMap<BigInteger, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] arr = str.toCharArray();
            BigInteger sum = new BigInteger("1");
            for (char c : arr) {
                sum = sum.multiply(new BigInteger(String.valueOf(primes[c-'a'])));
            }

            if (!map.containsKey(sum)) map.put(sum, new ArrayList<>());
            map.get(sum).add(str);
        }
        return new ArrayList<>(map.values());
    }
}