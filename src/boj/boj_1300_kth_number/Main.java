package boj.boj_1300_kth_number;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long k = Long.parseLong(br.readLine());

        long l = 1, r = (long) n * n;
        while (l < r) {
            long mid = (l + r) / 2;
            if (count(mid, n) >= k) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        System.out.println(l);
    }

    static long count(long mid, int n) {
        long cnt = 0;
        for (int i = 1; i <= n; i++) {
            cnt += Math.min(n, mid / i);
        }
        return cnt;
    }
}
// 1  2  3  4
// 2  4  6  8
// 3  6  9  12
// 4  8  12 16

// 1 2 3 4 5 6 7 8 9
// 1 2 2 3 3 4 6 6 9
