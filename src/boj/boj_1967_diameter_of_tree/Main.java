package boj.boj_1967_diameter_of_tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Edge{
        int to, cost;
        public Edge(int t, int c){
            to = t;
            cost = c;
        }
    }
    static List<Edge>[] graph;
    static int bestDist, bestNode;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        graph = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 1; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[parent].add(new Edge(child, cost));
            graph[child].add(new Edge(parent, cost));
        }

        bestDist = 0;
        bestNode = 1;
        visited = new boolean[n + 1];
        dfs(1, 0);
        int a = bestNode;

        bestDist = 0;
        bestNode = a;
        visited = new boolean[n + 1];
        dfs(a, 0);
        System.out.println(bestDist);
    }

    static void dfs(int node, int dist){
        visited[node] = true;
        if(dist > bestDist){
            bestDist = dist;
            bestNode = node;
        }
        for(Edge e : graph[node]){
            if(visited[e.to]) continue;

            dfs(e.to, dist + e.cost);
        }
    }
}
