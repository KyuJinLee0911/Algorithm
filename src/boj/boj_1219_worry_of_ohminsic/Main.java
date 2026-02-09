package boj.boj_1219_worry_of_ohminsic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
        int from, to, dist;
        public Edge(int from, int to, int dist){
            this.from = from;
            this.to = to;
            this.dist = dist;
        }
    }
    static final long NEG_INF = Long.MIN_VALUE / 4;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Edge> edges = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            edges.add(new Edge(from, to, price * -1));
            graph[from].add(to);
        }
        int[] earn = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            earn[i] = Integer.parseInt(st.nextToken());
        }
        int startMoney = 0;

        long[] dist = new long[n];
        Arrays.fill(dist, NEG_INF);
        dist[start] = startMoney + earn[start];

        for(int i = 0; i < n; i++){
            boolean updated = false;
            for(Edge e : edges){
                if(dist[e.from] == NEG_INF) continue;
                long nd = dist[e.from] + e.dist + earn[e.to];
                if(dist[e.to] < nd){
                    dist[e.to] = nd;
                    updated = true;
                }
            }

            if(!updated) break;
        }

        if(dist[end] == NEG_INF){
            System.out.println("gg");
            return;
        }

        boolean[] inCycleInfluence = new boolean[n];
        for(Edge e : edges){
            if(dist[e.from] == NEG_INF) continue;
            long nd = dist[e.from] + e.dist + earn[e.to];
            if(dist[e.to] < nd){
                inCycleInfluence[e.to] = true;
            }
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        for(int i = 0; i < n; i++){
            if(inCycleInfluence[i]){
                q.add(i);
                visited[i] = true;
            }
        }

        while(!q.isEmpty()){
            int cur = q.poll();
            if(cur == end){
                System.out.println("Gee");
                return;
            }
            for(int next : graph[cur]){
                if(visited[next]) continue;

                visited[next] = true;
                q.add(next);
            }
        }

        System.out.println(dist[end]);
    }
}
