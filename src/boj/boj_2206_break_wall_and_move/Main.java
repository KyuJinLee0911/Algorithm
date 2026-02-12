package boj.boj_2206_break_wall_and_move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
    static int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int[][] brokenWalls, map;
    static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        brokenWalls = new int[n][m];
        for(int i = 0; i < n; i++){
            String str = br.readLine();
            for(int j = 0; j < m; j++){
                map[i][j] = str.charAt(j) - '0';
            }
        }

        System.out.println(bfs(map));
    }

    static int bfs(int[][] map){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 1}); // row, col, 거리
        boolean[][] visited = new boolean[n][m];
        visited[0][0] = true;
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int row = cur[0];
            int col = cur[1];
            if(row == n - 1 && col == m - 1) return cur[2];
            for(int d = 0; d < 4; d++){
                int nextRow = row + delta[d][0];
                int nextCol = col + delta[d][1];
                if(nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= m) continue;
                int dist =  cur[2] + 1;
                int walls = map[nextRow][nextCol] == 0 ? brokenWalls[row][col] : brokenWalls[row][col] + 1;
                if(walls > 1) continue;


                if(visited[nextRow][nextCol]){
                    int min = Math.min(brokenWalls[nextRow][nextCol], walls);
                    if(min < brokenWalls[nextRow][nextCol]){
                        brokenWalls[nextRow][nextCol] = min;
                    } else {
                        continue;
                    }
                }
                visited[nextRow][nextCol] = true;
                brokenWalls[nextRow][nextCol] = walls;
                q.add(new int[]{nextRow, nextCol, dist});
            }
        }
        return -1;
    }
}
