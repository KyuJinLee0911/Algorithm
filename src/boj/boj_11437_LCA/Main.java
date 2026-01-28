package boj.boj_11437_LCA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int n, LOG;
    static int[][] up;
    static int[] depth;
    static List<Integer>[] child, graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        LOG = 1;
        while((1<<LOG) <= n) LOG++;
        up = new int[LOG][n + 1];
        depth = new int[n + 1];
        child = new ArrayList[n + 1];
        graph = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++){
            child[i] = new ArrayList<>();
            graph[i] = new ArrayList<>();

        }
        StringTokenizer st;
        for(int i = 0; i < n - 1; i++){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            graph[n1].add(n2);
            graph[n2].add(n1);
        }
        setParent(1);
        buildSparseTable();

        StringBuilder sb = new StringBuilder();
        int m = Integer.parseInt(br.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            sb.append(lca(n1, n2));
            if(i < m - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }

    private static void buildSparseTable() {
        for(int k = 1; k < LOG; k++){
            for(int v = 1; v <= n; v++){
                int mid = up[k - 1][v];
                up[k][v] = mid == 0 ? 0 : up[k - 1][mid];
            }
        }
    }

    private static int lca(int n1, int n2){
        if(depth[n1] < depth[n2]){
            int tmp = n1;
            n1 = n2;
            n2 = tmp;
        }

        int diff = depth[n1] - depth[n2];
        for(int k = 0; k < LOG; k++){
            if(((diff >> k) & 1) == 1)
                n1 = up[k][n1];
        }

        if(n1 == n2){
            return n1;
        }

        for(int k = LOG - 1; k >= 0; k--){
            if(up[k][n1] != up[k][n2]){
                n1 = up[k][n1];
                n2 = up[k][n2];
            }
        }

        return up[0][n1];
    }


    private static void setParent(int root){
        boolean[] visited = new boolean[n + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        visited[root] = true;
        depth[root] = 0;
        up[0][root] = 0;

        q.add(root);
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int next : graph[cur]){
                if(visited[next]) continue;
                visited[next] = true;
                depth[next] = depth[cur] + 1;
                up[0][next] = cur;
                q.add(next);
            }
        }

    }
}
