package boj.boj_2533_sns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] nodes, parents;
    static List<Integer>[] graph;
    static boolean[] isEA, visited;
    static int root;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        nodes = new int[n + 1];
        parents = new int[n + 1];
        graph = new ArrayList[n + 1];
        isEA = new boolean[n + 1];
        visited = new boolean[n + 1];

        for(int i = 1; i <= n; i++){
            nodes[i] = i;
            graph[i] = new ArrayList<>();
            parents[i] = i;
        }

        int maxDepth = 0;

        for(int i = 0; i < n - 1; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        root = 0;
        setParentAndChild(1);
        for(int i = 1; i <= n; i++){
            if(parents[i] == i){
                root = i;
            }
        }
        visited = new boolean[n + 1];

        divideAndConquer(root);
        int cnt = 0;
        for(boolean b : isEA){
            if(!b) continue;
            cnt++;
        }
        System.out.println(cnt);

    }

    private static void setParentAndChild(int cur){
        if(visited[cur]) return;
        visited[cur] = true;

        if(graph[cur].size() == 1){
            parents[cur] = graph[cur].get(0);
            if(graph[parents[cur]].size() == 1){
                parents[cur] = cur;
            }
            setParentAndChild(parents[cur]);
            return;
        }

        for(int i : graph[cur]){
            setParentAndChild(i);
        }

        for(int i : graph[cur]){
            if(parents[i] == cur) continue;

            parents[cur] = i;
        }

    }

    private static void divideAndConquer(int cur){
        if(visited[cur]) return;
        visited[cur] = true;
        if(graph[cur].size() == 1){
            isEA[parents[cur]] = true;

            return;
        }

        for(int child : graph[cur]){
            if(child == parents[cur]) continue;
            divideAndConquer(child);
        }

        boolean childIsEa = true;
        for(int c: graph[cur]){
            if(c == parents[cur]) continue;
            if(!isEA[c]){
                childIsEa = false;
                break;
            }
        }
        if(childIsEa){
            if(cur != root) {
                isEA[parents[cur]] = true;
            }
        } else {
            isEA[cur] = true;
        }
    }
}
