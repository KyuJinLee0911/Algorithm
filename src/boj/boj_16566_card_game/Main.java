package boj.boj_16566_card_game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class UnionFind{
        int[] parent;

        public UnionFind(int size){
            parent = new int[size];
            for(int i = 0; i < size; i++){
                parent[i] = i;
            }
        }

        public int find(int x){
            if(parent[x] != x){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y){
            int rx = find(x);
            int ry = find(y);

            if(rx != ry){
                parent[rx] = ry;
            }
        }
    }
    static int[] cards;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        cards = new int[m];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < m; i++){
            cards[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(cards);
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        UnionFind uf = new UnionFind(m + 1);
        for(int i = 0; i < k; i++){
            int red = Integer.parseInt(st.nextToken());
            int ub = upperBound(red);
            int idx = uf.find(ub);
            sb.append(cards[idx]).append("\n");
            uf.union(idx, idx + 1);
        }

        System.out.println(sb.toString().trim());
    }

    static int upperBound(int i){
        int l = 0, r = cards.length;

        while(l < r){
            int mid = (l + r) / 2;

            if(cards[mid] <= i){
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        if(l == cards.length) return -1;

        return l;
    }
}
