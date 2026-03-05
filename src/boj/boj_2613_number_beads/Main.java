package boj.boj_2613_number_beads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] beads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        beads = new int[n];
        int sum = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            beads[i] = Integer.parseInt(st.nextToken());
            sum += beads[i];
            max = Math.max(max, beads[i]);
        }

        int l = max, r = sum;
        while (l < r) {
            int mid = (l + r) / 2;

            if (canDivide(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        int s = 0;
        int[] cnts = new int[m];
        int groupIdx = 0;
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            if (s + beads[i] > l) {
                cnts[groupIdx++] = cnt;
                s = 0;
                cnt = 0;
            }
            s += beads[i];
            cnt++;
        }
        cnts[groupIdx++] = cnt;

        int idx = 0;
        while (groupIdx < m) {
            if (cnts[idx] > 1) {
                for (int j = groupIdx; j > idx; j--) {
                    cnts[j] = cnts[j - 1];
                }
                cnts[idx] = 1;
                cnts[idx + 1]--;
                groupIdx++;
                idx++;
            } else {
                idx++;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append(l).append("\n");
        for (int i = 0; i < m; i++) {
            sb.append(cnts[i]).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    static boolean canDivide(int maxSum) {
        int s = 0;
        int cnt = 1;
        for (int x : beads) {
            if (x > maxSum) return false;
            if (s + x > maxSum) {
                s = 0;
                cnt++;
            }
            s += x;
        }

        return cnt <= m;
    }
}
