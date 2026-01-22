package boj.boj_2957_bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        TreeMap<Integer, Long> map = new TreeMap<>();
        long totalC = 0;

        int first = Integer.parseInt(br.readLine());
        map.put(first, 0L);
        sb.append("0\n");

        for(int i = 1; i < n; i++){
            int x = Integer.parseInt(br.readLine());

            Integer lower = map.lowerKey(x);
            Integer higher = map.higherKey(x);

            long depth = 0;
            if(lower == null){
                depth = map.get(higher) + 1;
            } else if(higher == null) {
                depth = map.get(lower) + 1;
            } else {
                depth = Math.max(map.get(lower), map.get(higher)) + 1;
            }

            map.put(x, depth);
            totalC += depth;
            sb.append(totalC);
            if(i < n - 1){
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
}
