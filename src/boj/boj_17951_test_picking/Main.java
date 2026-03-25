package boj.boj_17951_test_picking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, k;
    static int[] tests;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        tests = new int[n];
        st = new StringTokenizer(br.readLine());
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            tests[i] = Integer.parseInt(st.nextToken());
            sum += tests[i];
            min = Math.min(min, tests[i]);
        }

        int l = min, r = sum;
        while (l < r) {
            int mid = (l + r + 1) / 2;

            if (getGroupCount(mid) >= k) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }


        System.out.println(l);
    }

    static int getGroupCount(int mid) {
        int cnt = 0;
        int sum = 0;
        // 최소 mid 이상이 되는 group의 수가 k개 이상이면 -> 가능 k개가 안되면 불가능
        for (int i = 0; i < n; i++) {
            sum += tests[i];

            if (sum >= mid) {
                cnt++;
                sum = 0;
            }
        }

        return cnt;
    }
}
