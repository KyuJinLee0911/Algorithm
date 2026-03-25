package boj.boj_1311_decide_what_to_do;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int INF = 200_001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] price = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                price[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int maskSize = 1 << n;
        int[][] dp = new int[n][maskSize];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < n; i++) {
            dp[0][1 << i] = price[0][i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < maskSize; j++) {
                if (dp[i - 1][j] == INF) continue;

                for (int k = 0; k < n; k++) {
                    int mask = 1 << k;
                    if ((j & mask) != 0) continue;
                    int next = j | mask;
                    dp[i][next] = Math.min(dp[i][next], dp[i - 1][j] + price[i][k]);
                }
            }
        }
        System.out.println(dp[n - 1][(1 << n) - 1]);
    }
}
