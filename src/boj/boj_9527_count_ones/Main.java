package boj.boj_9527_count_ones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());


        System.out.println(countOnes(b) - countOnes(a - 1));
    }

    static long countOnes(long n) {
        if (n < 0) return 0;

        long total = 0;
        for (int i = 0; i < 60; i++) {
            long bit = 1L << i; // 2^i
            long cycle = bit << 1; // 2 ^ (i + 1)

            long fullCycles = (n + 1) / cycle;
            long remainder = (n + 1) % cycle;

            total += fullCycles * bit;
            if (remainder > bit) {
                total += remainder - bit;
            }
        }

        return total;
    }
}

// 1000 1001 1010 1011 1100 1101 1110 1111
// 1  2 3  4 5 6 7  8 9 10 11 12 13 14 15
// 1  1 2  1 2 2 3  1 2 2  3  2  3  3  4 = 8 + 12
