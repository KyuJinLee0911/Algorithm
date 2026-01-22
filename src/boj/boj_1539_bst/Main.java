package boj.boj_1539_bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        TreeMap<Integer, Long> map = new TreeMap<>();
        long totalCount = 1;
        map.put(Integer.parseInt(br.readLine()), 1L);
        for(int i = 1; i < n; i++) {
            int val = Integer.parseInt(br.readLine());
            Integer lower = map.lowerKey(val);
            Integer higher = map.higherKey(val);
            long depth = 0;
            if(lower == null){
                depth = map.get(higher) + 1;
            } else if(higher == null) {
                depth = map.get(lower) + 1;
            } else {
                depth = Math.max(map.get(lower), map.get(higher)) + 1;
            }
            map.put(val, depth);
            totalCount += depth;
        }
        System.out.println(totalCount);
    }
}
