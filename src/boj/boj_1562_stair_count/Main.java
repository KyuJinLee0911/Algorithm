package boj.boj_1562_stair_count;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int MOD = 1_000_000_000;
    static final int FULL = (1 << 10) - 1;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[][][] dp = new long[N + 1][10][1 << 10];

        for(int d = 1; d <= 9; d++){
            dp[1][d][1 << d] = 1;
        }

        for(int len = 1; len < N; len++){
            for(int last = 0; last <= 9; last++){
                for(int mask = 0; mask <= FULL; mask++){
                    long cur = dp[len][last][mask];
                    if(cur == 0) continue;

                    if(last > 0){
                        int next = last - 1; // 다음에 올 수(현재 마지막 자리 수 - 1)
                        int nextMask = mask | (1 << next); // 다음에 올 수를 추가한 마스크
                        dp[len + 1][next][nextMask] = (dp[len + 1][next][nextMask] + cur) % MOD; // 다음 길이의 마지막에 올 수(next)의 해당하는 마스크에 현재 상태의 개수 추가(어차피 현재 상태의 연장이기 때문)
                    }

                    if(last < 9){
                        int next = last + 1; // 다음에 올 수 (현재 마지막 자리 수 + 1)
                        int nextMask = mask | (1 << next);
                        dp[len + 1][next][nextMask] = (dp[len + 1][next][nextMask] + cur) % MOD;
                    }
                }
            }
        }

        long ans = 0;
        for(int last = 0; last <= 9; last++){
            ans = (ans + dp[N][last][FULL]) % MOD;
        }
        System.out.println(ans);
    }
}

