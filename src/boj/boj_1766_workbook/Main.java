package boj.boj_1766_workbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] graph;
    static int[] indegree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        graph = new ArrayList[n + 1];
        indegree = new int[n + 1];
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph[from].add(to);
            indegree[to]++;
        }

        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i = 1; i <= n; i++){
            if(indegree[i] == 0){
                q.add(i);
            }
        }

        List<Integer> sequence = new ArrayList<>();
        while(!q.isEmpty()){
            int cur = q.poll();

            sequence.add(cur);
            for(int next : graph[cur]){
                indegree[next]--;
                if(indegree[next] == 0){
                    q.add(next);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i : sequence){
            sb.append(i).append(" ");
        }

        System.out.println(sb);
    }
}
