package boj.boj_1738_ally;

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
        Arrays.fill(dist, Long.MIN_VALUE);
        int start = 1;
        int end = n;
        dist[start] = 0;
        List<Integer>[] traceBack = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) traceBack[i] = new ArrayList<>();
        for(int i = 0; i < n - 1; i++){
            boolean updated = false;
            for(Edge e : edges){
                if(dist[e.from] == Long.MIN_VALUE) continue;

                long nd = dist[e.from] + e.cost;
                if(dist[e.to] < nd){
                    dist[e.to] = nd;
                    traceBack[e.to].clear();
                    traceBack[e.to].add(e.from);
                    updated = true;
                }
            }

            if(!updated) break;
        }

        if(dist[end] == Long.MIN_VALUE){
            System.out.println(-1);
            return;
        }

        boolean[] isCycleInfluenced = new boolean[n + 1];
        for(Edge e : edges){
            long nd = dist[e.from] + e.cost;
            if(dist[e.to] < nd){
                isCycleInfluenced[e.to] = true;
            }
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];
        for(int i = 1; i <= n; i++){
            if(isCycleInfluenced[i]){
                q.add(i);
                visited[i] = true;
            }
        }
        while(!q.isEmpty()){
            int cur = q.poll();
            if(cur == end) {
                System.out.println(-1);
                return;
            }

            for(int i : graph[cur]){
                if(visited[i]) continue;
                visited[i] = true;
                q.add(i);
            }
        }

        List<Integer> route = new ArrayList<>();
        Arrays.fill(visited, false);
        q.clear();
        q.add(end);
        visited[end] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            route.add(cur);
            if(cur == start) break;

            for(int i : traceBack[cur]){
                if(visited[i]) continue;
                visited[i] = true;
                q.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = route.size() - 1; i >= 0; i--){
            sb.append(route.get(i));
            if(i > 0){
                sb.append(" ");
            }
        }

        System.out.println(sb);
    }
}
