package boj.boj_1106_hotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[] prices = new int[N];
        int[] peoples = new int[N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            prices[i] = Integer.parseInt(st.nextToken());
            peoples[i] = Integer.parseInt(st.nextToken());
        }
        int[] dp = new int[C + 101];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 0; i < N; i++){
            int price = prices[i];
            int people = peoples[i];
            for(int j = people; j < C + 101; j++) {
                if(dp[j - people] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - people] + price);
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for(int i = C; i < dp.length; i++){
            minCost = Math.min(minCost, dp[i]);
        }

        System.out.println(minCost);
    }
}

// C보다는 커야 함
// 현재 코드로는 C보다 큰 값이 없기 때문에 안된다
// C보다 작은 최대값을 찾고, 남은 인원수로 다시 가격을 계산해야 하나?

