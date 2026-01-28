package boj.boj_1761_distance_between_two_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Node{
        int dist, target;
        public Node(int target, int dist){
            this.target = target;
            this.dist = dist;
        }
    }
    static int n, LOG;
    static List<Node>[] graph;
    static int[][] up;
    static int[] depth;
    static long[] distToRoot;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        LOG = 1;
        while((1 << LOG) <= n){
            LOG++;
        }

        up = new int[LOG][n + 1];
        distToRoot = new long[n + 1];
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
            graph[n1].add(new Node(n2, dist));
            graph[n2].add(new Node(n1, dist));
        }
        buildTree(1);
        buildSparseTable();
        StringBuilder sb = new StringBuilder();
        int m = Integer.parseInt(br.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int anc = lca(n1, n2);

            sb.append(getDistance(n1, n2, anc));
            if(i < m - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }

    static void buildTree(int root){
        boolean[] visited = new boolean[n + 1];
        depth[root] = 0;
        visited[root] = true;
        up[0][root] = 0;
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(new Node(root, 0));
        while(!q.isEmpty()){
            Node cur = q.poll();
            int curTarget = cur.target;
            for(Node n : graph[curTarget]){
                if(visited[n.target]) continue;
                visited[n.target] = true;
                up[0][n.target] = cur.target;
                depth[n.target] = depth[curTarget] + 1;
                distToRoot[n.target] = distToRoot[cur.target] + n.dist;
                q.add(n);
            }
        }
    }

    static void buildSparseTable() {
        for(int k = 1; k < LOG; k++){
            for(int v = 1; v <= n; v++){
                int mid = up[k - 1][v];
                up[k][v] = mid == 0 ? 0 : up[k - 1][mid];
            }
        }
    }

    static int lca(int n1, int n2){
        if(depth[n1] < depth[n2]){
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        int diff = depth[n1] - depth[n2];
        for(int k = 0; k < LOG; k++){
            if(((diff >> k) & 1) == 1) {
                n1 = up[k][n1];
            }
        }

        if(n1 == n2){
            return n1;
        }

        for(int k = LOG - 1; k >= 0; k--) {
            if(up[k][n1] != up[k][n2]){
                n1 = up[k][n1];
                n2 = up[k][n2];
            }
        }

        return up[0][n1];
    }

    static long getDistance(int n1, int n2, int lca){
        return distToRoot[n1] + distToRoot[n2] - 2 * distToRoot[lca];
    }
}
