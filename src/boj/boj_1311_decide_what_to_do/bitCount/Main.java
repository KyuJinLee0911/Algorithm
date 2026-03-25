package boj.boj_1311_decide_what_to_do.bitCount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int INF = 1_000_000_001;

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
        int[] dp = new int[maskSize];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int mask = 0; mask < maskSize; mask++) {
            int person = Integer.bitCount(mask);
            if (person == n) continue;

            int available = ((1 << n) - 1) ^ mask;

            while (available != 0) {
                int bit = available & -available;
                int job = Integer.numberOfTrailingZeros(bit);

                int nextMask = mask | bit;
                dp[nextMask] = Math.min(dp[nextMask], dp[mask] + price[person][job]);

                available -= bit;
            }
        }


        System.out.println(dp[(1 << n) - 1]);
    }
}
