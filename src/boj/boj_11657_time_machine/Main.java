package boj.boj_11657_time_machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
        int from, to, cost;
        public Edge(int from, int to, int cost){
            this.from = from;
            this.to  = to;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Edge> edges = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges.add(new Edge(from, to, cost));
            graph[from].add(to);
        }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        for(int i = 0; i < n - 1; i++){
            boolean updated = false;
            for(Edge e : edges){
                if(dist[e.from] == Long.MAX_VALUE) continue;
                long newDist = dist[e.from] + e.cost;
                if(dist[e.to] > newDist){
                    dist[e.to] = newDist;
                    updated = true;
                }
            }

            if(!updated) break;
        }


        boolean[] isCycleInfluence = new boolean[n + 1];
        for(Edge e : edges){
            if(dist[e.from] == Long.MAX_VALUE) continue;
            long nd = dist[e.from] + e.cost;
            if(dist[e.to] > nd){
                isCycleInfluence[e.to] = true;
                System.out.println(-1);
                return;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 2; i <= n; i++){
            if(dist[i] == Long.MAX_VALUE){
                sb.append(-1).append("\n");
            } else {
                sb.append(dist[i]).append("\n");
            }
        }

        System.out.println(sb);
    }
}
