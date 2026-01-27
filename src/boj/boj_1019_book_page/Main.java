package boj.boj_1019_book_page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        long[] count = new long[10];
        int num = 1;
        StringBuilder sb = new StringBuilder();
        while(num <= n){
            int high = n / (num * 10);
            int cur = (n / num) % 10;
            int low = n % num;

            for(int i = 1; i <= 9; i++){
                count[i] += (long) high * num;
                if(cur == i){
                    count[i] += low + 1;
                } else if(cur > i){
                    count[i] += num;
                }
            }


            if(high == 0) {
                num *= 10;
                continue;
            }

            count[0] += (long) (high - 1) * num;
            if(cur == 0){
                count[0] += low + 1;
            } else if(cur > 0){
                count[0] += num;
            }

            num *= 10;
        }
        for(int i = 0; i < 10; i++){
            sb.append(count[i]);
            if(i < 9){
                sb.append(" ");
            }
        }

        System.out.println(sb);
    }
}
