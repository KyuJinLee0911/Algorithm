package boj.boj_5624_good_number;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] arr;
    static int n;
    static final int OFFSET = 200_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        boolean[] two = new boolean[400001];

        int cnt = 0;

        for(int i = 0; i < n; i++) {
            boolean good = false;

            for(int j = 0; j < i; j++){
                int need = arr[i] - arr[j];
                if(two[need + OFFSET]) {
                    good = true;
                    break;
                }
            }

            if(good) cnt++;

            for(int j = 0; j <= i; j++){
                int sum = arr[i] + arr[j];
                if(sum >= -1 * OFFSET && sum <= OFFSET){
                    two[sum + OFFSET] = true;
                }
            }
        }

        System.out.println(cnt);
    }
}
