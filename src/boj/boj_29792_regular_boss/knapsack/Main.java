package boj.boj_29792_regular_boss.knapsack;

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
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        long[] dps = new long[n];
        for(int i = 0; i < n; i++){
            dps[i] = Long.parseLong(br.readLine());
        }
        long[] bossHp = new long[k];
        int[] meso = new int[k];
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            bossHp[i] = Long.parseLong(st.nextToken());
            meso[i] = Integer.parseInt(st.nextToken());
        }
        long[] mesoPerChar = new long[n];
        for(int i = 0; i < n; i++){
            long damagePerSec = dps[i];
            long[] timeTaken = new long[k];
            for(int j = 0; j < k; j++){
                timeTaken[j] = (damagePerSec + bossHp[j] - 1) / damagePerSec;
            }
            long best = getBest(k, timeTaken, meso);
            mesoPerChar[i] = best;
        }
        Arrays.sort(mesoPerChar);
        long sum = 0;
        for(int i = n - 1; i > n - m - 1; i--){
            sum += mesoPerChar[i];
        }
        System.out.println(sum);
    }

    private static long getBest(int dps, long[] bossHp, int[] meso) {
        long best = 0;
        long[] dp = new long[901];
        for(int i = 0; i < bossHp.length; i++){
            long t = (bossHp[i] + dps - 1) / dps;
            if(t > 900) continue;

            int w = (int) t;
            for(int time = 900; time >= w; time--){
                dp[time] = Math.max(dp[time], dp[time - w] + meso[i]);
            }
        }
        for(int time = 0; time <= 900; time++){
            best = Math.max(best, dp[time]);
        }
        return best;
    }
}
