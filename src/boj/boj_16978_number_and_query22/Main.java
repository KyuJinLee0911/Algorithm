package boj.boj_16978_number_and_query22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class SegTree{
        int n, ptr;
        int[] left, right;
        long[] tree;
        public SegTree(int n, int poolSize){
            this.n = n;
            left = new int[poolSize + 5];
            right = new int[poolSize + 5];
            tree = new long[poolSize + 5];
            this.ptr = 0;
        }

        private int newNode(int leftChild, int rightchild, long s){
            int idx = ++ptr;
            left[idx] = leftChild;
            right[idx] = rightchild;
            tree[idx] = s;
            return idx;
        }

        public int build(int[] arr){
            return build(arr, 1, n);
        }

        private int build(int[] arr, int start, int end){
            if(start == end){
                return newNode(0, 0, arr[start]);
            } else {
                int mid = (start + end) / 2;
                int lc = build(arr, start, mid);
                int rc = build(arr, mid + 1, end);
                return newNode(lc, rc, tree[lc] + tree[rc]);
            }
        }

        public int update(int prevRoot, int idx, int val){
            return update(prevRoot, 1, n, idx, val);
        }

        private int update(int prevRoot, int start, int end, int idx, int val){
            if(start == end){
                return newNode(0, 0, val);
            } else {
                int mid = (start + end) / 2;
                int lc = left[prevRoot];
                int rc = right[prevRoot];
                if(idx <= mid){
                    int newLc = update(lc, start, mid, idx, val);
                    return newNode(newLc, rc, tree[newLc] + tree[rc]);
                } else {
                    int newRc = update(rc, mid + 1, end, idx, val);
                    return newNode(lc, newRc, tree[lc] + tree[newRc]);
                }
            }
        }

        public long query(int root, int left, int right){
            return query(root, 1, n, left, right);
        }

        private long query(int node, int start, int end, int l, int r){
            if(end < l || r < start){
                return 0;
            }

            if(l <= start && end <= r){
                return tree[node];
            }

            int mid = (start + end) / 2;
            long lSum = query(left[node], start, mid, l, r);
            long rSum = query(right[node], mid + 1, end, l, r);
            return lSum + rSum;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        int LOG = 1;
        while((1 << LOG) < n) LOG++;
        SegTree tree = new SegTree(n, n * 4 + (m * (LOG + 2)) * 2);

        int[] roots = new int[m + 1];
        roots[0] = tree.build(arr);
        int t = 0;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            if(cmd == 1){
                int idx = Integer.parseInt(st.nextToken());
                int val = Integer.parseInt(st.nextToken());
                t++;
                roots[t] = tree.update(roots[t - 1], idx, val);
            } else if(cmd == 2){
                int k = Integer.parseInt(st.nextToken());
                int left= Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                sb.append(tree.query(roots[k], left, right)).append("\n");
            }
        }

        System.out.println(sb);
    }
}
