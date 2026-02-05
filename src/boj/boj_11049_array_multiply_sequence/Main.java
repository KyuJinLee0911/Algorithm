package boj.boj_11049_array_multiply_sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] rxc = new int[n + 1][2];
        StringTokenizer st;
        int[] p = new int[n + 1];
        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            rxc[i][0] = r;
            rxc[i][1] = c;
            if(i == 1) {
                p[0] = r;
            }
            p[i] = c;
        }

        int[][] dp = new int[n + 1][n + 1];
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(i == j) continue;
                dp[i][j] = 501*501*501;
            }
        }

        for(int len = 2; len <= n; len++){
            for(int i = 1; i + len - 1 <= n; i++){
                int j = i + len - 1;
                for(int k = i; k < j; k++){
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j]);
                }
            }
        }

        System.out.println(dp[1][n]);

    }
}
