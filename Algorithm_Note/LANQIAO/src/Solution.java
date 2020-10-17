import java.util.*;

class Solution {
    Set<Long> setA = new HashSet<>();
    Set<Long> setB = new HashSet<>();

    public boolean checkPalindromeFormation(String a, String b) {
        StringBuilder sa = new StringBuilder(a);
        StringBuilder sb = new StringBuilder(b);

        for (int i = 0; i < sa.length()/2; i++) {
            StringBuilder preA = new StringBuilder(sa.substring(0, i));
            StringBuilder subA = new StringBuilder(sa.substring(i));
            StringBuilder preB = new StringBuilder(sb.substring(0, i));
            StringBuilder subB = new StringBuilder(sb.substring(i));

            StringBuilder midA = new StringBuilder(preA.substring(0, subA.length()-preB.length()));
            StringBuilder endA = new StringBuilder(preA.substring(subA.length()-preB.length()));
            StringBuilder midB = new StringBuilder(preB.substring(0, subB.length()-preA.length()));
            StringBuilder endB = new StringBuilder(preB.substring(subB.length()-preA.length()));

            System.out.println(midA+","+endA);

            if (isPA(midA, i) && isP(preB.append(endA))) return true;
            if (isPB(midB, i) && isP(preA.append(endB))) return true;
        }

        for (int i = sa.length()/2-1; i < sa.length(); i++) {
            StringBuilder preA = new StringBuilder(sa.substring(0, i));
            StringBuilder subA = new StringBuilder(sa.substring(i));
            StringBuilder preB = new StringBuilder(sb.substring(0, i));
            StringBuilder subB = new StringBuilder(sb.substring(i));

            StringBuilder staA = new StringBuilder(preA.substring(0, preA.length()-subB.length()));
            StringBuilder midA = new StringBuilder(preA.substring(preA.length()-subB.length()));
            StringBuilder staB = new StringBuilder(preB.substring(0, preB.length()-subA.length()));
            StringBuilder midB = new StringBuilder(preB.substring(preB.length()-subA.length()));


            if (isPA(midA, i) && isP(staA.append(subB))) return true;
            if (isPB(midB, i) && isP(staB.append(subA))) return true;
        }

        return false;
    }

    private boolean isPA(StringBuilder s, int le) {
        if (setA.contains(le*1000000+le+s.length())) return true;
        if (isP(s)) {
            setA.add((long)le*1000000+le+s.length());
            return true;
        } else return false;
    }

    private boolean isPB(StringBuilder s, int le) {
        if (setB.contains(le*1000000+le+s.length())) return true;
        if (isP(s)) {
            setB.add((long)le*1000000+le+s.length());
            return true;
        } else return false;
    }

    private boolean isP(StringBuilder s) {
        char[] sarr = s.toString().toCharArray();
        int p1, p2;
        for (p1 = p2 = 0; p2 < s.length(); p2++) {
            sarr[p1++] = sarr[p2];
        }
        //p1 is len
        p2 = 0; p1 -= 1;
        while (p2 < p1) {
            if (sarr[p2] != sarr[p1]) return false;
            p2++; p1--;
        }
        return true;
    }
}