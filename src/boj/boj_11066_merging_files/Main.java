package boj.boj_11066_merging_files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {
            int k = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] bookPages = new int[k + 1];
            int[] prefixSum = new int[k + 1];

            for (int i = 1; i <= k; i++) {
                bookPages[i] = Integer.parseInt(st.nextToken());
                prefixSum[i] = prefixSum[i - 1] + bookPages[i];
            }
            int[][] dp = new int[k + 1][k + 1];
            for (int i = 0; i <= k; i++) {
                for (int j = 0; j <= k; j++) {
                    if (i == j) continue;

                    dp[i][j] = Integer.MAX_VALUE;
                }
            }

            for (int len = 2; len <= k; len++) {
                for (int i = 1; i + len - 1 <= k; i++) {
                    int j = i + len - 1;
                    for (int m = i; m < j; m++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][m] + dp[m + 1][j] +
                                (prefixSum[j] - prefixSum[i - 1]));
                    }
                }
            }


            sb.append(dp[1][k]).append("\n");
        } // tc end
        System.out.println(sb);
    }
}
