package boj.boj_1086_sungwon_park;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] numbers = new String[n];
        int maxLen = 50;
        int[] pow10 = new int[maxLen + 1];
        int[] len = new int[n];
        int[] mod = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = br.readLine();
            len[i] = numbers[i].length();
        }
        int k = Integer.parseInt(br.readLine());
        pow10[0] = 1 % k;
        for (int i = 1; i <= maxLen; i++) {
            pow10[i] = (pow10[i - 1] * 10) % k;
        }

        for (int i = 0; i < n; i++) {
            mod[i] = getMod(numbers[i], k);
        }

        int size = 1 << n;
        long[][] dp = new long[size][k];
        dp[0][0] = 1;
        for (int mask = 0; mask < size; mask++) {
            for (int rem = 0; rem < k; rem++) {
                if (dp[mask][rem] == 0) continue;

                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) continue;

                    int nextMask = mask | (1 << i);
                    int nextRem = (rem * pow10[len[i]] + mod[i]) % k;
                    dp[nextMask][nextRem] += dp[mask][rem];
                }
            }
        }

        long dominator = factorial(n);
        long numerator = dp[size - 1][0];
        long g = gcd(dp[size - 1][0], dominator);


        System.out.println((numerator / g) + "/" + (dominator / g));
    }

    static int getMod(String s, int k) {
        int rem = 0;
        for (char c : s.toCharArray()) {
            rem = (rem * 10 + (c - '0')) % k;
        }

        return rem;
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long t = (a % b);
            a = b;
            b = t;
        }

        return a;
    }

    static long factorial(int n) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }
}
