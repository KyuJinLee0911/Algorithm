package boj.boj_12899_data_structure;

import java.io.*;
import java.util.*;



public class Main {
    static class JohnwickTree{
        int size;
        int[] tree;

        public JohnwickTree(int n){
            size = n + 2;
            tree = new int[size];
        }

        private int lsb(int x){
            return x & -x;
        }

        private boolean contains(int val){
            return (sum(val) - sum(val - 1)) == 1;
        }

        public void insert(int val){
            if(!contains(val)){
                add(val, 1);
            }
        }

        private void erase(int val){
            if(contains(val)){
                add(val, -1);
            }
        }

        private void add(int idx, int delta){
            while(idx < size){
                tree[idx] += delta;
                idx += lsb(idx);
            }
        }

        public int sum(int idx){
            int result = 0;
            while(idx > 0){
                result += tree[idx];
                idx -= lsb(idx);
            }
            return result;
        }

        public int findKth(int k){
            int idx = 0;
            int bitMask = Integer.highestOneBit(size);

            for(; bitMask != 0; bitMask >>=1){
                int next = idx + bitMask;
                if(next <= size && tree[next] < k){
                    k -= tree[next];
                    idx = next;
                }
            }

            erase(idx + 1);

            return idx + 1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        JohnwickTree jwt = new JohnwickTree(2_000_000);

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int val = Integer.parseInt(st.nextToken());
            if(cmd == 1){
                jwt.insert(val);
            } else if(cmd == 2){
                int kth = jwt.findKth(val);
                System.out.println(kth);
            }
        }

    }
}
