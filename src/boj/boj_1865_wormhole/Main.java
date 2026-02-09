package boj.boj_1865_wormhole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Edge{
        int from, to, dist;
        public Edge(int from, int to, int dist){
            this.from = from;
            this.to = to;
            this.dist = dist;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < tc; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            List<Edge> edges = new ArrayList<>();

            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int dist = Integer.parseInt(st.nextToken());
                edges.add(new Edge(a, b, dist));
                edges.add(new Edge(b, a, dist));
            }

            for(int i = 0; i < w; i++){
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int dist = Integer.parseInt(st.nextToken());
                edges.add(new Edge(from, to, dist * -1));
            }

            int superSource = 0;
            long[] dist = new long[n + 1];
            Arrays.fill(dist, 0L);
            boolean hasNegCycle = false;

            for(int i = 1; i <= n; i++){
                boolean updated = false;
                for(Edge e : edges){
                    long newDist = dist[e.from] + e.dist;
                    if(dist[e.to] > newDist) {
                        dist[e.to] = newDist;
                        updated = true;
                        if(i == n) hasNegCycle = true;
                    }
                }

                if(!updated) break;
            }
            sb.append(hasNegCycle ? "YES" : "NO");
            if(t < tc - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
}
