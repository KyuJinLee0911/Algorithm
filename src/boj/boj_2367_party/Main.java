package boj.boj_2367_party;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
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
        int n = Integer.parseInt(st.nextToken()); // 사람 수
        int k = Integer.parseInt(st.nextToken()); // 한 사람이 가져올 수 있는 음식의 개수
        int d = Integer.parseInt(st.nextToken()); // 음식의 가짓수
        int s = 0;
        int basicPerson = 1;
        int basicFood = basicPerson + n;
        int t = basicFood + d;
        graph = new ArrayList[t + 1];
        for(int i = 0; i <= t; i++){
            graph[i] = new ArrayList<>();
        }
        level = new int[t + 1];
        it = new int[t + 1];

        st = new StringTokenizer(br.readLine());

        for(int i = 1; i <= n; i++){
            addEdge(s, i, k);
        }

        for(int i = 0; i < d; i++){
            int maxFood = Integer.parseInt(st.nextToken());
            addEdge(basicFood + i, t, maxFood);
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            int z = Integer.parseInt(st.nextToken());
            for(int j = 0; j < z; j++){
                int food = Integer.parseInt(st.nextToken());
                addEdge(i, basicFood + food - 1, 1);
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
        q.add(s);
        level[s] = 0;
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
        for(int i = it[u]; i < graph[u].size(); i++, it[u] = i) {
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

    static int maxFlow(int s, int t){
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
