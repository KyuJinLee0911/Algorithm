package boj.boj_2629_double_arm_scale.bit_masking;

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
        int[] dp = new int[sum + 1];
        for (int i = 0; i < n; i++) {
            dp[weights[i]] = (1 << (i + 1));
        }

        for (int i = min + 1; i <= sum; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i] != 0) break;

                int idx = i - weights[j];
                if (idx < 0) continue;

                int mask = 1 << (j + 1);

                boolean alreadyUsed = (dp[idx] & mask) != 0;
                if (alreadyUsed) continue;

                boolean isSingleWeight = (i == weights[j]);
                boolean canExtend = (dp[idx] != 0);

                if (!isSingleWeight && !canExtend) continue;

                dp[i] = dp[idx] | mask;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            boolean flag = false;
            int bead = beads[i];
            if (bead > sum) {
                sb.append("N ");
                continue;
            }

            if (dp[bead] != 0) {
                sb.append("Y ");
                continue;
            }

            for (int j = bead; j <= sum; j++) {
                if (dp[j - bead] != 0 && dp[j] != 0) {
                    sb.append("Y ");
                    flag = true;
                    break;
                }
            }
            if (!flag)
                sb.append("N ");
        }
        System.out.println(sb.toString().trim());
    }
}

// 2 4 8 16
