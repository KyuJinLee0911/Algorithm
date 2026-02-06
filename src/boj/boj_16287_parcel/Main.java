package boj.boj_16287_parcel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int[] pkg = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            pkg[i] = Integer.parseInt(st.nextToken());
        }

        Set<Integer> needs = new HashSet<>();

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                int need = w - (pkg[i] + pkg[j]);
                if(needs.contains(need)){
                    System.out.println("YES");
                    return;
                }
            }

            for(int j = 0; j < i; j++){
                needs.add((pkg[i] + pkg[j]));
            }
        }

        System.out.println("NO");
    }
}
