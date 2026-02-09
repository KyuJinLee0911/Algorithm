package boj.boj_2143_sum_of_two_arrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        int[] prefixA = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] subA = new int[n * (n + 1) / 2];
        int idx = 0;
        for(int i = 0; i < n; i++){
            int a = Integer.parseInt(st.nextToken());
            if(i == 0){
                prefixA[i] = a;
            } else {
                prefixA[i] = prefixA[i - 1] + a;
            }
            subA[idx++] = prefixA[i];
        }

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                subA[idx++] = prefixA[j] - prefixA[i];
            }
        }

        int m = Integer.parseInt(br.readLine());
        int[] prefixB = new int[m];
        int[] subB = new int[m * (m + 1) / 2];
        idx = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < m; i++){
            int b = Integer.parseInt(st.nextToken());
            if(i == 0){
                prefixB[i] = b;
            } else {
                prefixB[i] = prefixB[i - 1] + b;
            }
            subB[idx++] = prefixB[i];
        }

        for(int i = 0; i < m; i++){
            for(int j = i + 1; j < m; j++){
                subB[idx++] = prefixB[j] - prefixB[i];
            }
        }

        Arrays.sort(subA);
        Arrays.sort(subB);
        long count = 0;
        for(int i = 0; i < subA.length; i++){
            int find = t - subA[i];
            int hi = upperBound(subB, find);
            int lo = lowerBound(subB, find);
            count += (hi - lo);
        }

        System.out.println(count);
    }

    static int lowerBound(int[] arr, int x){
        int l = 0, r = arr.length;
        while(l < r) {
            int m = (l + r) >>> 1;
            if(arr[m] >= x){
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    static int upperBound(int[] arr, int x){
        int l = 0, r = arr.length;
        while(l < r) {
            int m = (l + r) >>> 1;
            if(arr[m] > x){
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
