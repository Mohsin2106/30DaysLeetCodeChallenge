import java.util.*;

class Solution {

    private static final Map<Integer, Map<Integer, Integer>> FACTOR = Map.of(
            0, Map.of(),
            1, Map.of(),
            2, Map.of(2, 1),
            3, Map.of(3, 1),
            4, Map.of(2, 2),
            5, Map.of(5, 1),
            6, Map.of(2, 1, 3, 1),
            7, Map.of(7, 1),
            8, Map.of(2, 3),
            9, Map.of(3, 2)
    );

    public String smallestNumber(String num, long t) {

        Pair p = getPrimeCount(t);

        if (!p.ok)
            return "-1";

        Map<Integer, Integer> need = p.map;

        Map<Integer, Integer> factorCount = compress(need);

        if (sum(factorCount) > num.length())
            return build(factorCount);

        Map<Integer, Integer> prefix = getPrimeCount(num);

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            if (contains(prefix, need))
                return num;
        }

        for (int i = num.length() - 1; i >= 0; i--) {

            int d = num.charAt(i) - '0';

            prefix = subtract(prefix, FACTOR.get(d));

            int remain = num.length() - 1 - i;

            if (i > firstZero)
                continue;

            for (int nd = d + 1; nd <= 9; nd++) {

                Map<Integer, Integer> req =
                        compress(
                                subtract(
                                        subtract(need, prefix),
                                        FACTOR.get(nd)));

                if (sum(req) <= remain) {

                    int ones = remain - sum(req);

                    return num.substring(0, i)
                            + nd
                            + "1".repeat(ones)
                            + build(req);
                }
            }
        }

        Map<Integer, Integer> req = compress(need);

        return "1".repeat(num.length() + 1 - sum(req)) + build(req);
    }

    static class Pair {
        Map<Integer, Integer> map;
        boolean ok;

        Pair(Map<Integer, Integer> m, boolean o) {
            map = m;
            ok = o;
        }
    }

    private Pair getPrimeCount(long t) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        int[] p = {2, 3, 5, 7};

        for (int x : p) {
            while (t % x == 0) {
                t /= x;
                cnt.put(x, cnt.get(x) + 1);
            }
        }

        return new Pair(cnt, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String s) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        for (char c : s.toCharArray()) {
            for (var e : FACTOR.get(c - '0').entrySet())
                cnt.put(e.getKey(), cnt.get(e.getKey()) + e.getValue());
        }

        return cnt;
    }

    private Map<Integer, Integer> compress(Map<Integer, Integer> cnt) {

        int c2 = cnt.get(2);
        int c3 = cnt.get(3);
        int c5 = cnt.get(5);
        int c7 = cnt.get(7);

        int d8 = c2 / 3;
        c2 %= 3;

        int d9 = c3 / 2;
        c3 %= 2;

        int d4 = c2 / 2;
        c2 %= 2;

        int d6 = 0;

        if (c2 == 1 && c3 == 1) {
            d6 = 1;
            c2 = 0;
            c3 = 0;
        }

        if (c3 == 1 && d4 == 1) {
            d4 = 0;
            c3 = 0;
            c2 = 1;
            d6 = 1;
        }

        Map<Integer, Integer> ans = new HashMap<>();

        ans.put(2, c2);
        ans.put(3, c3);
        ans.put(4, d4);
        ans.put(5, c5);
        ans.put(6, d6);
        ans.put(7, c7);
        ans.put(8, d8);
        ans.put(9, d9);

        return ans;
    }

    private String build(Map<Integer, Integer> cnt) {

        StringBuilder sb = new StringBuilder();

        for (int d = 2; d <= 9; d++) {
            int f = cnt.getOrDefault(d, 0);

            while (f-- > 0)
                sb.append(d);
        }

        return sb.toString();
    }

    private boolean contains(Map<Integer, Integer> a, Map<Integer, Integer> b) {

        for (int p : new int[]{2, 3, 5, 7})
            if (a.get(p) < b.get(p))
                return false;

        return true;
    }

    private Map<Integer, Integer> subtract(Map<Integer, Integer> a,
                                           Map<Integer, Integer> b) {

        Map<Integer, Integer> res = new HashMap<>(a);

        for (var e : b.entrySet())
            res.put(e.getKey(),
                    Math.max(0, res.get(e.getKey()) - e.getValue()));

        return res;
    }

    private int sum(Map<Integer, Integer> m) {

        int s = 0;

        for (int v : m.values())
            s += v;

        return s;
    }
}