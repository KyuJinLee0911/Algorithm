package boj.boj_2150_Strongly_Connected_Component.kosaraju;

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
    static boolean[] visited;
    static List<Integer>[] graph, reverseGraph;
    static int v;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        graph = new ArrayList[v + 1];
        reverseGraph = new ArrayList[v + 1];
        sccs = new TreeSet<>();
        for (int i = 1; i <= v; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            graph[v1].add(v2);
            reverseGraph[v2].add(v1);
        }
        visited = new boolean[v + 1];
        stack = new Stack<>();
        for(int i = 1; i <= v; i++){
            if(!visited[i])
                dfs1(i);
        }
        Arrays.fill(visited, false);
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            int v = stack.pop();
            if(visited[v]) continue;;
            ArrayList<Integer> scc = new ArrayList<>();
            dfs2(v, scc);
            Scc newScc = new Scc(scc);
            newScc.sort();
            sccs.add(newScc);
        }
        int cnt = 0;
        int sccCount = sccs.size();
        sb.append(sccCount).append("\n");
        while(!sccs.isEmpty()){
            Scc scc = sccs.pollFirst();
            cnt++;
            int sccSize = scc.size();
            for(int i = 0; i < sccSize; i++){
                sb.append(scc.get(i)).append(" ");
            }
            sb.append("-1");
            if(cnt < sccCount)
                sb.append("\n");
        }
        System.out.println(sb);
    }

    static void dfs1(int v){
        visited[v] = true;
        for(int w : graph[v]){
            if(!visited[w]) dfs1(w);
        }
        stack.add(v);
    }

    static void dfs2(int v, List<Integer> scc){
        visited[v] = true;
        scc.add(v);
        for(int w : reverseGraph[v]){
            if(!visited[w]) dfs2(w, scc);
        }
    }
}
