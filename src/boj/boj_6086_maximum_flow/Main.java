package boj.boj_6086_maximum_flow;

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
    static List<Edge>[] graph;
    static int[] level, it;
    static final int S = 0, E = 25, MAX_SIZE = 26 * 2;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        graph = new ArrayList[MAX_SIZE];
        for(int i = 0; i < MAX_SIZE; i++){
            graph[i] = new ArrayList<>();
        }

        StringTokenizer st;
        int[][] sum = new int[MAX_SIZE][MAX_SIZE];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            char from = st.nextToken().charAt(0);
            char to = st.nextToken().charAt(0);

            int fromIdx = Character.isUpperCase(from) ? from - 'A' : from - 'a' + 26;// 대문자 - 0 ~ 25, 소문자 26~51
            int toIdx = Character.isUpperCase(to) ? to - 'A' : to - 'a' + 26;
            int u = Math.min(fromIdx, toIdx), v = Math.max(fromIdx, toIdx);

            int capacity = Integer.parseInt(st.nextToken());
            sum[u][v] += capacity;
        }

        for(int i = 0; i < MAX_SIZE; i++){
            for(int j = i + 1; j < MAX_SIZE; j++){
                if(sum[i][j] == 0) continue;

                addEdge(i, j, sum[i][j]);
                addEdge(j, i, sum[i][j]);
            }
        }
        level = new int[MAX_SIZE];
        it = new int[MAX_SIZE];

        System.out.println(maxFlow());
    }

    static void addEdge(int u, int v, int cap){
        for(Edge e : graph[u]){
            if(e.to == v){
                Edge revEdge = graph[v].get(e.rev);
                if(revEdge.to == u){
                    e.cap += cap;
                    return;
                }
            }
        }

        Edge fwd = new Edge(v, graph[v].size(), cap);
        Edge bwd = new Edge(u, graph[u].size(), 0);
        graph[u].add(fwd);
        graph[v].add(bwd);
    }

    static boolean bfs(){
        Arrays.fill(level, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(S);
        level[S] = 0;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(Edge e : graph[cur]){
                if(e.cap <= 0) continue;
                if(level[e.to] != -1) continue;

                level[e.to] = level[cur] + 1;
                q.add(e.to);
            }
        }

        return level[E] != -1;
    }

    static int dfs(int u, int t, int pushed){
        if(pushed == 0) return 0;
        if(u == t) return pushed;

        for(int i = it[u]; i < graph[u].size(); i++){
            it[u] = i;
            Edge e = graph[u].get(i);
            if(e.cap <= 0) continue;
            if(level[e.to] != level[u] + 1) continue;

            int tr = dfs(e.to, t, Math.min(pushed, e.cap));
            if(tr == 0) continue;

            e.cap -= tr;
            graph[e.to].get(e.rev).cap += tr;
            return tr;
        }

        return 0;
    }

    static int maxFlow(){
        int flow = 0;
        while(bfs()){
            Arrays.fill(it, 0);
            while(true){
                int pushed = dfs(S, E, Integer.MAX_VALUE);
                if(pushed == 0) break;
                flow += pushed;
            }
        }
        return flow;
    }
}
