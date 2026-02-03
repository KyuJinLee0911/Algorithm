package boj.boj_2104_select_partial_array.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n + 1];
        long[] prefixSum = new long[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        arr[n] = 0;
        long maxScore = 0;
        for(int i = 0; i <= n; i++){
            long cur = i == n ? 0 : arr[i];
            while(!stack.isEmpty() && arr[stack.peek()] > cur){
                int mid = stack.pop();

                int left = stack.isEmpty() ? 0 : stack.peek() + 1;
                int right = i - 1;

                long score = rangeSum(left, right, prefixSum) * arr[mid];
                maxScore = Math.max(score, maxScore);
            }
            stack.add(i);
        }
        System.out.println(maxScore);
    }

    private static long rangeSum(int l, int r, long[] psum){
        return psum[r + 1] - psum[l];
    }
}
