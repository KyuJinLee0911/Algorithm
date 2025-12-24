package swea.swea_segtree_practice;

import java.io.*;
import java.util.*;

public class Solution {
    static class Pair{
        int min, max;
        public Pair(int min, int max){
            this.min = min;
            this.max = max;
        }
    }
    static class SegTree{
        int[] arr, maxTree, minTree;
        public SegTree(int[] init){
            this.arr = init;
            int n = init.length;
            maxTree = new int[n * 4];
            minTree = new int[n * 4];
            build(1, 0, n - 1);
        }

        private int getMid(int start, int end){
            return (start + end) / 2;
        }

        public void build(int node, int start, int end){
            if(start == end){
                maxTree[node] = arr[start];
                minTree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(node * 2, start, mid);
                build(node * 2 + 1, mid + 1, end);
                minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
                maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
            }
        }
        public int query(int left, int right){
            Pair p = query(1, 0, arr.length - 1, left, right);
            return p.max - p.min;
        }

        private Pair query(int node, int start, int end, int left, int right){
            if(right < start || end < left){
                return new Pair(Integer.MAX_VALUE, Integer.MIN_VALUE);
            }

            if(left <= start && end <= right){
                return new Pair(minTree[node], maxTree[node]);
            }

            int mid = getMid(start, end);
            Pair lVal = query(node * 2, start, mid, left, right);
            Pair rVal = query(node * 2 + 1, mid + 1, end, left, right);
            return new Pair(Math.min(lVal.min, rVal.min), Math.max(lVal.max, rVal.max));

        }

        public void update(int idx, int newVal){
            update(1, 0, arr.length - 1, idx, newVal);
        }

        private void update(int node, int start, int end, int idx, int val){
            if(start == end){
                arr[idx] = val;
                maxTree[node] = val;
                minTree[node] = val;
            } else {
                int mid = getMid(start, end);
                if(idx <= mid){
                    update(node * 2, start, mid, idx, val);
                } else {
                    update(node * 2 + 1, mid + 1, end, idx, val);
                }
                minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
                maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
            }
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= t; tc++){
            sb.append("#").append(tc).append(" ");
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int[] arr = new int[n];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i < n; i++){
                arr[i] = Integer.parseInt(st.nextToken());
            }

            SegTree tree = new SegTree(arr);

            for(int i = 0; i < q; i++){
                st = new StringTokenizer(br.readLine());
                int cmd = Integer.parseInt(st.nextToken());
                if(cmd == 0){
                    int idx = Integer.parseInt(st.nextToken());
                    int val = Integer.parseInt(st.nextToken());
                    tree.update(idx, val);
                } else if(cmd == 1){
                    int left = Integer.parseInt(st.nextToken());
                    int right = Integer.parseInt(st.nextToken());
                    int diff = tree.query(left, right - 1);
                    sb.append(diff).append(" ");
                }
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
