package boj.boj_9466_term_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int tc = 0; tc < T; tc++){
            int n = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] selected = new int[n];
            int[] state = new int[n];
            for(int i = 0; i < n; i++){
                selected[i] = Integer.parseInt(st.nextToken()) - 1;
            }
            int seq = 1;
            int[] sequenceId = new int[n];

            for(int i = 0; i < n; i++){
                int cur = i;
                while(state[cur] == 0){
                    state[cur] = 1;
                    sequenceId[cur] = seq;
                    cur = selected[cur];
                }

                while(state[cur] == 1 && sequenceId[cur] == seq){
                    state[cur] = 2;
                    cur = selected[cur];
                }
                seq++;
            }
            int cnt = 0;
            for(int i = 0; i < n; i++){
                if(state[i] == 2) continue;

                cnt++;
            }

            sb.append(cnt);
            if(tc < T - 1) sb.append("\n");
        }
        System.out.println(sb);
    }
}
