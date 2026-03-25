package boj.boj_2629_double_arm_scale.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] weights = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int sum = 0;

        for (int i = 0; i < n; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
            sum += weights[i];
        }
        int min = weights[0];
        int m = Integer.parseInt(br.readLine());
        int[] beads = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            beads[i] = Integer.parseInt(st.nextToken());
        }

        boolean[][] dp = new boolean[n + 1][sum + 1];
        dp[0][0] = true;

        for (int i = 0; i < n; i++) {
            int w = weights[i];
            for (int diff = 0; diff <= sum; diff++) {
                if (!dp[i][diff]) continue;

                dp[i + 1][diff] = true;

                if (diff + w <= sum) {
                    dp[i + 1][diff + w] = true;
                }

                dp[i + 1][Math.abs(diff - w)] = true;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int bead : beads) {
            if (bead > sum) {
                sb.append("N ");
            } else if (dp[n][bead]) {
                sb.append("Y ");
            } else {
                sb.append("N ");
            }
        }

        System.out.println(sb.toString().trim());
    }
}
