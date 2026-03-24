package boj.boj_2637_assembling_toys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        int[] indegree = new int[n + 1];
        int[][] graph = new int[n + 1][n + 1];
        StringTokenizer st;
        boolean[] isBasic = new boolean[n + 1];
        Arrays.fill(isBasic, true);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int middle = Integer.parseInt(st.nextToken());
            int base = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());
            graph[middle][base] += count; // middle을 만들려면 base가 count개 필요하다
            isBasic[middle] = false;
            indegree[base]++;
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) q.add(i);
        }
        graph[n][n] = 1;

        List<Integer> sequence = new ArrayList<>();
        while (!q.isEmpty()) {
            int cur = q.poll();
            sequence.add(cur);
            for (int i = 1; i <= n; i++) {
                if (graph[cur][i] == 0) continue;
                if (cur == i) continue;
                graph[i][i] += graph[cur][i] * graph[cur][cur];

                indegree[i]--;
                if (indegree[i] == 0) q.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (!isBasic[i]) continue;

            sb.append(i).append(" ").append(graph[i][i]).append("\n");
        }

        System.out.println(sb.toString().trim());

    }
}
