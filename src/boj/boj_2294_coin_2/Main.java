package boj.boj_2294_coin_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] coins = new int[n];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            coins[i] = Integer.parseInt(br.readLine());
            min = Math.min(min, coins[i]);
        }

        int[] dp = new int[k + 1];
        Arrays.fill(dp, 10001);
        dp[0] = 0;

        for (int price = min; price <= k; price++) {
            for (int idx = 0; idx < n; idx++) {
                int prev = price - coins[idx];
                if (prev < 0) continue;
                dp[price] = Math.min(dp[price], dp[prev] + 1);
            }
        }

        int ans = dp[k] == 10001 ? -1 : dp[k];

        System.out.println(ans);
    }
}
