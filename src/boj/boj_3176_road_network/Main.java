package boj.boj_3176_road_network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static List<int[]>[] graph;
    static int n, LOG;
    static int[] depth;
    static int[][] up, min, max;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        LOG = 1;
        while((1 << LOG) <= n)
            LOG++;
        up = new int[LOG][n + 1];
        min = new int[LOG][n + 1];
        max = new int[LOG][n + 1];
        graph = new ArrayList[n + 1];
        depth = new int[n + 1];
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        StringTokenizer st;
        for(int i = 0; i < n - 1; i++){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());
            graph[n1].add(new int[] {n2, dist});
            graph[n2].add(new int[] {n1, dist});
        }
        buildTree(1);
        buildSparseTable();
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int[] minMax = lca(n1, n2);
            sb.append(minMax[0]).append(" ").append(minMax[1]);
            if(i < m - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
    private static void buildSparseTable(){
        for(int k = 1; k < LOG; k++){
            for(int v = 1; v <= n; v++) {
                int mid = up[k - 1][v];
                up[k][v] = mid == 0 ? 0 : up[k - 1][mid];
                min[k][v] = Math.min(min[k - 1][v], min[k - 1][mid]);
                max[k][v] = Math.max(max[k - 1][v], max[k - 1][mid]);
            }
        }
    }

    private static void buildTree(int root){
        depth[root] = 0;
        up[0][root] = root;
        min[0][root] = Integer.MAX_VALUE;
        max[0][root] = Integer.MIN_VALUE;
        boolean[] visited = new boolean[n + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(root);
        visited[root] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int[] next : graph[cur]){
                if(visited[next[0]]) continue;
                int nextPos = next[0];
                visited[nextPos] = true;
                int nextDist = next[1];
                up[0][nextPos] = cur;
                min[0][nextPos] = nextDist;
                max[0][nextPos] = nextDist;
                depth[nextPos] = depth[cur] + 1;
                q.add(nextPos);
            }
        }
    }

    private static int[] lca(int n1, int n2){
        int[] minMax = new int[2];
        minMax[0] = Integer.MAX_VALUE;
        minMax[1] = Integer.MIN_VALUE;
        if(depth[n1] < depth[n2]){
            int tmp = n1;
            n1 = n2;
            n2 = tmp;
        }

        int diff = depth[n1] - depth[n2];
        for(int k = 0; k < LOG; k++){
            if(((diff >> k) & 1) == 1) {
                minMax[0] = Math.min(minMax[0], min[k][n1]);
                minMax[1] = Math.max(minMax[1], max[k][n1]);
                n1 = up[k][n1];
            }
        }

        if(n1 == n2){
            return minMax;
        }

        for(int k = LOG - 1; k >= 0; k--){
            if(up[k][n1] != up[k][n2]){
                minMax[0] = Math.min(minMax[0], Math.min(min[k][n1], min[k][n2]));
                minMax[1] = Math.max(minMax[1], Math.max(max[k][n1], max[k][n2]));
                n1 = up[k][n1];
                n2 = up[k][n2];
            }
        }
        minMax[0] = Math.min(Math.min(min[0][n1], min[0][n2]), minMax[0]);
        minMax[1] = Math.max(Math.max(max[0][n1], max[0][n2]), minMax[1]);

        return minMax;
    }
}
