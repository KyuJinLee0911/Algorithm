package boj.boj_10775_airport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static class UnionFind{
        int[] parent, rank;
        public UnionFind(int size){
            parent = new int[size];
            rank = new int[size];
            for(int i = 1; i < size; i++){
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x){
            if(parent[x] != x){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX != rootY){
                parent[rootX] = rootY;
                rank[rootY] += rank[rootX];
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int g = Integer.parseInt(br.readLine());
        int p = Integer.parseInt(br.readLine());
        int[] gates = new int[p + 1];
        boolean[] used = new boolean[g + 1];
        for(int i = 1; i <= p; i++){
            gates[i] = Integer.parseInt(br.readLine());
        }
        UnionFind uf = new UnionFind(g + 1);
        int maxSize = 0;
        for(int i = 1; i <= p; i++){
            int target = uf.find(gates[i]);
            if(target == 0) break;
            if(!used[target]) {
                used[target] = true;
                int next = target - 1;
                uf.union(target, next);
            }
        }

        for(int i = 1; i <= g; i++){
            if(used[i]) maxSize++;
        }

        System.out.println(maxSize);
    }
}
