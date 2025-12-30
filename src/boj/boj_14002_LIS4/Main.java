package boj.boj_14002_LIS4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        int[] dp = new int[N];
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
            dp[i] = 1;
        }

        int max = 0;
        int maxIdx = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < i; j++){
                if(arr[i] > arr[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Integer.max(dp[i], max);
            maxIdx = i;
        }

        int curIdx = max - 1;
        int[] lis = new int[max];
        System.out.println(max);
        StringBuilder sb = new StringBuilder();
        for(int i = maxIdx; i >= 0; i--){
            if(curIdx < 0) break;

            if(dp[i] == curIdx + 1){
                lis[curIdx--] = arr[i];
            }
        }

        for(int i = 0; i < max; i++){
            sb.append(lis[i]);
            if(i < max - 1) sb.append(" ");
        }
        System.out.println(sb);
    }
}

// 1부터 시작
// 1 -> 등록
// 2 -> 등록
//