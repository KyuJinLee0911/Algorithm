package boj.boj_2150_Strongly_Connected_Component;

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
    static int[] visit, lowset;
    static boolean[] onStack;
    static Stack<Integer> stack;
    static int index;
    static List<Integer>[] graph;
    static int v;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        graph = new ArrayList[v + 1];
        for(int i = 1; i <= v; i++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < e; i++){
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            graph[v1].add(v2);
        }
        sccs = new TreeSet<>();
        visit = new int[v + 1];
        lowset = new int[v + 1];
        Arrays.fill(visit, -1);
        Arrays.fill(lowset, -1);
        onStack = new boolean[v + 1];
        stack = new Stack<>();
        index = 1;
        for(int i = 1; i <= v; i++){
            if(visit[i] == -1)
                tarjan(i);
        }

        StringBuilder sb = new StringBuilder();
        int sccsCount = sccs.size();
        sb.append(sccs.size()).append("\n");
        for(int i = 0; i < sccsCount; i++){
            Scc curScc = sccs.pollFirst();
            int sccSize = curScc.size();
            for(int j = 0; j < sccSize; j++){
                sb.append(curScc.get(j));
                if(j < sccSize - 1)
                    sb.append(" ");
            }
            sb.append(" ").append("-1");
            if(i < sccsCount - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);

    }

    private static int tarjan(int v){
        visit[v] = index;
        lowset[v] = index;
        index++;
        stack.add(v);
        onStack[v] = true;

        for(int w : graph[v]){
            if(visit[w] == -1)
                lowset[v] = Math.min(lowset[v], tarjan(w));
            else if(onStack[w])
                lowset[v] = Math.min(lowset[v], visit[w]);
        }

        if(lowset[v] == visit[v]){
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

        return lowset[v];
    }
}
