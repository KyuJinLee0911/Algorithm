package boj.boj_7579_app.single_array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] bytes = new int[n];
        int[] costs = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            bytes[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            costs[i] = Integer.parseInt(st.nextToken());
            sum += costs[i];
        }

        int[] dp = new int[sum + 1];

        dp[0] = 0;


        for (int i = 0; i < n; i++) {
            for (int c = sum; c >= costs[i]; c--) {
                dp[c] = Math.max(dp[c], dp[c - costs[i]] + bytes[i]);
            }
        }

        int ans = 0;
        for (int c = 0; c <= sum; c++) {
            if (dp[c] < m) continue;

            ans = c;
            break;
        }

        System.out.println(ans);
    }
}
