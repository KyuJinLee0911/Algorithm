package boj.boj_3977_soccer_tactics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Integer>[] graph;
    static Stack<Integer> stack;
    static int[] dfn, low, sccIds, indegree;
    static boolean[] onStack;
    static int index, curId;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int t = 0; t < tc; t++){
            String line;
            do {
                line = br.readLine();
            } while(line != null && line.isEmpty());
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            graph = new ArrayList[n];
            for(int i = 0; i < n; i++){
                graph[i] = new ArrayList<>();
            }
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                graph[from].add(to);
            }

            stack = new Stack<>();
            dfn = new int[n];
            low = new int[n];
            onStack = new boolean[n];
            sccIds = new int[n];
            curId = 0;
            for(int i = 0; i < n; i++){
                dfn[i] = -1;
                low[i] = -1;
            }
            index = 0;
            for(int i = 0; i < n; i++){
                if(dfn[i] == -1){
                    tarjan(i);
                }
            }

            indegree = new int[curId];
            for(int i = 0; i < n; i++){
                buildCompressedGraph(i);
            }
            int cnt = 0;
            int entries = -1;
            for(int i = 0; i < curId; i++){
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

    static void tarjan(int v){
        dfn[v] = index;
        low[v] = index;
        index++;
        stack.add(v);
        onStack[v] = true;

        for(int w : graph[v]){
            if(dfn[w] == -1){
                tarjan(w);
                low[v] = Math.min(low[v], low[w]);
            } else if(onStack[w]){
                low[v] = Math.min(low[v], dfn[w]);
            }
        }

        if(low[v] == dfn[v]){
            int w;
            do{
                w = stack.pop();
                sccIds[w] = curId;
                onStack[w] = false;
            } while(w != v);

            curId++;
        }
    }

    static void buildCompressedGraph(int v){
        for(int w : graph[v]){
            if(sccIds[w] != sccIds[v]){
                indegree[sccIds[w]]++;
            }
        }
    }
}
