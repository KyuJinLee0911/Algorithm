package boj.boj_1477_building_a_service_area;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, m, l;
    static int[] sas;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        sas = new int[n + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            sas[i] = Integer.parseInt(st.nextToken());
        }
        sas[0] = 0;
        sas[n + 1] = l;

        Arrays.sort(sas);

        int left = 1, right = l - 1;
        while (left < right) {
            int mid = (left + right) / 2;

            if (canBuild(mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        System.out.println(left);
    }

    static boolean canBuild(int dist) {
        int cnt = 0;
        for (int i = 0; i < n + 1; i++) {
            int gap = sas[i + 1] - sas[i];
            cnt += (gap - 1) / dist;
        }

        return cnt > m;
    }
}
