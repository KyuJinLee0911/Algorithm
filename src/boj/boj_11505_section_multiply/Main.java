package boj.boj_11505_section_multiply;

import java.io.*;
import java.util.*;

class SegTree{
    int[] arr;
    long[] tree;
    final int MOD = 1_000_000_007;

    public SegTree(int[] init){
        int n = init.length;
        arr = init;
        tree = new long[4 * n];
        build(1, 0, n - 1);
    }

    private void build(int node, int start, int end){
        if(start == end){
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(2 * node, start, mid);
            build(2 * node + 1 , mid + 1, end);
            tree[node] = (tree[2 * node] % MOD * tree[2 * node + 1] % MOD);
        }
    }

    public int query(int left, int right){
        return (int) (query(1, 0, arr.length - 1, left, right) % MOD);
    }

    private long query(int node, int start, int end, int left, int right){
        if(right < start || end < left){
            return 1;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        int mid = (start + end) / 2;
        long lSum = query(2 * node, start, mid, left, right) % MOD;
        long rSum = query(2 * node + 1, mid + 1, end, left, right) % MOD;
        return (lSum * rSum);
    }

    public void update(int idx, int newValue){
        update(1, 0, arr.length - 1, idx, newValue);
    }

    private void update(int node, int start, int end, int idx, int val){
        if(start == end){
            arr[idx] = val;
            tree[node] = val % MOD;
        } else {
            int mid = (start + end) / 2;
            if(idx <= mid){
                update(2 * node, start, mid, idx, val);
            } else {
                update(2 * node + 1, mid + 1, end, idx, val);
            }
            tree[node] = (tree[2 * node] % MOD * tree[2 * node + 1] % MOD);
        }
    }
}

// 그림으로
//                1[0~5]
//       2[0~2]            3[3~5]
//  4[0~1]    5[2]     6[3~4]   7[5]
// 8[0] 9[1]        10[3] 11[4]
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] initVal = new int[N];
        for(int i = 0; i < N; i++){
            initVal[i] = Integer.parseInt(br.readLine());
        }

        SegTree tree = new SegTree(initVal);
        for(int i = 0; i < M + K; i++){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if(cmd == 1){
                tree.update(b - 1, c);
            } else if(cmd == 2){
                int ans = tree.query(b - 1, c - 1);
                System.out.println(ans);
            }
        }
    }
}
