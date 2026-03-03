package boj.boj_1561_amusement_park;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final long MAX_TIME = 60_000_000_000L;
    static int n, m;
    static int[] times;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        times = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            times[i] = Integer.parseInt(st.nextToken());
        }

        if (n <= m) {
            System.out.println(n);
            return;
        }

        long l = 0, r = MAX_TIME;

        while (l < r) {
            long mid = (l + r) / 2;

            if (count(mid) >= n) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        long cntBefore = count(l - 1);
        for (int i = 0; i < m; i++) {
            long mod = l % times[i];
            if (mod != 0) continue;

            if (cntBefore == n - 1) {
                System.out.println(i + 1);
                break;
            } else {
                cntBefore++;
            }
        }
    }

    static long count(long time) {
        long cnt = 0;
        for (int i = 0; i < m; i++) {
            cnt += time / times[i] + 1;
        }
        return cnt;
    }
}
// 1 ~ m -> 번호에 맞는 놀이기구 탐
// m + 1 ~ n -> 각 타이밍에 비어있는 놀이기구 탑승
