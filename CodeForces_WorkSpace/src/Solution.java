import java.util.*;

public class Solution {

    public static void main(String[] args) {
        String[] paths = {"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"};
        new Solution().findDuplicate(paths);
    }

    public List < List < String >> findDuplicate(String[] paths) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String path: paths) {
            String[] arr = path.split(" ");
            for (int i = 1; i < arr.length; i++) {
                String[] name_cont = arr[i].split("\\(");
                name_cont[1] = name_cont[1].replace(")", "");
                List<String> list = map.getOrDefault(name_cont[1], new ArrayList<>());
                list.add(arr[0] + "/" + name_cont[0]);
                map.put(name_cont[1], list);
            }
        }

        List<List<String>> res = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).size() > 1) res.add(map.get(key));
        }
        return res;
    }
}