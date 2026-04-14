package boj.boj_11780_floyd_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int INF = 100_000_001;
    static int[][] graph, prev;
    static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        graph = new int[n + 1][n + 1];
        prev = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) continue;

                graph[i][j] = INF;
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (graph[f][t] > c) {
                graph[f][t] = c;
                prev[f][t] = t;
            }
        }


        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (graph[i][k] == INF || graph[k][j] == INF) continue;
                    if (graph[i][j] > graph[i][k] + graph[k][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                        prev[i][j] = prev[i][k];

                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (graph[i][j] == INF) sb.append(0).append(" ");
                else sb.append(graph[i][j]).append(" ");
            }
            sb.append("\n");
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j || graph[i][j] == INF) {
                    sb.append(0).append("\n");
                    continue;
                }

                List<Integer> route = new ArrayList<>();
                int cur = i;
                route.add(cur);

                while (cur != j) {
                    route.add(prev[cur][j]);
                    cur = prev[cur][j];
                }

                sb.append(route.size()).append(" ");
                for (int k = 0; k < route.size(); k++) {
                    sb.append(route.get(k)).append(" ");
                }

                sb.append("\n");
            }
        }

        System.out.println(sb.toString().trim());

    }
}
