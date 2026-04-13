package boj.boj_2251_panibottle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static boolean[] water;
    static int A, B, C;
    static boolean[][][] flag;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        water = new boolean[C + 1];
        water[C] = true;
        flag = new boolean[A + 1][B + 1][C + 1];

        dfs(0, 0, C);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= C; i++) {
            if (!water[i]) continue;

            sb.append(i).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    static void dfs(int a, int b, int c) {
        if (flag[a][b][c]) return;
        flag[a][b][c] = true;

        if (a == 0) {
            water[c] = true;
        }


        if (a < A) {
            int amount = A - a;

            // c -> a => a에 공간이 남아있어야 함 -> a < A
            // A가 가득 차거나 -> c의 양이 적어도 a의 남은 공간보다 큰 경우
            if (amount <= c) {
                dfs(A, b, c - amount);
            } else { // C가 전부 비거나 -> c의 양이 a의 남은 공간보다 작은 경우
                dfs(a + c, b, 0);
            }

            // b -> a
            if (amount <= b) {
                dfs(A, b - amount, c);
            } else {
                dfs(a + b, 0, c);
            }
        }


        if (b < B) {
            int amount = B - b;

            // c -> b
            if (amount <= c) {
                dfs(a, B, c - amount);
            } else {
                dfs(a, b + c, 0);
            }

            // a -> b
            if (amount <= a) {
                dfs(a - amount, B, c);
            } else {
                dfs(0, a + b, c);
            }
        }

        if (c < C) {
            int amount = C - c;

            // a -> c
            if (amount <= a) {
                dfs(a - amount, b, C);
            } else {
                dfs(0, b, a + c);
            }

            // b -> c
            if (amount <= b) {
                dfs(a, b - amount, C);
            } else {
                dfs(a, 0, b + c);
            }
        }
    }
}
