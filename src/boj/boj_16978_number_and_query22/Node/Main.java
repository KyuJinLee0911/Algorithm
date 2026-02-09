package boj.boj_16978_number_and_query22.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class SegTree{
        static class Node{
            Node left, right;
            long sum;
            public Node(Node left, Node right, long sum){
                this.left = left;
                this.right = right;
                this.sum = sum;
            }
        }
        int n;
        public SegTree(int n){
            this. n = n;
        }

        public Node build(int[] arr){
            return build(arr, 1, n);
        }

        private Node build(int[] arr, int start, int end){
            if(start == end){
                return new Node(null, null, arr[start]);
            }

            int mid = (start + end) >>> 1;
            Node lc = build(arr, start, mid);
            Node rc = build(arr, mid + 1, end);
            return new Node(lc, rc, lc.sum + rc.sum);
        }

        public Node update(Node prevRoot, int idx, int val){
            return update(prevRoot, 1, n, idx, val);
        }

        private Node update(Node prevRoot, int start, int end, int idx, int val){
            if(start == end){
                return new Node(null, null, val);
            }

            int mid = (start + end) >>> 1;
            Node lc = prevRoot.left;
            Node rc = prevRoot.right;
            if(idx <= mid){
                Node newLc = update(lc, start, mid, idx, val);
                return new Node(newLc, rc, newLc.sum + rc.sum);
            } else {
                Node newRc = update(rc, mid + 1, end, idx, val);
                return new Node(lc, newRc, lc.sum + newRc.sum);
            }
        }

        public long query(Node root, int l, int r){
            return query(root, 1, n, l, r);
        }

        private long query(Node node, int start, int end, int l, int r){
            if(end < l || r < start){
                return 0;
            }

            if(l <= start && end <= r){
                return node.sum;
            }

            int mid = (start + end) >>> 1;
            return query(node.left, start, mid, l, r) + query(node.right, mid + 1, end, l, r);
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
        SegTree tree = new SegTree(n);

        SegTree.Node[] roots = new SegTree.Node[m + 1];
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
