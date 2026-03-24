package boj.boj_17404_rgb_street_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, min;
    static int[][] price, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        price = new int[n + 1][3];
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                price[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[n + 1][3];
        min = Integer.MAX_VALUE;

        init();
        dp[1][0] = price[1][0];
        calculate(1, 2);

        init();
        dp[1][1] = price[1][1];
        calculate(0, 2);

        init();
        dp[1][2] = price[1][2];
        calculate(0, 1);

        System.out.println(min);
    }

    static void calculate(int endColor1, int endColor2) {
        for (int j = 2; j <= n; j++) {
            dp[j][0] = price[j][0] + Math.min(dp[j - 1][1], dp[j - 1][2]);
            dp[j][1] = price[j][1] + Math.min(dp[j - 1][0], dp[j - 1][2]);
            dp[j][2] = price[j][2] + Math.min(dp[j - 1][0], dp[j - 1][1]);
        }

        min = Math.min(min, Math.min(dp[n][endColor1], dp[n][endColor2]));
    }

    static void init() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], 1_000_001);
        }
    }
}
