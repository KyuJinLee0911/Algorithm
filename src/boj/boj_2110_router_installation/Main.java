package boj.boj_2110_router_installation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, c;
    static int[] routers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        routers = new int[n];
        for (int i = 0; i < n; i++) {
            routers[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(routers);

        int l = 0, r = routers[n - 1] - routers[0] + 1;
        while (l < r) {
            int mid = (l + r) / 2;

            if (canInstall(mid)) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        System.out.println(l - 1);

    }

    static boolean canInstall(int dist) {
        int pos = routers[0];
        int cnt = 1;
        for (int i = 1; i < n; i++) {
            int nextPos = routers[i];
            if (nextPos - pos >= dist) {
                pos = nextPos;
                cnt++;
            }
        }
        return cnt >= c;
    }
}
