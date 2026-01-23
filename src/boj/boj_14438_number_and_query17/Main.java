package boj.boj_14438_number_and_query17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class SegTree{
        int[] arr;
        int[] tree;

        public SegTree(int[] input){
            int n = input.length;
            arr = input;
            tree = new int[n * 4];
            for(int i = 0; i < n * 4; i++){
                tree[i] = 1_000_000_001;
            }

            build(1, 0, n - 1);
        }

        private void build(int node, int start, int end){
            if(start == end){
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(2 * node, start, mid);
                build(2 * node + 1, mid + 1, end);
                tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
            }
        }

        public int query(int left, int right){
            return query(1, 0, arr.length - 1, left, right);
        }

        private int query(int node, int start, int end, int left, int right){
            if(right < start || end < left){
                return 1_000_000_001;
            }

            if(left <= start && end <= right){
                return tree[node];
            }

            int mid = (start + end) / 2;
            int lMin = query(2 * node, start, mid, left, right);
            int rMin = query(2 * node + 1, mid + 1, end, left, right);
            return Math.min(lMin, rMin);
        }

        public void update(int idx, int val){
            update(1, 0, arr.length - 1, idx, val);
        }

        private void update(int node, int start, int end, int idx, int val){
            if(start == end){
                arr[idx] = val;
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                if(idx <= mid) {
                    update(2 * node, start, mid, idx, val);
                } else {
                    update(2 * node + 1, mid + 1, end, idx, val);
                }
                tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        SegTree tree = new SegTree(arr);
        int m = Integer.parseInt(br.readLine());

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            if(command == 1){
                tree.update(n1 - 1, n2);
            } else if(command == 2){
                System.out.println(tree.query(n1 - 1, n2 - 1));
            }
        }
    }
}
