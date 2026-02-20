package boj.boj_1016_power_nono_number;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());
        int sqrtMax = (int) Math.sqrt(max);
        int size = (int) (max - min) + 1;
        boolean[] check = new boolean[size];
        for(long i = 2; i <= sqrtMax; i++){
            long sq = i * i;
            long start = ((min + sq  - 1) / sq) * sq;
            for(long x = start; x <= max; x += sq){
                check[(int) (x - min)] = true;
            }
        }

        int ans = 0;
        for(boolean b : check){
            if(!b){
                ans++;
            }
        }
        System.out.println(ans);
    }
}
