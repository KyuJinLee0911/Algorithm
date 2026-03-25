package boj.boj_2293_coin_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        dp[0] = 1;
        for (int coin : coins) {
            for (int x = coin; x <= k; x++) {
                dp[x] += dp[x - coin];
            }
        }

        System.out.println(dp[k]);
    }
}