package boj.boj_2696_get_middle_value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static PriorityQueue<Integer> left, right;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {
            int n = Integer.parseInt(br.readLine());
            left = new PriorityQueue<>(Collections.reverseOrder());
            right = new PriorityQueue<>();
            StringTokenizer st;
            sb.append((n + 1) / 2).append("\n");
            int rows = n / 10 + 1;
            int count = 0;

            for (int i = 0; i < rows; i++) {
                int cols = Math.min(n, 10);
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= cols; j++) {
                    int number = Integer.parseInt(st.nextToken());
                    addNumber(number);
                    balance();
                    int idx = i * 10 + j;
                    if (idx % 2 == 1) {
                        sb.append(left.peek()).append(" ");
                        count++;
                    }

                    if (count >= 10) {
                        sb.append("\n");
                        count = 0;
                    }
                }

                n -= 10;
            }

            sb.append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    private static void addNumber(int n) {
        left.add(n);
        if (Math.abs(left.size() - right.size()) > 1) {
            if (left.size() > right.size()) {
                right.add(left.poll());
            } else {
                left.add(right.poll());
            }
        }
    }

    private static void balance() {
        if (!left.isEmpty() && !right.isEmpty() && left.peek() > right.peek()) {
            int r = right.poll();
            int l = left.poll();
            left.add(r);
            right.add(l);
        }
    }
}
