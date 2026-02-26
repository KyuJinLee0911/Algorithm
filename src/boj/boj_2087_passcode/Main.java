package boj.boj_2087_passcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Code implements Comparable<Code> {
        int sum, mask;

        public Code(int sum, int mask) {
            this.sum = sum;
            this.mask = mask;
        }

        @Override
        public int compareTo(Code o){
            return Integer.compare(sum, o.sum);
        }
    }
    static int[] a;
    static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = Integer.parseInt(br.readLine());
        }
        k = Integer.parseInt(br.readLine());

        System.out.println(mitm());
    }

    static void genLeft(int l, int r, int sum, List<Code> out, int mask){
        if(l == r){
            out.add(new Code(sum, mask));
            return;
        }

        genLeft(l + 1, r, sum, out, mask);
        int bit = 1 << (l);
        genLeft(l + 1, r, sum + a[l], out, mask | bit);
    }

    static void genRight(int l, int r, int sum, List<Code> out, int mask, int mid){
        if(l == r){
            out.add(new Code(sum, mask));
            return;
        }

        genRight(l + 1, r, sum, out, mask, mid);

        int bit = 1 << (l - mid);
        genRight(l + 1, r, sum + a[l], out, mask | bit, mid);
    }

    static int upperBound(List<Code> sum, int val){
        int l = 0, r = sum.size();
        while(l < r){
            int mid = (l + r) / 2;

            if(sum.get(mid).sum <= val){
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    static String mitm(){
        int mid = n / 2;

        List<Code> left = new ArrayList<>();
        List<Code> right = new ArrayList<>();

        genLeft(0, mid, 0, left, 0);
        genRight(mid, n, 0, right, 0, mid);

        Collections.sort(right);

        for(Code l : left){
            int need = k - l.sum;
            int idx = upperBound(right, need) - 1;
            if(idx >= 0 && right.get(idx).sum == need){
                return getBinary(l.mask, right.get(idx).mask, mid);
            }
        }

        return "";
    }

    static String getBinary(int left, int right, int mid){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < mid; i++){
            sb.append((left >> i & 1));
        }

        for(int i = 0; i < n - mid; i++){
            sb.append((right >> i) & 1);
        }

        return sb.toString();
    }
}
