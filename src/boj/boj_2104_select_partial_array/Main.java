package boj.boj_2104_select_partial_array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static class SegTree{
        int[] arr;
        int[] minIdxTree;
        public SegTree(int[] arr) {
            this.arr = arr;
            int n = arr.length;
            minIdxTree = new int[n * 4];
            build();
        }

        private int better(int i, int j){
            if(arr[i] != arr[j]) return arr[i] < arr[j] ? i : j;
            return Math.min(i, j);
        }

        private void build(){
            buildMin(1, 0, arr.length - 1);
        }

        private void buildMin(int node, int start, int end){
            if(start == end){
                minIdxTree[node] = start;
            } else {
                int mid = (start + end) / 2;
                buildMin(node * 2, start, mid);
                buildMin(node * 2 + 1, mid + 1, end);
                minIdxTree[node] = better(minIdxTree[node * 2], minIdxTree[node * 2 + 1]);
            }
        }

        public int query(int left, int right){
            return queryMin(1, 0, arr.length - 1, left, right);
        }

        private int queryMin(int node, int start, int end, int left, int right){
            if(end < left || right < start){
                return -1;
            }

            if(left <= start && end <= right){
                return minIdxTree[node];
            }

            int mid = (start + end) / 2;
            int leftMin = queryMin(node * 2, start, mid, left, right);
            int rightMin = queryMin(node * 2 + 1, mid + 1, end, left, right);

            if(leftMin == -1) return rightMin;
            if(rightMin == -1) return leftMin;
            return better(leftMin, rightMin);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        long[] prefixSum = new long[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }

        SegTree tree = new SegTree(arr);
        System.out.println(dnc(0, n - 1, arr, prefixSum, tree));

    }

    private static long rangeSum(int l, int r, long[] psum){
        return psum[r + 1] - psum[l];
    }

    private static long dnc(int l, int r, int[] arr, long[] pSum, SegTree tree){
        if(l > r) return 0;
        int k = tree.query(l, r);
        long sum = rangeSum(l, r, pSum);
        long best = sum * arr[k];

        long leftBest = dnc(l, k - 1, arr, pSum, tree);
        long rightBest = dnc(k + 1, r, arr, pSum, tree);

        return Math.max(best, Math.max(leftBest, rightBest));
    }
}
