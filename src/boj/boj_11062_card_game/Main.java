package boj.boj_11062_card_game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] cards;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {
            int n = Integer.parseInt(br.readLine());
            cards = new int[n];
            st = new StringTokenizer(br.readLine());
            int sum = 0;
            for (int i = 0; i < n; i++) {
                cards[i] = Integer.parseInt(st.nextToken());
                sum += cards[i];
            }


            int idx = 0;
            int[][] dp = new int[n][n];
            for (int i = 0; i < n; i++) {
                dp[i][i] = cards[i];
            }


            for (int len = 2; len <= n; len++) {
                for (int l = 0; l + len - 1 < n; l++) {
                    int r = l + len - 1;
                    dp[l][r] = Math.max(cards[l] - dp[l + 1][r], cards[r] - dp[l][r - 1]);
                }
            }
            int ans = (sum + dp[0][n - 1]) / 2;

            sb.append(ans).append("\n");
        }

        System.out.println(sb.toString().trim());
    }
}
// 내가 더 높은 점수를 낸다 -> 다음번에 상대가 선택할 카드가 작도록 만들고, 내가 더 큰 카드를 먹게 만든다
// 양 끝 두 개의 카드에 대해서, 내가 이 카드를 먹었을 때 +2턴 뒤에 내가 더 큰 카드를 먹을 수 있는가
