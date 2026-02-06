package boj.boj_1208_sum_of_subsequence;

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
        int[] left = new int[(int) Math.pow(2, half) - 1];
        int[] right = new int[(int) Math.pow(2, n - half) - 1];
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

        for(int i = 0; i < left.length; i++){
            int find = s - left[i];
            int lb = lowerBound(right, find);
            int ub = upperBound(right, find);
            count += (ub - lb);
        }

        System.out.println(count);
    }

    static int lowerBound(int[] a, int x){
        int l = 0, r = a.length;
        while(l < r){
            int mid = (l + r) / 2;
            if(a[mid] >= x){
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    static int upperBound(int[] a, int x){
        int l = 0, r = a.length;
        while(l < r){
            int mid = (l + r) / 2;
            if(a[mid] > x){
                r = mid;
            } else{
                l = mid + 1;
            }
        }
        return l;
    }
}