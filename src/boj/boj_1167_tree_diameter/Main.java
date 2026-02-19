package boj.boj_1167_tree_diameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Edge{
        int to, dist;
        public Edge(int t, int d){
            to = t;
            dist = d;
        }
    }
    static List<Edge>[] graph;
    static boolean[] visited;
    static int bestNode;
    static long bestDist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        graph = new ArrayList[n + 1];
        for(int i = 1; i<= n; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            String str;
            while(!(str = st.nextToken()).equals("-1")){
                int child = Integer.parseInt(str);
                int dist = Integer.parseInt(st.nextToken());
                graph[id].add(new Edge(child, dist));
            }
        }
        bestDist = 0L;
        bestNode = 1;
        visited = new boolean[n + 1];
        dfs(1, 0);
        int a = bestNode;

        bestDist = 0L;
        bestNode = a;
        visited = new boolean[n + 1];
        dfs(a, 0);
        System.out.println(bestDist);

    }

    static void dfs(int node, long dist){
        visited[node] = true;
        if(dist > bestDist){
            bestDist = dist;
            bestNode = node;
        }

        for(Edge e : graph[node]){
            if(visited[e.to]) continue;

            dfs(e.to, dist + e.dist);
        }
    }

}
