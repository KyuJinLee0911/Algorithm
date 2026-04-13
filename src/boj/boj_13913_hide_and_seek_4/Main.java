package boj.boj_13913_hide_and_seek_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        if (end < start) {
            sb.append(start - end).append("\n");
            for (int i = start; i >= end; i--) {
                sb.append(i).append(" ");
            }

            System.out.println(sb.toString().trim());
            return;
        }

        int[][] dp = new int[end * 2 + 1][2];
        for (int i = 1; i <= end * 2; i++) {
            dp[i][0] = 100_001;
        }
        dp[start][0] = 0;
        dp[start][1] = -1;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            int cur = q.poll();

            if (cur < 0 || cur > end * 2) continue;

            int backward = cur - 1;
            int forward = cur + 1;
            int teleport = cur * 2;
            if (backward > 0) {
                if (dp[cur][0] + 1 < dp[backward][0]) {
                    dp[backward][0] = dp[cur][0] + 1;
                    dp[backward][1] = cur;
                    q.add(backward);
                }

            }

            if (forward <= end) {
                if (dp[cur][0] + 1 < dp[forward][0]) {
                    dp[forward][0] = dp[cur][0] + 1;
                    dp[forward][1] = cur;
                    q.add(forward);
                }
            }

            if (teleport <= end * 2) {
                if (dp[cur][0] + 1 < dp[teleport][0]) {
                    dp[teleport][0] = dp[cur][0] + 1;
                    dp[teleport][1] = cur;
                    q.add(teleport);
                }
            }
        }


        int count = dp[end][0];
        sb.append(count).append("\n");
        int cur = end;
        int[] route = new int[count + 1];
        route[count--] = end;
        while (count >= 0) {
            route[count--] = dp[cur][1];
            cur = dp[cur][1];
        }

        for (int i = 0; i <= dp[end][0]; i++) {
            sb.append(route[i]).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}
