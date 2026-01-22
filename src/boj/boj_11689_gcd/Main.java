package boj.boj_11689_gcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        int x = (int) Math.ceil(Math.sqrt(n)) + 1;

        long rp = n;
        long pf = n;

        for(int i = 2; i < x; i++){
            if(pf % i == 0) {
                rp = rp - rp / i;

                while(pf % i == 0) {
                    pf /= i;
                }
            }
        }

        if(pf > 1){
            rp = rp - rp / pf;
        }

        System.out.println(rp);
    }
}

// 1,000,000,000,000
