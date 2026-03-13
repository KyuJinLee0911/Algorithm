package boj.boj_1520_downhill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int m, n;
    static int[][] map, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        map = new int[m][n];
        dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        System.out.println(dfs(0, 0));
    }

    static int dfs(int y, int x) {
        if (y == m - 1 && x == n - 1) {
            return 1;
        }

        if (dp[y][x] != -1) {
            return dp[y][x];
        }

        dp[y][x] = 0;

        for (int d = 0; d < 4; d++) {
            int ny = y + delta[d][0];
            int nx = x + delta[d][1];

            if (ny < 0 || ny >= m || nx < 0 || nx >= n) continue;
            if (map[ny][nx] >= map[y][x]) continue;

            dp[y][x] += dfs(ny, nx);
        }

        return dp[y][x];
    }
}