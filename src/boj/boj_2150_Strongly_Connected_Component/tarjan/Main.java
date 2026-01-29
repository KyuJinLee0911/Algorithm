package boj.boj_2150_Strongly_Connected_Component.tarjan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Scc implements Comparable<Scc>{
        List<Integer> scc;
        public Scc(){
            scc = new ArrayList<>();
        }

        public Scc(List<Integer> scc){
            this.scc = scc;
        }

        public void add(int v){
            scc.add(v);
        }

        public int get(int idx){
            return scc.get(idx);
        }

        public int size(){
            return scc.size();
        }

        public void sort(){
            Collections.sort(scc);
        }

        @Override
        public int compareTo(Scc o){
            return Integer.compare(scc.get(0), o.get(0));
        }
    }
    static TreeSet<Scc> sccs;
    static Stack<Integer> stack;
    static int[] visited, low;
    static boolean[] onStack;
    static List<Integer>[] graph;
    static int v, index;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        graph = new ArrayList[v + 1];
        for (int i = 1; i <= v; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            graph[v1].add(v2);
        }
        stack = new Stack<>();
        sccs = new TreeSet<>();
        visited = new int[v + 1];
        low = new int[v + 1];
        for(int i = 1; i <= v; i++){
            visited[i] = -1;
            low[i] = -1;
        }
        onStack = new boolean[v + 1];
        index = 1;
        for(int i = 1; i <= v; i++){
            if(visited[i] == -1){
                tarjan(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        int sccCount = sccs.size();
        sb.append(sccCount).append("\n");
        int cnt = 0;
        while(!sccs.isEmpty()){
            cnt++;
            Scc scc = sccs.pollFirst();
            for(int i = 0; i < scc.size(); i++){
                sb.append(scc.get(i)).append(" ");
            }
            sb.append("-1");
            if(cnt < sccCount)
                sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void tarjan(int v){
        visited[v] = index;
        low[v] = index;
        index++;
        stack.add(v);
        onStack[v] = true;

        for(int w : graph[v]){
            if(visited[w] == -1){
                tarjan(w);
                low[v] = Math.min(low[v], low[w]);
            } else if(onStack[w]){
                low[v] = Math.min(low[v], visited[w]);
            }
        }

        if(low[v] == visited[v]){
            Scc scc = new Scc();
            int w;
            do{
                w = stack.pop();
                onStack[w] = false;
                scc.add(w);
            } while(w != v);
            scc.sort();
            sccs.add(scc);
        }
    }
}
