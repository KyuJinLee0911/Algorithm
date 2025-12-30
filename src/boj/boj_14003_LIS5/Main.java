package boj.boj_14003_LIS5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        int[] dp = new int[N];
        List<Integer> tails = new ArrayList<>();
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int maxIdx = 0;
        for(int i = 0; i < N; i++){
            int num = arr[i];
            int idx = binarySearch(tails, num);

            if(idx < 0){
                idx = -(idx + 1);
            }

            if(idx == tails.size()){
                tails.add(num);
                maxIdx = i;
            } else {
                tails.set(idx, num);
            }
            dp[i] = idx;
        }
        int max = tails.size();
        int curIdx = tails.size() - 1;
        int[] lis = new int[max];
        System.out.println(max);
        StringBuilder sb = new StringBuilder();

        for(int i = maxIdx; i >= 0; i--){
            if(curIdx < 0) break;
            if(dp[i] == curIdx){
                lis[curIdx--] = arr[i];
            }
        }

        for(int i = 0; i < max; i++){
            sb.append(lis[i]);
            if(i < max - 1) sb.append(" ");
        }
        System.out.println(sb);
    }

    private static int binarySearch(List<Integer> list, int num){
        int l = 0;
        int r = list.size() - 1;
        while(l <= r){
            int mid = (l + r) / 2;
            int midVal = list.get(mid);

            if(midVal > num){
                r = mid - 1;
            } else if(midVal < num) {
                l = mid + 1;
            } else {
                return mid;
            }
        }

        return -(l + 1);
    }
}
