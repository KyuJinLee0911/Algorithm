package boj.boj_7453_four_integer_that_sum_is_zero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        int[] A = new int[n];
        int[] B = new int[n];
        int[] C = new int[n];
        int[] D = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }
        int m = n * n;
        int[] AB = new int[m];
        int[] CD = new int[m];

        for (int i = 0; i < n; i++) {
            int a = A[i];
            int c = C[i];
            for (int j = 0; j < n; j++) {
                int b = B[j];
                int d = D[j];
                AB[i * n + j] = a + b;
                CD[i * n + j] = c + d;
            }
        }
        Arrays.parallelSort(AB);
        Arrays.parallelSort(CD);

        long count = 0;

        int l = 0, r = m - 1;

        while(l < m && r >= 0){
            long sum = (long) AB[l] + (long) CD[r];
            if(sum == 0){
                int av = AB[l];
                int bv = CD[r];

                long cntA = 0;
                while(l < m && AB[l] == av) {
                    cntA++;
                    l++;
                }

                long cntB = 0;
                while(r >= 0 && CD[r] == bv){
                    cntB++;
                    r--;
                }

                count += (cntA * cntB);
            } else if (sum < 0){
                l++;
            } else{
                r--;
            }
        }

        System.out.println(count);
    }
}
