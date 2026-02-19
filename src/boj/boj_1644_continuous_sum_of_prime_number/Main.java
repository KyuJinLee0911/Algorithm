package boj.boj_1644_continuous_sum_of_prime_number;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int rootN = (int) Math.sqrt(n);
        boolean[] isPrimeNumber = new boolean[n + 1];
        Arrays.fill(isPrimeNumber, true);
        isPrimeNumber[0] = false;
        isPrimeNumber[1] = false;
        for(int i = 2; i <=rootN; i++){
            if(!isPrimeNumber[i]) continue;
            for(int j = 2; i * j <= n; j++){
                isPrimeNumber[i * j] = false;
            }
        }
        List<Integer> primeNumbers = new ArrayList<>();
        for(int i = 2; i <= n; i++){
            if(!isPrimeNumber[i]) continue;
            primeNumbers.add(i);
        }
        int count = 0;
        int l = 0, r = 0;
        int sum = 0;
        while(r < primeNumbers.size()){
            sum += primeNumbers.get(r);

            while(sum >= n){
                if(sum == n){
                    count++;
                }
                sum -= primeNumbers.get(l);
                l++;
            }

            r++;
        }

        System.out.println(count);
    }
}
