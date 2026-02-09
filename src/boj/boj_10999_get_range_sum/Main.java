package boj.boj_10999_get_range_sum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static class SegTree{
        long[] arr, tree, lazy;
        public SegTree(long[] arr){
            this.arr = arr;
            int n = arr.length;
            tree = new long[n * 4];
            lazy = new long[n * 4];
            build(1, 0, n - 1);
        }

        public void build(int node, int start, int end){
            if(start == end){
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(node * 2, start, mid);
                build(node * 2 + 1, mid + 1, end);
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }

        public void update(int left, int right, long add){
            update(1, 0, arr.length - 1, left, right, add);
        }

        private void update(int node, int start, int end, int left, int right, long add){
            if(right < start || end < left) return;
            if(left <= start && end <= right){
                apply(node, start, end, add);
                return;
            }

            push(node, start, end);
            int mid = (start + end) >>> 1;
            update(node * 2, start, mid, left, right, add);
            update(node * 2 + 1, mid + 1, end, left, right, add);
            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        public long query(int left, int right){
            return query(1, 0, arr.length - 1, left, right);
        }

        private long query(int node, int start, int end, int left, int right){
            if(end < left || right < start){
                return 0;
            }
            if(left <= start && end <= right){
                return tree[node];
            }

            push(node, start, end);
            int mid = (start + end) >>> 1;
            long lSum = query(node * 2, start, mid, left, right);
            long rSum = query(node * 2 + 1, mid + 1, end, left, right);
            return lSum + rSum;
        }

        private void apply(int node, int l, int r, long add){
            tree[node] += (r - l + 1L) * add;
            lazy[node] += add;
        }

        private void push(int node, int l, int r){
            if(lazy[node] == 0 || l == r) return;
            int mid = (l + r) / 2;
            long add = lazy[node];
            apply(node * 2, l, mid, add);
            apply(node * 2 + 1, mid + 1, r, add);
            lazy[node] = 0;
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        long[] arr = new long[n];
        for(int i = 0; i < n; i++){
            arr[i] = Long.parseLong(br.readLine());
        }

        SegTree tree = new SegTree(arr);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m + k; i++){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken()) - 1; // 0-base indexing
            int r = Integer.parseInt(st.nextToken()) - 1;
            if(cmd == 1){
                long add = Long.parseLong(st.nextToken());
                tree.update(l, r, add);
            } else {
                long q = tree.query(l, r);
                sb.append(q).append("\n");
            }
        }

        System.out.println(sb);
    }
}
