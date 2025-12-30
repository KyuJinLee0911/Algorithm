package boj.boj_2580_sudoku;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] rowUsed = new boolean[9][10];
    static boolean[][] colUsed = new boolean[9][10];
    static boolean[][] boxUsed = new boolean[9][10];
    static List<int[]> empties = new ArrayList<>();
    static int[][] board = new int[9][9];
    static boolean solved = false;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 9; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++){
                int num = Integer.parseInt(st.nextToken());
                board[i][j] = num;
                if(num != 0){
                    int idx = getIdx(i,j);
                    rowUsed[i][num] = true;
                    colUsed[j][num] = true;
                    boxUsed[idx][num] = true;
                } else {
                    empties.add(new int[]{i, j});
                }
            }
        }

        dfs(0);

        String answer = writeAsString(board);
        System.out.println(answer);
    }

    private static void dfs(int idx){
        if(solved) return;
        if(idx == empties.size()){
            solved = true;
            return;
        }

        int r = empties.get(idx)[0];
        int c = empties.get(idx)[1];
        int b = getIdx(r, c);

        for(int num = 1; num <= 9; num++){
            if(!rowUsed[r][num] && !colUsed[c][num] && !boxUsed[b][num]){
                place(r, c, b, num);
                dfs(idx + 1);
                if(solved) return;
                remove(r, c, b, num);
            }
        }

    }

    private static void place(int r, int c, int b, int num){
        board[r][c] = num;
        rowUsed[r][num] = true;
        colUsed[c][num] = true;
        boxUsed[b][num] = true;
    }

    private static void remove(int r, int c, int b, int num){
        board[r][c] = 0;
        rowUsed[r][num] = false;
        colUsed[c][num] = false;
        boxUsed[b][num] = false;
    }

    private static String writeAsString(int[][] board){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                sb.append(board[i][j]);
                if(j < 8) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static int getIdx(int i, int j){
        return (i / 3) * 3 + j / 3;
    }
}
