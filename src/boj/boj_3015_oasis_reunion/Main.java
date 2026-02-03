package boj.boj_3015_oasis_reunion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    static class Node{
        int height, cnt;
        public Node(int height, int cnt){
            this.height = height;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }
        long count = 0;
        Stack<Node> st = new Stack<>();
        for(int i = 0; i < n; i++){
            int cnt = 1;
            while (!st.isEmpty() && st.peek().height < arr[i]){
                Node popped = st.pop();
                count += popped.cnt;
            }

            if(!st.isEmpty() && st.peek().height == arr[i]){
                count += st.peek().cnt;
                cnt += st.peek().cnt;
                st.pop();
            }

            if(!st.isEmpty()){
                count++;
            }
            st.add(new Node(arr[i], cnt));
        }
        System.out.println(count);
    }
}

