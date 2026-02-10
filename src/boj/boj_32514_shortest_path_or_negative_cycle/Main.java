package boj.boj_32514_shortest_path_or_negative_cycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
        int from, to, cost;
        public Edge(int from, int to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
    static final long MAX_INF = Long.MAX_VALUE / 4;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        List<Edge> edges = new ArrayList<>();
        int[] parent = new int[n];

        for(int i = 0; i < n; i++) {
            parent[i] = -1;
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edges.add(new Edge(from, to, cost));
        }

        long[] dist = new long[n];
        Arrays.fill(dist, MAX_INF);
        dist[s] = 0;
        for(int i = 0; i < n - 1; i++){
            boolean updated = false;
            for(Edge e : edges){
                if(dist[e.from] == MAX_INF) continue;
                long nd = dist[e.from] + e.cost;
                if(dist[e.to] > nd){
                    dist[e.to] = nd;
                    parent[e.to] = e.from;
                    updated = true;
                }
            }
            if(!updated) break;
        }
        int x = -1;
        for(Edge e : edges){
            if(dist[e.from] == MAX_INF) continue;;
            long nd = dist[e.from] + e.cost;
            if(dist[e.to] > nd){
                x = e.to;
                dist[e.to] = nd;
                parent[e.to] = e.from;
            }
        }

        StringBuilder sb = new StringBuilder();
        if(x != -1){
            sb.append("CYCLE").append("\n");
            for(int i = 0; i < n; i++){
                x = parent[x];
            }
            ArrayList<Integer> negCycle = new ArrayList<>();
            int cur = x;
            do{
                negCycle.add(cur);
                cur = parent[cur];
            } while(cur != x);
            negCycle.add(x);
            Collections.reverse(negCycle);
            int count = negCycle.size() - 1;
            sb.append(count).append("\n");
            for (int i = 0; i <= count; i++){
                sb.append(negCycle.get(i));
                if(i < count){
                    sb.append(" ");
                }
            }
        } else {
            sb.append("PATH").append("\n");
            for(int i = 0; i < n; i++){
                sb.append(dist[i]);
                if(i < n-1){
                   sb.append(" ");
                }
            }
        }

        System.out.println(sb);
    }
}
