package boj.boj_13460_bead_escape2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static Queue<Integer> rq = new ArrayDeque<>();
    static Queue<Integer> bq = new ArrayDeque<>();
    static char[][] board;
    static int N, M;
    static int[][] diff = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];

        boolean[][][][] visited = new boolean[N][M][N][M];
        int[][][][] moveCount = new int[N][M][N][M];
        int redPos = 0;
        int bluePos = 0;
        int exitPos = 0;
        for(int i = 0; i < N; i++){
            String str = br.readLine();
            for(int j = 0; j < M; j++){
                char cur = str.charAt(j);
                board[i][j] = cur;
                if(cur == 'R'){
                    redPos = i * M + j;
                } else if(cur == 'B'){
                    bluePos = i * M + j;
                } else if(cur == 'O'){
                    exitPos = i * M + j;
                }
            }
        }

        rq.add(redPos);
        bq.add(bluePos);
        visited[redPos / M][redPos % M][bluePos / M][bluePos % M] = true;
        int ery = -1;
        int erx = -1;
        int eby = -1;
        int ebx = -1;
        while(!rq.isEmpty() && !bq.isEmpty()){
            int curRed = rq.poll();
            int ry = curRed / M;
            int rx = curRed % M;
            int curBlue = bq.poll();
            int by = curBlue / M;
            int bx = curBlue % M;

            if(board[ry][rx] == 'O'){
                ery = ry;
                erx = rx;
                eby = by;
                ebx = bx;
                break;
            }

            board[redPos / M][redPos % M] = '.';
            board[bluePos / M][bluePos % M] = '.';
            redPos = curRed;
            bluePos = curBlue;
            board[ry][rx] = 'R';
            board[by][bx] = 'B';

            for(int i = 0; i < 4; i++){
                int dy = diff[i][0];
                int dx = diff[i][1];
                int dr = getDelta(ry, rx, i);
                int db = getDelta(by, bx, i);

                int nry = ry + dy * dr;
                int nrx = rx + dx * dr;
                int nby = by + dy * db;
                int nbx = bx + dx * db;

                if(board[nby][nbx] == 'O'){
                    moveCount[nry][nrx][nby][nbx] = -1;
                    continue;
                }

                if(nry == nby && nrx == nbx){
                    if(dr > db){
                        nry -= dy;
                        nrx -= dx;
                    } else if(dr < db) {
                        nby -= dy;
                        nbx -= dx;
                    }
                }

                if(visited[nry][nrx][nby][nbx]) continue;

                visited[nry][nrx][nby][nbx] = true;
                moveCount[nry][nrx][nby][nbx] = moveCount[ry][rx][by][bx] + 1;
                rq.add(nry * M + nrx);
                bq.add(nby * M + nbx);
            }
        }
        int ans = -1;
        if(ery != -1 && erx != -1 && eby != -1 && ebx != -1){
            ans = moveCount[ery][erx][eby][ebx];
        }

        ans = ans <= 10 && ans > 0 ? ans : -1;

        System.out.println(ans);
    }

    private static int getDelta(int y, int x, int dir){
        Queue<Integer> move = new ArrayDeque<>();
        move.add(y * M + x);
        int cnt = 0;
        while(!move.isEmpty()){
            int cur = move.poll();
            int cy = cur / M;
            int cx = cur % M;
            int dy = diff[dir][0];
            int dx = diff[dir][1];
            int ny = cy + dy;
            int nx = cx + dx;

            if(ny < 1 || ny >= N - 1 || nx < 1 || nx >= M - 1 || board[ny][nx] == '#'){
                break;
            }

            move.add(ny * M + nx);
            cnt++;
            if(board[ny][nx] == 'O')
                break;
        }
        return cnt;
    }

}
