package boj.boj_6549_biggest_rectangle_in_histogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        long[] arr;
        StringBuilder sb = new StringBuilder();
        Stack<Integer> idxStack;

        while(!(str = br.readLine()).equals("0")){
            idxStack = new Stack<>();
            StringTokenizer st = new StringTokenizer(str);
            int n = Integer.parseInt(st.nextToken());
            arr = new long[n + 1];
            for(int i = 0; i < n; i++){
                arr[i] = Integer.parseInt(st.nextToken());
            }
            arr[n] = 0;
            long max = 0;
            for(int i = 0; i <= n; i++){
                while(!idxStack.isEmpty() && arr[idxStack.peek()] > arr[i]){
                    int idx = idxStack.pop();
                    long h = arr[idx];

                    int left = idxStack.isEmpty() ? -1 : idxStack.peek();
                    int right = i;
                    max = Long.max(h * (right - left - 1), max);
                }
                idxStack.add(i);
            }

            sb.append(max).append("\n");
        }
        System.out.println(sb);
    }
}
