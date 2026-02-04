package boj.boj_11281_2SAT4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] graph;
    static int n, m, idx, sccCount;
    static Stack<Integer> stack;
    static int[] visited, low, sccIds;
    static boolean[] onStack;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        stack = new Stack<>();
        visited = new int[2 * n + 1];
        low = new int[2 * n + 1];
        sccIds = new int[2 * n + 1];
        for(int i = 1; i <= 2 * n; i++){
            visited[i] = -1;
            low[i] = -1;
            sccIds[i] = -1;
        }
        onStack = new boolean[2 * n + 1];
        idx = 0;
        sccCount = 0;

        graph = new ArrayList[2 * n + 1];// 1~n = 양수, n + 1 ~ 2n = 음수
        for(int i = 1; i <= 2*n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            int fIdx = first > 0 ? first : Math.abs(first) + n;
            int sIdx = second > 0 ? second : Math.abs(second) + n;
            int reverseFirst = fIdx <= n ? fIdx + n : fIdx - n;
            int reverseSecond = sIdx <= n ? sIdx + n : sIdx - n;

            graph[reverseFirst].add(sIdx);
            graph[reverseSecond].add(fIdx);
        }

        for(int i = 1; i <= 2 * n; i++){
            if(visited[i] == -1){
                tarjan(i);
            }
        }

        for(int i = 1; i <= n; i++){
            if(sccIds[i] == sccIds[i + n]){
                System.out.println(0);
                return;
            }
        }

        int[] flags = new int[n + 1];
        StringBuilder sb = new StringBuilder();
        sb.append(1).append("\n");

        for(int i = n; i > 0; i--){
            flags[i] = sccIds[i] < sccIds[i + n] ? 1 : 0;
        }

        for(int i = 1; i <= n; i++){
            sb.append(flags[i]);
            if(i < n)
                sb.append(" ");
        }

        System.out.println(sb);
    }

    private static void tarjan(int v){
        visited[v] = idx;
        low[v] = idx;
        idx++;
        stack.add(v);
        onStack[v] = true;

        for(int w : graph[v]){
            if(visited[w] == -1){
                tarjan(w);
                low[v] = Math.min(low[v], low[w]);
            } else if(onStack[w]) {
                low[v] = Math.min(low[v], visited[w]);
            }
        }

        if(low[v] == visited[v]){
            int w;
            do{
                w = stack.pop();
                onStack[w] = false;
                sccIds[w] = sccCount;
            }while(v != w);
            sccCount++;
        }
    }
}
