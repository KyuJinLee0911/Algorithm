package boj.boj_11406_buying_books_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge {
        int to, rev, cap;

        public Edge(int to, int rev, int cap) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
        }
    }
    static List<Edge>[] graph;
    static int[] level, it;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = 0;
        int basicPerson = 1;
        int basicBookstore = basicPerson + n;
        int t = basicBookstore + m;
        graph = new ArrayList[t + 1];
        level = new int[t + 1];
        it = new int[t + 1];
        for(int i = 0; i <= t; i++){
            graph[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        for(int i = basicPerson; i <= n; i++){
            int a = Integer.parseInt(st.nextToken());
            addEdge(0, i, a);
        }

        st = new StringTokenizer(br.readLine());
        for(int i = basicBookstore; i < t; i++){
            int b = Integer.parseInt(st.nextToken());
            addEdge(i, t, b);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++){
                int c = Integer.parseInt(st.nextToken());
                addEdge(basicPerson + j, basicBookstore + i, c);
            }
        }

        System.out.println(maxFlow(0, t));
    }

    static void addEdge(int u, int v, int cap){
        Edge fwd = new Edge(v, graph[v].size(), cap);
        Edge bwd = new Edge(u, graph[u].size(), 0);
        graph[u].add(fwd);
        graph[v].add(bwd);
    }

    static boolean bfs(int s, int t){
        Arrays.fill(level, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        level[s] = 0;
        q.add(s);
        while(!q.isEmpty()){
            int u = q.poll();
            for(Edge e : graph[u]){
                if(e.cap <= 0) continue;
                if(level[e.to] != -1) continue;
                level[e.to] = level[u] + 1;
                q.add(e.to);
            }
        }

        return level[t] != -1;
    }

    static int dfs(int u, int t, int pushed){
        if(pushed == 0) return 0;
        if(u == t) return pushed;
        for (int i = it[u]; i < graph[u].size(); i++, it[u] = i) {
            Edge e = graph[u].get(i);
            if(e.cap == 0) continue;
            if(level[e.to] != level[u] + 1) continue;

            int tr = dfs(e.to, t, Math.min(e.cap, pushed));
            if(tr == 0) continue;

            e.cap -= tr;
            graph[e.to].get(e.rev).cap += tr;
            return tr;
        }
        return 0;
    }

    static int maxFlow(int s, int  t){
        int flow = 0;
        while(bfs(s, t)){
            Arrays.fill(it, 0);
            while(true){
                int pushed = dfs(s, t, Integer.MAX_VALUE);
                if(pushed == 0) break;
                flow += pushed;
            }
        }

        return flow;
    }
}
