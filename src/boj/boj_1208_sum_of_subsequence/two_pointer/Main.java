package boj.boj_1208_sum_of_subsequence.two_pointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        long count = 0;
        int half = n / 2;
        int lLen = (1 << half) - 1;
        int rLen = (1 << (n - half)) - 1;
        int[] left = new int[lLen];
        int[] right = new int[rLen];
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());

        }
        int idx = 0;
        for(int i = 1; i < (1 << half); i++){
            int sum = 0;
            for(int j = 0; j < half; j++){
                if((i & (1 << j)) != 0){
                    sum += arr[j];
                }
            }
            if(sum == s){
                count++;
            }
            left[idx++] = sum;
        }

        idx = 0;
        for(int i = 1; i < (1 << (n - half)); i++){
            int sum = 0;
            for(int j = 0; j < (n - half); j++){
                if((i & (1 << j)) != 0){
                    sum += arr[j + half];
                }
            }
            if(sum == s){
                count++;
            }
            right[idx++] = sum;
        }

        Arrays.sort(left);
        Arrays.sort(right);

        int l = 0, r = right.length - 1;
        while(l < lLen && r >= 0){
            int sum = left[l] + right[r];
            if(sum == s){
                int lv = left[l];
                int cntL = 0;
                while(l < lLen && left[l] == lv){
                    cntL++;
                    l++;
                }
                int rv = right[r];
                int cntR = 0;
                while(r >= 0 && right[r] == rv){
                    cntR++;
                    r--;
                }
                count += ((long) cntL * (long) cntR);
            } else if(sum < s){
                l++;
            } else {
                r--;
            }
        }

        System.out.println(count);
    }
}
