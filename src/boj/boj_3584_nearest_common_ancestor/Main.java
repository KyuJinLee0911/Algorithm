package boj.boj_3584_nearest_common_ancestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parent, depth;
    static List<Integer>[] child;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for(int tc = 0; tc < t; tc++){
            int n = Integer.parseInt(br.readLine());
            parent = new int[n + 1];
            child = new ArrayList[n + 1];
            for(int i = 1; i <= n; i++){
                child[i] = new ArrayList<>();
                parent[i] = i;
            }
            depth = new int[n + 1];
            StringTokenizer st;
            for(int i = 0; i < n - 1; i++){
                st = new StringTokenizer(br.readLine());
                int p = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                parent[c] = p;
                child[p].add(c);
                setDepth(c);
            }
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            System.out.println(findNCA(n1, n2));
        }

    }

    private static int findNCA(int n1, int n2){
        if(depth[n1] < depth[n2]){
            return findNCA(n1, parent[n2]);
        } else if(depth[n1] > depth[n2]){
            return findNCA(parent[n1], n2);
        }

        if(n1 == n2){
            return n1;
        }

        return findNCA(parent[n1], parent[n2]);
    }

    private static void setDepth(int c){
        depth[c] = depth[parent[c]] + 1;
        for(int gc : child[c]){
            setDepth(gc);
        }
    }
}
