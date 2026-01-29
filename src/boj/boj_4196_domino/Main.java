package boj.boj_4196_domino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] graph, compressedSccGraph;
    static int[] visited, low, sccId, indegree;
    static boolean[] onStack;
    static Stack<Integer> stack;
    static int idx, sccCount;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < tc; t++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            graph = new ArrayList[n + 1];
            for(int i = 1; i <= n; i++){
                graph[i] = new ArrayList<>();
            }
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                graph[s].add(e);
            }
            visited = new int[n + 1];
            low = new int[n + 1];
            idx = 1;
            onStack = new boolean[n + 1];
            sccId = new int[n + 1];
            stack = new Stack<>();
            sccCount = 0;


            for(int i = 1; i <= n; i++){
                if(visited[i] == 0){
                    tarjan(i);
                }
            }

            compressedSccGraph = new ArrayList[sccCount];
            for(int i = 0; i < sccCount; i++){
                compressedSccGraph[i] = new ArrayList<>();
            }
            indegree = new int[sccCount];
            for(int i = 1; i <= n; i++){
                buildCompressedGraph(i);
            }
            int answer = 0;
            for(int i = 0; i < sccCount; i++){
                if(indegree[i] == 0)
                    answer++;
            }
            sb.append(answer);
            if(t < tc - 1){
                sb.append("\n");
            }

        }
        System.out.println(sb);
    }

    private static void buildCompressedGraph(int v){
        for(int w : graph[v]){
            if(sccId[v] != sccId[w]){
                compressedSccGraph[sccId[v]].add(sccId[w]);
                indegree[sccId[w]]++;
            }
        }
    }

    private static void tarjan(int v){
        visited[v] = idx;
        low[v] = idx;
        idx++;
        stack.add(v);
        onStack[v] = true;

        for(int w : graph[v]){
            if(visited[w] == 0){
                tarjan(w);
                low[v] = Math.min(low[v], low[w]);
            } else if(onStack[w]){
                low[v] = Math.min(low[v], visited[w]);
            }
        }

        if(low[v] == visited[v]){
            int w;
            do{
                w = stack.pop();
                sccId[w] = sccCount;
                onStack[w] = false;
            } while(w != v);
            sccCount++;
        }
    }
}
