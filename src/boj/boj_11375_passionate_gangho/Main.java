package boj.boj_11375_passionate_gangho;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] adj;
    static int[] match;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        adj = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++){
            adj[i] = new ArrayList<>();
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            for(int j = 0; j < cnt; j++){
                int work = Integer.parseInt(st.nextToken());
                adj[i].add(work);
            }
        }

        match = new int[m + 1];
        Arrays.fill(match, -1);
        int ans = 0;
        for(int i = 1; i <= n; i++){
            visited = new boolean[m + 1];
            if(dfs(i)) ans++;
        }

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
    }

    static boolean dfs(int i){
        for(int v : adj[i]){
            if(visited[v]) continue;
            visited[v] = true;

            if(match[v] == -1 || dfs(match[v])){
                match[v] = i;
                return true;
            }
        }
        return false;
    }
}
