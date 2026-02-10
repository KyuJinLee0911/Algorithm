package boj.boj_11376_passionate_gangho2;

import java.io.*;
import java.util.*;

public class Main {
    static List<Integer>[] adj;
    static int[] pairA, pairB, dist;
    static boolean[] visited;
    static final int INF = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int L = n * 2;
        adj = new ArrayList[L + 1];
        for(int i = 1; i <= L; i++){
            adj[i] = new ArrayList<>();
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            for(int j = 0; j < cnt; j++){
                int work = Integer.parseInt(st.nextToken());
                adj[i].add(work);
                adj[i + n].add(work);
            }
        }
        pairA = new int[L + 1];
        pairB = new int[m + 1];
        dist = new int[L + 1];

        int ans = 0;
        while(bfs(L)){
            for(int i = 1; i <= L; i++){
                if(pairA[i] == 0 && dfs(i)){
                    ans++;
                }
            }
        }

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
    }

    static boolean bfs(int L){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for(int i = 1; i <= L; i++){
            if(pairA[i] == 0){
                dist[i] = 0;
                q.add(i);
            } else {
                dist[i] = INF;
            }
        }

        boolean foundAugPath = false;

        while(!q.isEmpty()){
            int u = q.poll();
            for(int v : adj[u]){
                int u2 = pairB[v];
                if(u2 == 0){
                    foundAugPath = true;
                } else if(dist[u2] == INF){
                    dist[u2] = dist[u] + 1;
                    q.add(u2);
                }
            }
        }
        return foundAugPath;
    }

    static boolean dfs(int i){
        for(int v : adj[i]){
            int u2 = pairB[v];
            if(u2 == 0 || (dist[u2] == dist[i] + 1 && dfs(u2))){
                pairA[i] = v;
                pairB[v] = i;
                return true;
            }
        }
        dist[i] = INF;
        return false;
    }
}
