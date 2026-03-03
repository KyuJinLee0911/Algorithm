package boj.boj_2343_guitar_lesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] bluerays;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        bluerays = new int[n];
        st = new StringTokenizer(br.readLine());
        int l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            bluerays[i] = Integer.parseInt(st.nextToken());
            r += bluerays[i];
        }

        while (l < r) {
            int mid = (l + r) / 2;

            if (canRecord(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        System.out.println(getBest(l));
    }

    static boolean canRecord(int length) {
        int sum = 0;
        int cnt = 1;
        for (int i = 0; i < n; i++) {
            sum += bluerays[i];

            if (sum > length) {
                cnt++;
                sum = bluerays[i];
            }
        }

        return cnt <= m;
    }

    static int getBest(int l) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += bluerays[i];
            if (sum > l) {
                sum = bluerays[i];
            }
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }
}
