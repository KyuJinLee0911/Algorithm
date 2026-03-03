package boj.boj_13397_dividing_section_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }

        int l = 0, r = max - min;
        if (r == 0) {
            System.out.println(0);
            return;
        }
        while (l < r) {
            int mid = (l + r) / 2;

            if (canDivide(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        System.out.println(getBest(l));

    }

    static boolean canDivide(int diff) {
        int cnt = 1;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);

            int delta = max - min;
            if (delta > diff) {
                cnt++;
                min = arr[i];
                max = arr[i];
            }
        }

        return cnt <= m;
    }

    static int getBest(int l) {
        int sMin = Integer.MAX_VALUE, sMax = Integer.MIN_VALUE, sDifMax = Integer.MIN_VALUE;
        int difMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            sMin = Math.min(arr[i], sMin);
            sMax = Math.max(arr[i], sMax);
            if (sMax - sMin > l) {
                difMax = Math.max(sDifMax, difMax);
                sMin = arr[i];
                sMax = arr[i];
            } else {
                sDifMax = Math.max(sDifMax, sMax - sMin);
                difMax = Math.max(sDifMax, difMax);
            }
        }

        return difMax;
    }
}
