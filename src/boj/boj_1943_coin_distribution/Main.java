package boj.boj_1943_coin_distribution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < 3; tc++) {
            int n = Integer.parseInt(br.readLine());
            int sum = 0;
            List<Integer> coins = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int price = Integer.parseInt(st.nextToken());
                int count = Integer.parseInt(st.nextToken());
                sum += price * count;
                
                int k = 1;
                while (count > 0) {
                    int take = Math.min(k, count);
                    coins.add(price * take);
                    count -= take;
                    k <<= 1;
                }

            }
            if (sum % 2 == 1) {
                sb.append(0).append("\n");
                continue;
            }
            int half = sum / 2;
            boolean[] dp = new boolean[half + 1];
            dp[0] = true;
            for (int coin : coins) {
                for (int price = half; price >= coin; price--) {
                    if (dp[price - coin]) dp[price] = true;
                }
            }

            int ans = dp[half] ? 1 : 0;
            sb.append(ans).append("\n");
        }

        System.out.println(sb.toString().trim());
    }
}
