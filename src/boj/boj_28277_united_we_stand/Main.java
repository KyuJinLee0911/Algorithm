package boj.boj_28277_united_we_stand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        HashSet<Integer>[] unions = new HashSet[n + 1];
        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            unions[i] = new HashSet<>();
            int size = Integer.parseInt(st.nextToken());
            for(int j = 0; j < size; j++){
                int element = Integer.parseInt(st.nextToken());
                unions[i].add(element);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < q; i++){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            if(cmd == 2){
                int id = Integer.parseInt(st.nextToken());
                sb.append(unions[id].size()).append("\n");
            } else if(cmd == 1) {
                int id1 = Integer.parseInt(st.nextToken());
                int id2 = Integer.parseInt(st.nextToken());
                if(unions[id2].size() > unions[id1].size()){
                    HashSet<Integer> tmp = new HashSet<>(unions[id1]);
                    unions[id1] = unions[id2];
                    unions[id2] = tmp;
                }
                unions[id1].addAll(unions[id2]);
                unions[id2].clear();
            }
        }

        System.out.println(sb.toString().trim());
    }
}
