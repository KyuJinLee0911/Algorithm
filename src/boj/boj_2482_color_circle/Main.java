package boj.boj_2482_color_circle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int MOD = 1_000_000_003;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        dp[1][1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = modularSum(dp[i - 1][j], dp[i - 2][j - 1]);
            }
        }

        // n개 중 k개를 고르는 경우
        // - 1번을 사용하는 경우 - 1, 2, n번을 사용 못함 dp[n - 3][k - 1]
        // - 1번을 사용하지 않는 경우 - 1번을 사용 못함 dp[n - 1][k]
        int ans = modularSum(dp[n - 3][k - 1], dp[n - 1][k]);
        System.out.println(ans);
    }

    static int modularSum(int a, int b) {
        return (a % MOD + b % MOD) % MOD;
    }
}
