package boj.boj_9007_canoe_athlete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] ab, cd;
    static int minSum, minDiff, n, k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            minSum = Integer.MAX_VALUE;
            minDiff = Integer.MAX_VALUE;
            int[][] students = new int[4][n];
            for (int i = 0; i < 4; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    students[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int size = n * n;

            ab = new int[size];
            cd = new int[size];
            int index = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    ab[index] = students[0][i] + students[1][j];
                    cd[index++] = students[2][i] + students[3][j];
                }
            }

            Arrays.sort(ab);
            Arrays.sort(cd);
            int l = 0, r = size - 1;
            while (r >= 0 && l < size) {
                int sum = ab[l] + cd[r];
                update(sum);
                if (sum > k) {
                    r--;
                } else if (sum < k) {
                    l++;
                } else {
                    break;
                }

            }
            sb.append(minSum).append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    static void update(int val) {
        int diff = Math.abs(k - val);

        if (diff < minDiff || (diff == minDiff && val < minSum)) {
            minSum = val;
            minDiff = diff;
        }
    }
}
