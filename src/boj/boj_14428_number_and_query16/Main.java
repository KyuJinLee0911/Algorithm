package boj.boj_14428_number_and_query16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static class Node{
        int idx, val;

        public Node(int idx, int val){
            this.idx = idx;
            this.val = val;
        }
    }
    static class SegTree{
        int[] arr;
        Node[] tree;

        public SegTree(int[] input){
            int n = input.length;
            arr = input;
            tree = new Node[n * 4];
            for(int i = 0; i < n * 4; i++){
                tree[i] = new Node(100_001, 1_000_000_001);
            }

            build(1, 0, n - 1);
        }

        private void build(int node, int start, int end){
            if(start == end){
                tree[node].val = arr[start];
                tree[node].idx = start;
            } else {
                int mid = (start + end) / 2;
                build(2 * node, start, mid);
                build(2 * node + 1, mid + 1, end);
                tree[node].val = Math.min(tree[2 * node].val, tree[2 * node + 1].val);
                if(tree[2 * node].val < tree[2 * node + 1].val){
                    tree[node].idx = tree[node * 2].idx;
                } else if(tree[2 * node].val > tree[2 * node + 1].val){
                    tree[node].idx = tree[2 * node + 1].idx;
                } else {
                    tree[node].idx = Math.min(tree[2 * node].idx, tree[2 * node + 1].idx);
                }
            }
        }

        public Node query(int left, int right){
            return query(1, 0, arr.length - 1, left, right);
        }

        private Node query(int node, int start, int end, int left, int right){
            if(right < start || end < left){
                return new Node(100_001, 1_000_000_001);
            }

            if(left <= start && end <= right){
                return tree[node];
            }

            int mid = (start + end) / 2;
            Node lMin = query(2 * node, start, mid, left, right);
            Node rMin = query(2 * node + 1, mid + 1, end, left, right);
            if(lMin.val < rMin.val){
                return lMin;
            } else if(rMin.val < lMin.val){
                return rMin;
            } else {
                if(lMin.idx < rMin.idx){
                    return lMin;
                } else {
                    return rMin;
                }
            }
        }

        public void update(int idx, int val){
            update(1, 0, arr.length - 1, idx, val);
        }

        private void update(int node, int start, int end, int idx, int val){
            if(start == end){
                arr[idx] = val;
                tree[node].val = val;
                tree[node].idx = idx;
            } else {
                int mid = (start + end) / 2;
                if(idx <= mid) {
                    update(2 * node, start, mid, idx, val);
                } else {
                    update(2 * node + 1, mid + 1, end, idx, val);
                }
                tree[node].val = Math.min(tree[2 * node].val, tree[2 * node + 1].val);
                if(tree[2 * node].val < tree[2 * node + 1].val){
                    tree[node].idx = tree[2 * node].idx;
                } else if(tree[2 * node].val > tree[2 * node + 1].val){
                    tree[node].idx = tree[2 * node + 1].idx;
                } else {
                    tree[node].idx = Math.min(tree[2 * node].idx, tree[2 * node + 1].idx);
                }
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
                System.out.println(tree.query(n1 - 1, n2 - 1).idx + 1);
            }
        }
    }
}
