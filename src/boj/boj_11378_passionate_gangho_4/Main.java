package boj.boj_11378_passionate_gangho_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
        int to, rev, cap;
        public Edge(int t, int r, int c){
            to = t;
            rev = r;
            cap = c;
        }
    }
    static List<Edge>[] adj;
    static int[] level, it;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int s = 1;
        int x = 2;
        int basePeople = 3;
        int baseJob = basePeople + n;
        int t = baseJob + m;
        int v = t;
        level = new int[v + 1];
        it = new int[v + 1];

        adj = new ArrayList[v + 1];
        for(int i = 1; i <= v; i++){
            adj[i] = new ArrayList<>();
        }

        for(int i = 0; i < n; i++){
            int people = basePeople + i;
            addEdge(s, people, 1);
        }

        addEdge(s, x, k);
        for(int i = 0; i < n; i++){
            int people = basePeople + i;
            addEdge(x, people, k);
        }

        for(int i = 0; i < m; i++){
            int job = baseJob + i;
            addEdge(job, t, 1);
        }


        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int people = basePeople + i;
            int jobCount = Integer.parseInt(st.nextToken());
            for(int j = 0; j < jobCount; j++){
                int job = Integer.parseInt(st.nextToken());
                int jobIdx = job + baseJob - 1;
                addEdge(people, jobIdx, 1);
            }
        }

        int answer = maxFlow(s, t);
        System.out.println(answer);

    }

    static void addEdge(int u, int v, int cap){
        Edge fwd = new Edge(v, adj[v].size(), cap);
        Edge bwd = new Edge(u, adj[u].size(), 0);
        adj[u].add(fwd);
        adj[v].add(bwd);
    }

    static boolean bfs(int s, int t){
        Arrays.fill(level, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        level[s] = 0;
        q.add(s);

        while(!q.isEmpty()){
            int u = q.poll();
            for(Edge e : adj[u]){
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

        for(int i = it[u]; i < adj[u].size(); i++, it[u] = i){
            Edge e = adj[u].get(i);
            if(e.cap == 0) continue;
            if(level[e.to] != level[u] + 1) continue;

            int tr = dfs(e.to, t, Math.min(pushed, e.cap));
            if(tr == 0) continue;

            e.cap -= tr;
            adj[e.to].get(e.rev).cap += tr;
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
