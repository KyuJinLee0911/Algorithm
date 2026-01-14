package boj.boj_1697_hide_and_seek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] minTime;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        if(n < k){
            minTime = new int[k * 2];
            proceed(n, k);
            System.out.println(minTime[k]);
        } else if(n > k) {
            System.out.println(n - k);
        } else {
            System.out.println(0);
        }
    }

    private static void proceed(int n, int k){
        Queue<Integer> q = new ArrayDeque<>();
        q.add(n);
        while(!q.isEmpty()){
            int cur = q.poll();

            int newTime = minTime[cur] + 1;
            int forward = cur + 1;
            int backward = cur - 1;
            int teleport = cur * 2;
            if(forward == k || backward == k || teleport == k){
                if(minTime[k] == 0){
                    minTime[k] = newTime;
                } else {
                    minTime[k] = Math.min(minTime[k], newTime);
                }
                break;
            }
            if(forward < k * 2 && (minTime[forward] == 0 || minTime[forward] > newTime)){
                q.add(forward);
                minTime[forward] = newTime;
            }
            if(backward > 0 && (minTime[backward] == 0 || minTime[backward] > newTime)){
                q.add(backward);
                minTime[backward] = newTime;
            }
            if(teleport < k * 2 && (minTime[teleport] == 0 || minTime[teleport] > newTime)){
                q.add(teleport);
                minTime[teleport] = newTime;
            }
        }
    }
}
