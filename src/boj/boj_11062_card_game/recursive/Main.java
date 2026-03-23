package boj.boj_11062_card_game.recursive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] cards;
    static int[][] dp;
    static boolean[][] visited;

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
            dp = new int[n][n];
            visited = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                dp[i][i] = cards[i];
                visited[i][i] = true;
            }


            int ans = (sum + recur(0, n - 1)) / 2;

            sb.append(ans).append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    static int recur(int l, int r) {
        if (l == r) return cards[l];

        if (visited[l][r]) return dp[l][r];
        visited[l][r] = true;

        int pickLeft = cards[l] - recur(l + 1, r);
        int pickRight = cards[r] - recur(l, r - 1);

        dp[l][r] = Math.max(pickLeft, pickRight);

        return dp[l][r];
    }
}
// 내가 더 높은 점수를 낸다 -> 다음번에 상대가 선택할 카드가 작도록 만들고, 내가 더 큰 카드를 먹게 만든다
// 양 끝 두 개의 카드에 대해서, 내가 이 카드를 먹었을 때 +2턴 뒤에 내가 더 큰 카드를 먹을 수 있는가
