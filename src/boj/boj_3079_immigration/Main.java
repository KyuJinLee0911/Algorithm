package boj.boj_3079_immigration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, arr[i]);
        }

        long l = 0, r = (long) max * m;
        while (l < r) {
            long mid = (l + r) / 2;

            if (getCount(mid) >= m) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        System.out.println(l);
    }

    static long getCount(long time) {
        long cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt += (time / arr[i]);
            if (cnt >= m) {
                return cnt;
            }
        }

        return cnt;
    }
}
