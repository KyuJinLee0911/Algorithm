package boj.boj_12738_LIS3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        List<Integer> tails = new ArrayList<>();
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i : arr){
            int idx = binarySearch(tails, i);

            if(idx < 0){
                idx = -(idx + 1);
            }

            if(idx == tails.size()){
                tails.add(i);
            } else {
                tails.set(idx, i);
            }
        }

        System.out.println(tails.size());
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
