package boj.boj_25953_temporal_graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static final int INF = 1_000_000_001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        List<Edge>[] edgesPerTime = new ArrayList[t + 1];
        for (int time = 1; time <= t; time++) {
            edgesPerTime[time] = new ArrayList<>();
        }

        for (int time = 1; time <= t; time++) {
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                edgesPerTime[time].add(new Edge(from, to, weight));
            }
        }

        int[][] dp = new int[t + 1][n];
        for (int time = 1; time <= t; time++) {
            Arrays.fill(dp[time], INF);
        }
        dp[1][s] = 0;
        for (Edge edge : edgesPerTime[1]) {
            if (edge.from == s) {
                dp[1][edge.to] = edge.weight;
            } else if (edge.to == s) {
                dp[1][edge.from] = edge.weight;
            }
        }

        for (int time = 2; time <= t; time++) {
            for (int i = 0; i < n; i++) {
                dp[time][i] = dp[time - 1][i];
            }

            for (Edge edge : edgesPerTime[time]) {
                int from = edge.from;
                int to = edge.to;
                int weight = edge.weight;

                dp[time][to] = Math.min(dp[time][to], dp[time - 1][from] + weight);
                dp[time][from] = Math.min(dp[time][from], dp[time - 1][to] + weight);
            }
        }
        int ans = dp[t][e] == INF ? -1 : dp[t][e];
        System.out.println(ans);
    }

    static class Edge {
        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
