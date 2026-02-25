package boj.boj_3977_soccer_tactics.kosaraju;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Integer>[] graph, revGraph;
    static Stack<Integer> stack;
    static boolean[] visited;
    static int[] sccIds, indegree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < tc; t++) {
            String line;
            do {
                line = br.readLine();
            } while (line != null && line.isEmpty());
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            graph = new ArrayList[n];
            revGraph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
                revGraph[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());
                graph[v1].add(v2);
                revGraph[v2].add(v1);
            }
            stack = new Stack<>();
            visited = new boolean[n];
            sccIds = new int[n];
            for(int i = 0; i < n; i++){
                if(!visited[i])
                    dfs1(i);
            }
            Arrays.fill(visited, false);
            int sccCnt = 0;
            while (!stack.isEmpty()){
                int v = stack.pop();
                if(visited[v]) continue;

                dfs2(v, sccCnt);
                sccCnt++;
            }

            indegree = new int[sccCnt];
            for(int i = 0; i < n; i++){
                buildCompressed(i);
            }
            int cnt = 0;
            int entries = -1;
            for(int i = 0; i < sccCnt; i++){
                if(indegree[i] == 0) {
                    entries = i;
                    cnt++;
                }
            }
            if(cnt != 1) {
                sb.append("Confused").append("\n\n");
                continue;
            }

            for(int i = 0; i < n; i++){
                int id = sccIds[i];
                if(id == entries) {
                    sb.append(i).append("\n");
                }
            }

            sb.append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    static void dfs1(int v){
        visited[v] = true;
        for(int w : graph[v]){
            if(!visited[w]) dfs1(w);
        }
        stack.add(v);
    }

    static void dfs2(int v, int id){
        visited[v] = true;
        sccIds[v] = id;
        for(int w : revGraph[v]){
            if(!visited[w]) dfs2(w, id);
        }
    }

    static void buildCompressed(int v){
        for(int w : graph[v]){
            if(sccIds[v] != sccIds[w]){
                indegree[sccIds[w]]++;
            }
        }
    }
}
