package boj.boj_1114_cut_the_log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int l, k, c;
    static int[] cutable;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        l = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        cutable = new int[k + 2];
        st = new StringTokenizer(br.readLine());
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            cutable[i] = Integer.parseInt(st.nextToken());
        }
        cutable[k] = 0;
        cutable[k + 1] = l;
        Arrays.sort(cutable);
        for (int i = 1; i < k + 2; i++) {
            min = Math.min(min, cutable[i] - cutable[i - 1]);
        }


        int left = min, right = l;

        while (left < right) {
            int mid = (left + right) / 2;

            if (canCut(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        System.out.print(left + " ");

        int firstCut;
        int cnt = 0;
        int sum = 0;
        int lastCutIdx = -1;
        for (int i = k + 1; i >= 1; i--) {
            int diff = cutable[i] - cutable[i - 1];

            if (sum + diff > left) {
                sum = 0;
                cnt++;
                lastCutIdx = i;
            }

            sum += diff;
        }
        if(cnt < c){
            for(int i = lastCutIdx - 1; i >= 1; i--){
                int diff1 = cutable[lastCutIdx] - cutable[i];
                int diff2 = cutable[i];

                if(diff1 <= left && diff2 <= left){
                    lastCutIdx = i;
                }
            }
        }

        System.out.println(cutable[lastCutIdx]);
    }

    static boolean canCut(int length) {
        int cnt = 0;
        int sum = cutable[0];

        for (int i = 0; i < k + 1; i++) {
            int diff = cutable[i + 1] - cutable[i];
            if (diff > length) return false;

            if (sum + diff > length) {
                sum = 0;
                cnt++;
            }

            sum += diff;
        }

        return cnt <= c;
    }
}
