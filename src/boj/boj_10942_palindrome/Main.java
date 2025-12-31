package boj.boj_10942_palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] numbers = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[][] dp = new int[N][N];
        for(int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
            dp[i][i] = 1;
        }

        for(int i = 0; i < N - 1; i++){
            int left = i - 1;
            int right = i + 1;

            while(left >= 0 && right < N && left <= right){
                if(numbers[left] == numbers[right]){
                    dp[left][right] = 1;
                    left--;
                    right++;
                } else {
                    break;
                }
            }

            if(numbers[i] == numbers[i + 1]){
                left = i;
                right = i + 1;
                while(left >= 0 && right < N && left <= right){
                    if(numbers[left] == numbers[right]){
                        dp[left][right] = 1;
                        left--;
                        right++;
                    } else {
                        break;
                    }
                }
            }
        }

        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1; // 0-base indexing
            int e = Integer.parseInt(st.nextToken()) - 1;
            sb.append(dp[s][e]);
            if(i < M - 1) sb.append("\n");
        }
        System.out.println(sb);
    }
}