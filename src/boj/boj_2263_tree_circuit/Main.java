package boj.boj_2263_tree_circuit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, cnt;
    static StringBuilder sb;
    static int[] inorder, postorder, inorderIdx;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        inorder = new int[n];
        postorder = new int[n];
        inorderIdx = new int[n + 1];
        for (int i = 0; i < n; i++) {
            inorder[i] = Integer.parseInt(st.nextToken());
            inorderIdx[inorder[i]] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            postorder[i] = Integer.parseInt(st.nextToken());
        }

        int root = postorder[n - 1];
        dnc(0, n - 1, 0, n - 1);
        System.out.println(sb);
    }

    private static void dnc(int inL, int inR, int postL, int postR) {
        if(inL > inR || postL > postR) return;

        int root = postorder[postR];
        int rootIdx = inorderIdx[root];

        sb.append(root).append(" ");

        int leftSize = rootIdx - inL;

        dnc(inL, rootIdx - 1, postL, postL + leftSize - 1);
        dnc(rootIdx + 1, inR, postL + leftSize, postR - 1);
    }
}


//  2
// 1 3
// Inorder = 1-2-3 (left-root-right)
// inorder의 가장 첫번째, 마지막은 leaf 확정

// Postorder = 1-3-2 (left-right-root)
// postorder의 가장 마지막에 오는 숫자가 root 노드

// => postorder로 root를 찾고, root를 기준으로 좌우로 나눠서 분할정복하면 될듯?
//
// Preorder = 2-1-3 (root-left-right)

