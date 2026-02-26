package boj.boj_1450_knapsack_problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static long[] items;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        items = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            items[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(items);
        int cnt = mitm(c);

        System.out.println(cnt);
    }

    static int upperBound(List<Long> sums, long val) {
        int l = 0, r = sums.size();
        while (l < r) {
            int mid = (l + r) / 2;

            if (sums.get(mid) <= val) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    static void generateSums(int l, int r, long sum, long val, ArrayList<Long> out) {
        if (l == r) {
            out.add(sum);
            return;
        }

        generateSums(l + 1, r, sum, val, out);
        generateSums(l + 1, r, sum + items[l], val, out);
    }

    static int mitm(long target) {
        int n = items.length;
        int mid = n / 2;
        int cnt = 0;

        ArrayList<Long> left = new ArrayList<>();
        ArrayList<Long> right = new ArrayList<>();

        generateSums(0, mid, 0, target, left);
        generateSums(mid, n, 0, target, right);

        Collections.sort(right);
        for (long sumLeft : left) {
            long need = target - sumLeft;
            cnt += upperBound(right, need);
        }

        return cnt;
    }
}
