package boj.boj_2473_three_solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long minAbs;
    static long[] solutions, bestThree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        solutions = new long[N];
        bestThree = new long[3];

        for(int i = 0; i < N; i++){
            solutions[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(solutions);

        minAbs = Long.MAX_VALUE;
        for(int i = 0; i < N - 2; i++){
            int l = i + 1;
            int r = N - 1;

            while(l < r){
                long sum = solutions[i] + solutions[l] + solutions[r];
                long abs = Math.abs(sum);

                if(abs < minAbs){
                    minAbs = abs;
                    bestThree[0] = solutions[i];
                    bestThree[1] = solutions[l];
                    bestThree[2] = solutions[r];
                    if(minAbs == 0) break;
                }

                if(sum < 0) l++;
                else r--;
            }
            if(minAbs == 0) break;
        }
        System.out.println(bestThree[0] + " " + bestThree[1] + " " + bestThree[2]);
    }
}