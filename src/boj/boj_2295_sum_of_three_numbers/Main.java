package boj.boj_2295_sum_of_three_numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            int a = Integer.parseInt(br.readLine());
            arr[i] = a;
        }
        int[] sums = new int[n * n];
        int idx = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                sums[idx++] = arr[i] + arr[j];
            }
        }
        Arrays.sort(arr);
        Arrays.sort(sums);

        for(int i = n - 1; i >= 0; i--){
            for(int j = 0; j < n; j++){
                int target = arr[i] - arr[j];
                if(Arrays.binarySearch(sums, target) >= 0){
                    System.out.println(arr[i]);
                    return;
                }
            }
        }
    }
}
